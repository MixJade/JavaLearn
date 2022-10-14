package swingCategory;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Calculator();
    }

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

