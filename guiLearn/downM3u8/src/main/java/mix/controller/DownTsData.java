package mix.controller;

import mix.model.TsName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公共变量类
 */
public class DownTsData {
    private final String FILE_NAME; // 保存的文件名
    private final CopyOnWriteArrayList<TsName> TS_LIST = new CopyOnWriteArrayList<>(),
            alreadyTlList = new CopyOnWriteArrayList<>();
    private final AtomicInteger ERROR_COUNT = new AtomicInteger(0);

    public DownTsData(List<TsName> tsList, String dirPath) {
        FILE_NAME = dirPath + "\\dealItemList.txt";
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

    public int getProgress() {
        return alreadyTlList.size();
    }

    /**
     * 将已完成的项目加入已完成列表
     *
     * @param item 已完成项目
     */
    void setProgress(TsName item) {
        alreadyTlList.add(item);
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
        } catch (IOException ignored) {
        }
    }

    /**
     * 在程序结束时,将所有已完成的项目写入文件中
     */
    public void writeDealItemToFile() {
        File file = new File(FILE_NAME);
        try {
            // 创建新文件
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
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

    void addErrorCount() {
        ERROR_COUNT.incrementAndGet();
    }

    public int getErrCount() {
        return ERROR_COUNT.get();
    }
}
