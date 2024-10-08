package study;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * 测试将字符串放入剪贴板
 *
 * @since 2024-10-08 10:03:16
 */
public class TesClipboard {
    public static void main(String[] args) {
        String myString = "This is my string";
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
