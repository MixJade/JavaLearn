package demo;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 超长文本弹窗
 *
 * @since 2024-10-16 17:00:31
 */
public class LongTextDialog extends JFrame implements ActionListener {
    LongTextDialog() {
        setTitle("长文本测试");

        // 创建一个新的按钮
        JButton button = new JButton("Click Me");
        // 添加一个动作监听器到按钮上
        button.addActionListener(this);
        // 将按钮添加到 JFrame 上
        add(button);

        // 设置 JFrame 的大小，并使它可见
        setSize(300, 200);
        setVisible(true);

        // 设置 JFrame 在关闭窗口时退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        new LongTextDialog();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 设置要显示的长文本
        String longText = "This is a very long text.\n".repeat(300);
        // 设置显示的文本区域
        JTextArea textArea = new JTextArea(8, 20);
        textArea.setText(longText);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        // 设置滚动条
        JScrollPane scrollPane = new JScrollPane(textArea);
        // 在点击按钮时显示带有长文本的消息对话框
        JOptionPane.showMessageDialog(this, scrollPane);
    }
}