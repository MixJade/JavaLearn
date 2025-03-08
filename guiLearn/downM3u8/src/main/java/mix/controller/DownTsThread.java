package mix.controller;

import mix.model.TsName;
import mix.utils.DownFile;

import java.util.concurrent.CountDownLatch;

/**
 * 用于读取元素并计数的线程
 */
public class DownTsThread extends Thread {
    private final DownTsData downTsData;
    private final CountDownLatch latch;
    private final String DIR_PATH;

    public DownTsThread(DownTsData downTsData, CountDownLatch latch, String downDir) {
        this.downTsData = downTsData;
        this.latch = latch;
        this.DIR_PATH = downDir;
    }

    @Override
    public void run() {
        TsName item;
        while ((item = downTsData.getNextItem()) != null) {
            boolean downFromWeb = DownFile.downFromWeb(item.url(), DIR_PATH + "\\" + item.fileName());
            if (downFromWeb) downTsData.setProgress(item);
            else downTsData.addErrorCount();
        }
        latch.countDown();
    }
}
