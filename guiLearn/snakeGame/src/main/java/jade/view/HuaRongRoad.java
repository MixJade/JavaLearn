package jade.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HuaRongRoad extends JFrame implements MouseListener, KeyListener, ActionListener {
    PersonBtn[] personBtns = new PersonBtn[10];
    JButton left, right, above, below;
    JButton restart = new JButton("重新开始");

    public HuaRongRoad() {
        init();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 320, 500);
        setVisible(true);
        validate();
    }

    public void init() {
        setTitle("华容道");
        setLayout(null);
        add(restart);
        restart.setBounds(100, 320, 120, 35);
        restart.addActionListener(this);
        String[] name = {"曹操", "关羽", "张", "刘", "周", "黄", "兵", "兵", "兵", "兵"};
        for (int k = 0; k < name.length; k++) {
            personBtns[k] = new PersonBtn(k, name[k]);
            personBtns[k].addMouseListener(this);
            personBtns[k].addKeyListener(this);
            add(personBtns[k]);
        }
        personBtns[0].setBounds(104, 54, 100, 100);
        personBtns[1].setBounds(104, 154, 100, 50);
        personBtns[2].setBounds(54, 154, 50, 100);
        personBtns[3].setBounds(204, 154, 50, 100);
        personBtns[4].setBounds(54, 54, 50, 100);
        personBtns[5].setBounds(204, 54, 50, 100);
        personBtns[6].setBounds(54, 254, 50, 50);
        personBtns[7].setBounds(204, 254, 50, 50);
        personBtns[8].setBounds(104, 204, 50, 50);
        personBtns[9].setBounds(154, 204, 50, 50);
        personBtns[9].requestFocus();
        left = new JButton();
        right = new JButton();
        above = new JButton();
        below = new JButton();
        add(left);
        add(right);
        add(above);
        add(below);
        left.setBounds(49, 49, 5, 260);
        right.setBounds(254, 49, 5, 260);
        above.setBounds(49, 49, 210, 5);
        below.setBounds(49, 304, 210, 5);
        validate();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PersonBtn man = (PersonBtn) e.getSource();
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            go(man, below);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            go(man, above);
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            go(man, left);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            go(man, right);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        PersonBtn man = (PersonBtn) e.getSource();
        int x = e.getX(),
                y = e.getY();
        int w = man.getBounds().width;
        int h = man.getBounds().height;
        if (y > h / 2)
            go(man, below);
        if (y < h / 2)
            go(man, above);
        if (x < w / 2)
            go(man, left);
        if (x > w / 2)
            go(man, right);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void go(PersonBtn man, JButton direction) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        if (direction == below)
            y = y + 50;
        else if (direction == above)
            y = y - 50;
        else if (direction == left)
            x = x - 50;
        else if (direction == right)
            x = x + 50;
        manRect.setLocation(x, y);
        Rectangle directionRect = direction.getBounds();
        for (int k = 0; k < 10; k++) {
            Rectangle personRect = personBtns[k].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != k))
                move = false;
        }
        if (manRect.intersects(directionRect))
            move = false;
        if (move)
            man.setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new HuaRongRoad();
    }
}
