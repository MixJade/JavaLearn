package fartherUseSon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 子组件
public class ChildComponent extends JPanel implements ActionListener {
    private CustomListener listener;

    public ChildComponent() {
        add(new JLabel("这是子组件"));
        JButton jButton = new JButton("触发事件");
        jButton.addActionListener(this);
        add(jButton);
    }

    public void setCustomListener(CustomListener listener) { //为子组件设置监听器
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("这是子组件自己的方法");
        if (listener != null) {
            listener.customEventOccurred(); //触发事件
        }
    }
}