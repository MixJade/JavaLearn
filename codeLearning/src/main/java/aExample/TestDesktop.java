package aExample;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 桌面类的测试
 *
 * @since 2024-7-12 17:06
 */
public class TestDesktop {
    private final static String FILE_NAME = "新建文本文件.txt";

    /**
     * 在桌面上创建文件
     * (这个不是Desktop类,但后面要用)
     *
     * @since 2024-7-12 17:06
     */
    @Test
    public void createFileOnDesk() {
        try {
            // 获取桌面路径
            File desktopDir = new File(System.getProperty("user.home") + "/Desktop");
            // 创建一个新的txt文件
            File textFile = new File(desktopDir, FILE_NAME);
            if (!textFile.exists()) {
                if (textFile.createNewFile()) {
                    System.out.println("文件创建成功！请前往桌面查看");
                } else {
                    System.out.println("文件创建错误！");
                }
            } else {
                System.out.println("文件已存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Desktop类将文件移入回收站
     *
     * @since 2024-3-29 22:44
     */
    @Test
    public void moveButTrash() {
        // 从系统内获取桌面路径
        String desktop = System.getProperty("user.home") + "/Desktop";
        File file = new File(desktop, FILE_NAME);
        // 将文件移入回收站
        Desktop.getDesktop().moveToTrash(file);
        System.out.println("文件【" + FILE_NAME + "】已移入回收站");
    }

    /**
     * 使用Java打开文件夹
     *
     * @since 2024-7-4 09:45:04
     */
    @Test
    public void openExportDir() {
        // 先查看当前系统是否有图形化界面
        if (Desktop.isDesktopSupported()) {
            try {
                // 打开上两级文件夹
                File myFile = new File("../../");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("没有找到文件夹");
            }
        }
    }
}