package study;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 使用Java打开文件夹
 *
 * @since 2024-7-4 09:45:04
 */
public class DeskOpenDir {
    public static void main(String[] args) {
        if (Desktop.isDesktopSupported()) {
            try {
                // 打开上两级文件夹
                File myFile = new File("../../");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("没有找到文件");
            }
        }
    }
}
