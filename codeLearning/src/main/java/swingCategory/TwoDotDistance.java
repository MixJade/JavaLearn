package swingCategory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TwoDotDistance {
    public static void main(String[] args) {
        Window win = new Window();
        win.setTitle("两点距离MVC版");
        win.setBounds(100, 100, 500, 200);
    }
}

class Window extends JFrame implements ActionListener {
    TwoDistance distance;//模型
    JTextField textAx, textBx, textAy, textBy;//视图
    JTextArea showArea;//视图
    JButton controlButton;//控制器

    Window() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        distance = new TwoDistance();
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

    public void actionPerformed(ActionEvent e) {
        try {
            double ax = Double.parseDouble(textAx.getText().trim());
            double ay = Double.parseDouble(textAy.getText().trim());
            double bx = Double.parseDouble(textBx.getText().trim());
            double by = Double.parseDouble(textBy.getText().trim());
            distance.setAx(ax);
            distance.setAy(ay);
            distance.setBx(bx);
            distance.setBy(by);
            double distance = this.distance.getDistance();
            showArea.append("距离:" + distance + "\n");
        } catch (Exception ex) {
            showArea.append("\n" + ex + "\n");
        }
    }
}

class TwoDistance {
    double ax, ay, bx, by;

    public double getDistance() {
        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }

    public void setAx(double a1) {
        ax = a1;
    }

    public void setAy(double a2) {
        ay = a2;
    }

    public void setBx(double b1) {
        bx = b1;
    }

    public void setBy(double b2) {
        by = b2;
    }
}
