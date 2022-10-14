package swingCategory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorTrapezoid {
    public static void main(String[] args) {
        TrapezoidWindow win = new TrapezoidWindow();
        win.setTitle("使用MVC结构");
        win.setBounds(100, 100, 420, 260);
    }
}

class TrapezoidWindow extends JFrame implements ActionListener {
    TrapezoidLaden laden;//模型
    JTextField textAbove, textBottom, textHeight;//视图
    JTextArea showArea;//视图
    JButton controlButton;//控制器

    TrapezoidWindow() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        laden = new TrapezoidLaden();
        textAbove = new JTextField(5);
        textBottom = new JTextField(5);
        textHeight = new JTextField(5);
        showArea = new JTextArea();
        controlButton = new JButton("计算面积");
        JPanel pNorth = new JPanel();
        pNorth.add(new JLabel("上底:"));
        pNorth.add(textAbove);
        pNorth.add(new JLabel("下底:"));
        pNorth.add(textBottom);
        pNorth.add(new JLabel("高:"));
        pNorth.add(textHeight);
        pNorth.add(controlButton);
        controlButton.addActionListener(this);
        add(pNorth, BorderLayout.NORTH);
        add(new JScrollPane(showArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double above = Double.parseDouble(textAbove.getText().trim());
            double bottom = Double.parseDouble(textBottom.getText().trim());
            double height = Double.parseDouble(textHeight.getText().trim());
            laden.setAbove(above);
            laden.setBottom(bottom);
            laden.setHeight(height);
            double area = laden.getArea();
            showArea.append("面积:" + area + "\n");
        } catch (Exception ex) {
            showArea.append("\n" + ex + "\n");
        }
    }
}

class TrapezoidLaden {
    double above, bottom, height;

    public double getArea() {
        return (above + bottom) * height / 2.0;
    }

    public void setAbove(double a) {
        above = a;
    }

    public void setBottom(double b) {
        bottom = b;
    }

    public void setHeight(double c) {
        height = c;
    }
}