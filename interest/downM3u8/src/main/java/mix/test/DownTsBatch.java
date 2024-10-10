package mix.test;

import mix.entiy.TsName;
import mix.utils.DownFile;
import mix.utils.ReadTsFromM3u8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

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
        // 创建结束钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("程序结束了！");
            // 将列表写入文件
            myOKO.writeDealItemToFile();
        }));
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
    private static final String FILE_NAME = "dealItemList.txt"; // 保存的文件名
    private final CopyOnWriteArrayList<TsName> TS_LIST = new CopyOnWriteArrayList<>(),
            alreadyTlList = new CopyOnWriteArrayList<>();
    private final int LIST_SIZE;

    public MyOKO(List<TsName> tsList) {
        LIST_SIZE = tsList.size();
        readDingItemFromFile(tsList);
        TS_LIST.addAll(tsList);
    }

    /**
     * 获取下一项待处理，并从待选列表中移除
     *
     * @return 下一项待处理的
     */
    TsName getNextItem() {
        if (TS_LIST.isEmpty()) {
            return null;
        }
        return TS_LIST.remove(0);
    }

    /**
     * 将已完成的项目加入已完成列表
     *
     * @param item 已完成项目
     */
    void setProgress(TsName item) {
        alreadyTlList.add(item);
    }

    String getProgress() {
        return alreadyTlList.size() + "/" + LIST_SIZE;
    }

    /**
     * 从文件中读取已完成的项目，并与传来的列表进行筛选
     *
     * @param tsList 传来的列表
     */
    private void readDingItemFromFile(List<TsName> tsList) {
        try {
            Path filePath = Paths.get(FILE_NAME);
            if (Files.exists(filePath)) {
                // 文件存在
                List<String> lines = Files.readAllLines(filePath);
                List<TsName> haveTsName = new ArrayList<>();
                for (String line : lines) {
                    haveTsName.add(new TsName("", line));
                }
                // 循环并筛选掉已处理的
                tsList.removeAll(haveTsName);
                // 将已处理的加入已处理列表
                alreadyTlList.addAll(haveTsName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将所有已完成的项目写入文件中
     */
    public void writeDealItemToFile() {
        File file = new File(FILE_NAME);
        try {
            // 创建新文件
            if (file.createNewFile()) {
                System.out.println("创建文件: " + file.getName());
            }
            // 创建FileWriter对象
            FileWriter writer = new FileWriter(file);
            // 向文件写入内容
            List<String> alreadyTlTextList = alreadyTlList.stream()
                    .map(TsName::fileName).toList();
            writer.write(String.join("\n", alreadyTlTextList));
            writer.close();
        } catch (IOException ignored) {
        }
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
            myOKO.setProgress(item);
        }
        latch.countDown();
    }
}