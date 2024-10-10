package threadCategory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 5个线程读取一个列表，每读取完成一个元素，就存入一个全局列表中。
 * 如果程序中断，下次读取就从上次中断处开始
 *
 * @since 2024-10-09 17:58:26
 */
public class SharedIncrement {
    private static final int THREAD_COUNT = 5; // 线程数量


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        List<String> elList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            elList.add("El-" + (i + 1));
        }
        // 从文件读取并筛选列表
        MyOKO myOKO = new MyOKO(elList);
        // 创建结束钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("程序结束了！");
            // 将列表写入文件
            myOKO.writeDealItemToFile();
        }));
        // 批量启动
        for (int i = 0; i < THREAD_COUNT; i++) {
            new MySleepThread(i, myOKO, latch).start();
        }
        latch.await();  // 等待所有的线程结束
        System.out.println("All of jobs has processed");
    }
}

/**
 * 公共变量类
 */
class MyOKO {
    private static final String FILE_NAME = "dealItemList.txt"; // 保存的文件名
    private final CopyOnWriteArrayList<String> EL_LIST = new CopyOnWriteArrayList<>(),
            alreadyTlList = new CopyOnWriteArrayList<>();
    private final int LIST_SIZE;

    public MyOKO(List<String> elList) {
        LIST_SIZE = elList.size();
        readDingItemFromFile(elList);
        EL_LIST.addAll(elList);
    }

    /**
     * 获取下一项待处理，并从待选列表中移除
     *
     * @return 下一项待处理的
     */
    String getNextItem() {
        if (EL_LIST.isEmpty()) {
            return null;
        }
        return EL_LIST.remove(0);
    }

    /**
     * 将已完成的项目加入已完成列表
     *
     * @param item 已完成项目
     */
    void setProgress(String item) {
        alreadyTlList.add(item);
        System.out.println(alreadyTlList.size() + "/" + LIST_SIZE + " " + item);
    }

    /**
     * 从文件中读取已完成的项目，并与传来的列表进行筛选
     *
     * @param elList 传来的列表
     */
    private void readDingItemFromFile(List<String> elList) {
        try {
            Path filePath = Paths.get(FILE_NAME);
            if (Files.exists(filePath)) {
                // 文件存在
                List<String> lines = Files.readAllLines(filePath);
                System.out.println(lines.size());
                System.out.println(lines);
                // 循环并筛选掉已处理的
                elList.removeAll(lines);
                // 将已处理的加入已处理列表
                alreadyTlList.addAll(lines);
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
            writer.write(String.join("\n", alreadyTlList));
            writer.close();
        } catch (IOException ignored) {
        }
    }
}

/**
 * 用于读取元素并计数的线程
 */
class MySleepThread extends Thread {
    private final MyOKO myOKO;
    private final CountDownLatch latch;

    public MySleepThread(int name, MyOKO myOKO, CountDownLatch latch) {
        super("T" + name);
        this.myOKO = myOKO;
        this.latch = latch;
    }

    @Override
    @SuppressWarnings("BusyWait") // 在循环中sleep可能忙于等待
    public void run() {
        String item;
        while ((item = myOKO.getNextItem()) != null) {
            try {
                Thread.sleep(1000); // 处理元素
                myOKO.setProgress(item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        latch.countDown();
    }
}