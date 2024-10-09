package mix.test;

import mix.utils.DownFile;

import java.io.File;

/**
 * 下载M3U8
 */
public class DownM3u8 {
    public static void main(String[] args) {
        String webUrl = "https://m3u8.hmrvideo.com/play/3e8a80dc1c714e35aa77b29114e15dd8.m3u8";
        String filePath = "example/index.m3u8";
        // 下载之前看对应的文件夹是否存在
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                System.out.println("建立文件夹失败");
            }
        }
        // 下载m3u8并保存
        DownFile.downFromWeb(webUrl, filePath);
        System.out.println("m3u8已保存至:" + filePath);
    }
}
