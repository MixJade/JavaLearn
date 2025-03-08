package fartherUseSon;


import javax.swing.*;

//父组件
public class ParentComponent extends JFrame implements CustomListener {

    public ParentComponent() {
        setTitle("测试子组件触发父组件");
        ChildComponent childComponent = new ChildComponent();
        childComponent.setCustomListener(this); //在父组件中实现监听器并设置到子组件中
        add(childComponent);

        setSize(200, 300);
        // 启动窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ParentComponent();
    }

    @Override
    public void customEventOccurred() {
        System.out.println("父组件的方法被触发");
        JOptionPane.showMessageDialog(this, "父组件的方法被触发", "测试", JOptionPane.PLAIN_MESSAGE);
    }
}