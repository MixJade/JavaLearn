package mix.test;

import mix.entiy.TsName;
import mix.utils.DownFile;
import mix.utils.ReadTsFromM3u8;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用5个线程批量下载ts文件
 *
 * @since 2024-10-09 22:03:26
 */
public class DownTsBatch {

    private static final int THREAD_COUNT = 5; // 线程数量


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        String m3u8Path = "example/index.m3u8";
        List<TsName> tsNames = ReadTsFromM3u8.readTsNameList(m3u8Path);
        MyOKO myOKO = new MyOKO(tsNames);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new DownTsThread(i, myOKO, latch, m3u8Path).start();
        }
        // 定时输出进度
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(myOKO.getProgress());
            }
        }, 0, 5000); // 每5000毫秒（5秒）执行一次
        latch.await();  // 等待所有的线程结束
        System.out.println("All of jobs has processed");
    }
}

/**
 * 公共变量类
 */
class MyOKO {
    private static final AtomicInteger SUC_COUNT = new AtomicInteger(0);
    private final CopyOnWriteArrayList<TsName> TS_LIST = new CopyOnWriteArrayList<>();
    private final int LIST_SIZE;

    public MyOKO(List<TsName> tsNameList) {
        TS_LIST.addAll(tsNameList);
        LIST_SIZE = tsNameList.size();
    }

    TsName getNextItem() {
        if (TS_LIST.isEmpty()) {
            return null;
        }
        return TS_LIST.remove(0);
    }

    void addCount() {
        SUC_COUNT.incrementAndGet();
    }

    String getProgress() {
        return SUC_COUNT + "/" + LIST_SIZE;
    }
}

/**
 * 用于读取元素并计数的线程
 */
class DownTsThread extends Thread {
    private final MyOKO myOKO;
    private final CountDownLatch latch;
    private final String DIR_PATH;

    public DownTsThread(int name, MyOKO myOKO, CountDownLatch latch, String m3u8Path) {
        super("T" + name);
        this.myOKO = myOKO;
        this.latch = latch;
        // 获取保存文件路径
        int index = m3u8Path.lastIndexOf("/");
        if (index == -1) this.DIR_PATH = m3u8Path; // 如果没有出现斜线
        else this.DIR_PATH = m3u8Path.substring(0, index + 1); // 返回包含斜线及其前面的字符串
    }

    @Override
    public void run() {
        TsName item;
        while ((item = myOKO.getNextItem()) != null) {
            DownFile.downFromWeb(item.url(), DIR_PATH + item.fileName());
            myOKO.addCount();
        }
        latch.countDown();
    }
}