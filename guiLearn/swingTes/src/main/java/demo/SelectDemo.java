package demo;

import javax.swing.*;
import java.awt.*;

public class SelectDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建主窗口
            JFrame frame = new JFrame("Swing下拉框基础使用");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

            // 1. 创建下拉框 - 方式1：直接传入选项数组
            // 1. 创建Key-Value格式的选项列表
            KeyValueItem[] cityItems = {
                    new KeyValueItem("010", "北京"),
                    new KeyValueItem("021", "上海"),
                    new KeyValueItem("020", "广州"),
                    new KeyValueItem("0755", "深圳")
            };
            JComboBox<KeyValueItem> cityComboBox = new JComboBox<>(cityItems);

            // 可选：设置默认选中项（索引从0开始）
            cityComboBox.setSelectedItem(new KeyValueItem("010", "北京"));

            // 2. 创建下拉框 - 方式2：先创建空下拉框，再动态添加选项
            JComboBox<String> genderComboBox = new JComboBox<>();
            genderComboBox.addItem("男");
            genderComboBox.addItem("女");
            genderComboBox.addItem("保密");
            // 可选：设置默认选中项（索引从0开始）
            genderComboBox.setSelectedIndex(1); // 默认选中第二个

            // 3. 添加选中事件监听
            JButton getValueBtn = new JButton("获取选中值");
            getValueBtn.addActionListener(e -> {
                KeyValueItem selectedCity = (KeyValueItem) cityComboBox.getSelectedItem(); // 获取选中的对象
                String selectedGender = (String) genderComboBox.getSelectedItem();

                // 弹窗展示结果
                assert selectedCity != null;
                JOptionPane.showMessageDialog(frame,
                        "选中城市：" + selectedCity.key() + "_" + selectedCity.value() +
                                "\n选中性别：" + selectedGender,
                        "选中结果",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            // 添加组件到窗口
            frame.add(new JLabel("城市选择："));
            frame.add(cityComboBox);
            frame.add(new JLabel("性别选择："));
            frame.add(genderComboBox);
            frame.add(getValueBtn);

            frame.setVisible(true);
        });
    }
}

record KeyValueItem(String key, String value) {
    // 重写toString，让下拉框显示value
    @Override
    public String toString() {
        return this.value;
    }
}