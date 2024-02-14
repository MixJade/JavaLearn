package course_05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 【5.1】【题】加减乘除计算器
 * 编写一个应用程序，有一个标题为“计算”的窗口，窗口的布局为FlowLayout布局。
 * 设计四个按钮，分别命名为“加”、“差”、“积、”、“除”，
 * 另外，窗口中还有三个文本框。
 * 单击相应的按钮，将两个文本框的数字做运算，在第三个文本框中显示结果。
 * 要求处理NumberFormatException异常。
 *
 * @since 2022-4-28
 */
public class Calculator extends JFrame implements ActionListener {
    JButton plus, subtract, multiply, division;
    JTextField one, two, three;
    JLabel label;
    public Calculator() {
        init();
        setVisible(true);
        setResizable(true);
        validate();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    public void init() {
        setLayout(new FlowLayout());
        setSize(300, 320);
        setTitle("计算");
        plus = new JButton("加");
        subtract = new JButton("差");
        multiply = new JButton("积");
        division = new JButton("除");
        one = new JTextField(10);
        two = new JTextField(10);
        three = new JTextField(10);
        label = new JLabel(" ", JLabel.CENTER);
        label.setBackground(Color.green);
        add(one);
        add(label);
        add(two);
        add(three);
        add(plus);
        add(subtract);
        add(multiply);
        add(division);
        plus.addActionListener(this);
        subtract.addActionListener(this);
        multiply.addActionListener(this);
        division.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        double n;
        if (e.getSource() == plus) {
            double n1, n2;
            try {
                n1 = Double.parseDouble(one.getText());
                n2 = Double.parseDouble(two.getText());
                n = n1 + n2;
                three.setText(String.valueOf(n));
                label.setText("+");
            } catch (NumberFormatException ee) {
                three.setText("请输入数字字符");
            }
        } else if (e.getSource() == subtract) {
            double n1, n2;
            try {
                n1 = Double.parseDouble(one.getText());
                n2 = Double.parseDouble(two.getText());
                n = n1 - n2;
                three.setText(String.valueOf(n));
                label.setText("-");
            } catch (NumberFormatException ee) {
                three.setText("请输入数字字符");
            }
        } else if (e.getSource() == multiply) {
            double n1, n2;
            try {
                n1 = Double.parseDouble(one.getText());
                n2 = Double.parseDouble(two.getText());
                n = n1 * n2;
                three.setText(String.valueOf(n));
                label.setText("*");
            } catch (NumberFormatException ee) {
                three.setText("请输入数字字符");
            }
        } else if (e.getSource() == division) {
            double n1, n2;
            try {
                n1 = Double.parseDouble(one.getText());
                n2 = Double.parseDouble(two.getText());
                n = n1 / n2;
                three.setText(String.valueOf(n));
                label.setText("/");
            } catch (NumberFormatException ee) {
                three.setText("请输入数字字符");
            }
        }
        validate();
    }
}

