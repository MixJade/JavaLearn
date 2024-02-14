package course_05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 【5.3】【题】两点距离MVC版
 * 用MVC框架实现 二维平面上的两个点的距离的计算。
 *
 * @since 2022-4-28
 */
public class TwoDotDistance {
    /**
     * Controller层：两点之间的距离启动类
     * <p>用MVC思想来做Gui</p>
     */
    public static void main(String[] args) {
        TwoDistanceView win = new TwoDistanceView();
        win.setTitle("两点距离MVC版");
        win.setBounds(100, 100, 500, 200);
    }
}

/**
 * View层：两点间的视图
 */
class TwoDistanceView extends JFrame implements ActionListener {
    JTextField textAx, textBx, textAy, textBy;//视图
    JTextArea showArea;//视图
    JButton controlButton;//控制器

    TwoDistanceView() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        textAx = new JTextField(5);
        textAy = new JTextField(5);
        textBx = new JTextField(5);
        textBy = new JTextField(5);
        showArea = new JTextArea();
        controlButton = new JButton("计算距离");
        JPanel pNorth = new JPanel();
        pNorth.add(new JLabel("Ax:"));
        pNorth.add(textAx);
        pNorth.add(new JLabel("Ay:"));
        pNorth.add(textAy);
        pNorth.add(new JLabel("Bx:"));
        pNorth.add(textBx);
        pNorth.add(new JLabel("By:"));
        pNorth.add(textBy);
        pNorth.add(controlButton);
        controlButton.addActionListener(this);
        add(pNorth, BorderLayout.NORTH);
        add(new JScrollPane(showArea), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double ax = Double.parseDouble(textAx.getText().trim());
            double ay = Double.parseDouble(textAy.getText().trim());
            double bx = Double.parseDouble(textBx.getText().trim());
            double by = Double.parseDouble(textBy.getText().trim());
            TwoDistance twoDistance = new TwoDistance(ax, ay, bx, by);
            double distance = twoDistance.getDistance();
            showArea.append("距离:" + distance + "\n");
        } catch (Exception ex) {
            showArea.append("\n" + ex + "\n");
        }
    }
}

/**
 * Model层：两点间的数据模型
 */
record TwoDistance(double ax, double ay, double bx, double by) {
    double getDistance() {
        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }
}