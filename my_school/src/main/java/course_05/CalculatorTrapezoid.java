package course_05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 【5.2】【题】计算梯形面积
 * 参照例子15编写一个体现MVC结构的GUI程序。
 * 首先编写一个封装梯形类，然后再编写一个窗口。
 * 要求窗口使用三文本框和一个文本区为梯形对象中的数据提供视图，
 * 其中三个文本框用来显示和更新梯形对象的上底、下底和高；
 * 文本区对象用来显示梯形的面积。
 * 窗口中有一个按钮，用户单击该按钮后，程序用3个文本框中的数据分别作为梯形对象的上底、下底和高，
 * 并将计算出的梯形的面积显示在文本区中。
 *
 * @since 2022-4-28
 */
public class CalculatorTrapezoid extends JFrame implements ActionListener {
    JTextField textAbove, textBottom, textHeight;//视图
    JTextArea showArea;//视图
    JButton controlButton;//控制器
    CalculatorTrapezoid() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        CalculatorTrapezoid win = new CalculatorTrapezoid();
        win.setTitle("计算梯形面积");
        win.setBounds(100, 100, 420, 260);
    }

    void init() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double above = Double.parseDouble(textAbove.getText().trim());
            double bottom = Double.parseDouble(textBottom.getText().trim());
            double height = Double.parseDouble(textHeight.getText().trim());
            Trapezoid trapezoid = new Trapezoid(above, bottom, height);
            double area = trapezoid.getArea();
            showArea.append("面积:" + area + "\n");
        } catch (Exception ex) {
            showArea.append("\n" + ex + "\n");
        }
    }

    /**
     * 梯形实体类(私有内部类)
     *
     * @param above  上底
     * @param bottom 下底
     * @param height 高
     */
    private record Trapezoid(double above, double bottom, double height) {
        double getArea() {
            return (above + bottom) * height / 2.0;
        }
    }
}