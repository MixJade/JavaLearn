package tableTes;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Swing表格样例
 *
 * @since 2024-08-30 16:09:57
 */
public class TableExample extends JFrame {
    private final JTextField nameInput = new JTextField(),
            ageInput = new JTextField();
    private int selectRow = -1;
    private MyData selectData = new MyData("默认名称", "0", false);
    FormDialog dialog = new FormDialog(this, "用户信息编辑");

    public TableExample() {
        FlatMacLightLaf.setup();
        // 窗口属性
        this.setLayout(new BorderLayout()); // 东南西北布局
        this.setTitle("表格样例尝试");

        // 设置图标
        ImageIcon icon = new ImageIcon("src/main/resources/favor.jpg");
        this.setIconImage(icon.getImage());

        // 设置表格数据(其实可以用二维数组直接上)
        List<MyData> data = MyData.getMyDataList();
        MyTableModel tableModel = new MyTableModel(data);
        // 初始化表格
        JTable table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);

        // 设置表格选中事件
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            // 如果还在调整中，直接返回
            if (e.getValueIsAdjusting()) {
                return;
            }
            int row = table.getSelectedRow();
            if (row != -1) {
                selectRow = row;
                MyData rowData = tableModel.getRowData(row);
                selectData = rowData;
                nameInput.setText(selectData.name());
                ageInput.setText(selectData.age());
                // 打印选中行的数据
                System.out.println(rowData);
            }
        });

        // 设置删除按钮
        JButton delSixBtn = new JButton("删除所选");
        delSixBtn.addActionListener(e -> tableModel.removeRowData(selectRow));
        // 设置更新按钮
        JButton updSixBtn = new JButton("更新所选");
        updSixBtn.addActionListener(e -> {
            if (selectRow == -1) return;
            // 创建并显示弹窗
            dialog.setInitialData(selectData);
            dialog.setVisible(true);
            // 获取弹窗返回结果
            if (dialog.isConfirmed()) {
                tableModel.updateRowData(selectRow, dialog.getFormData());
            }
        });
        // 按钮加入顶部面板
        JPanel topPanel = new JPanel();
        topPanel.add(delSixBtn);
        topPanel.add(updSixBtn);

        // 底部分页条
        JPanel bottomPanel = new JPanel();
        JLabel pageLabel = new JLabel("@2024 - 2025");
        bottomPanel.add(pageLabel);

        // 最后添加组件
        this.add(topPanel, BorderLayout.NORTH);
        this.add(sp, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
//        this.add(rightPanel, BorderLayout.EAST);
        this.setSize(500, 300);
        // this.pack(); // 调整框架的大小以适应其所有的子组件
        this.setVisible(true);
        this.setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TableExample();
    }
}