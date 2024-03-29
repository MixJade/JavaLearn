package study;

import java.awt.*;
import java.io.File;

/**
 * 桌面类的测试
 */
public class TestDesktop {
    public static void main(String[] args) {
        // 从系统内获取桌面路径
        String desktop = System.getProperty("user.home") + "\\Desktop\\";
        File file = new File(desktop + "新建 文本文档.txt");
        // 将文件移入回收站
        Desktop.getDesktop().moveToTrash(file);
    }
}