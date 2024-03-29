package course_05;

import javax.swing.*;
import java.awt.*;

/**
 * 【5.4】【题】计算器布局
 * 完成如下图所示计算器的界面布局。
 *
 * @since 2022-4-28
 */
public class CalculatorLayout {
    public static void main(String[] args) {
        JFrame KON = new JFrame("徒有其表的计算器");
        JPanel mio = new JPanel();
        JTextArea emergence = new JTextArea(1, 10);
        emergence.setText("请按下按钮:");
        KON.setBounds(240, 240, 360, 360);
        mio.setLayout(null);
        mio.setBounds(0, 20, 280, 280);
        Font f = new Font("黑体", Font.PLAIN, 10);
        JButton[] buttonNormal = new JButton[22];
        String[] twentyTwoButton = new String[]{"7", "8", "9", "除", "sqrt", "4", "5", "6", "乘",
                "1/x", "1", "2", "3", "减", "%", "0", ".", "+/%", "加", "=", "BackSpace", "Clear"};
        int[] twoLongSeat = new int[]{40, 160};
        int across, stand;
        for (int i = 0; i < 22; i++) {
            across = i % 5 * 60 + 10;
            stand = i / 5 * 60 + 40;
            String buttonName = twentyTwoButton[i];
            buttonNormal[i] = new JButton(buttonName);

            if (i < 20) {
                buttonNormal[i].setBounds(across, stand, 55, 55);
                buttonNormal[i].addActionListener(e -> emergence.append(buttonName));
            } else {
                buttonNormal[i].setBounds(twoLongSeat[i - 20], 5, 100, 30);
                buttonNormal[i].addActionListener(e -> emergence.setText("按钮都是摆设 :"));
            }
            buttonNormal[i].setFont(f);
            mio.add(buttonNormal[i]);
        }
        buttonNormal[20].setForeground(Color.RED);
        KON.setLayout(new BorderLayout(2, 2));
        KON.add(emergence, BorderLayout.NORTH);
        KON.add(mio, BorderLayout.CENTER);
        KON.setVisible(true);
        KON.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
