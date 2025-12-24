package tableTes;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Swing表格样例
 *
 * @since 2024-08-30 16:09:57
 */
public class TableExample {
    private final JTextField nameInput = new JTextField(),
            ageInput = new JTextField();
    private int selectRow = 0;
    private MyData selectData = new MyData("默认名称", "0", false);

    public TableExample() {
        FlatMacLightLaf.setup();
        // 窗口属性
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout()); // 东南西北布局
        frame.setTitle("表格样例尝试");

        // 设置图标
        ImageIcon icon = new ImageIcon("src/main/resources/favor.jpg");
        frame.setIconImage(icon.getImage());

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
                ageInput.setText(String.valueOf(selectData.age()));
                // 打印选中行的数据
                System.out.println(rowData);
            }
        });

        // 设置删除按钮
        JButton delSixBtn = new JButton("删除所选");
        delSixBtn.addActionListener(e -> tableModel.removeRowData(selectRow));
        // 设置更新按钮
        JButton updSixBtn = new JButton("更新所选");
        updSixBtn.addActionListener(e -> tableModel.updateRowData(selectRow, new MyData(
                nameInput.getText(), ageInput.getText(), selectData.sex()
        )));
        // 按钮加入顶部面板
        JPanel topPanel = new JPanel();
        topPanel.add(delSixBtn);
        topPanel.add(updSixBtn);

        // 底部分页条
        JPanel bottomPanel = new JPanel();
        JLabel pageLabel = new JLabel("这是分页条");
        bottomPanel.add(pageLabel);

        // 右侧面板
        JPanel rightPanel = new JPanel(); // 布局GridLayout，每行有2列;
        // 姓名
        JLabel nameLabel = new JLabel("姓名");
        nameInput.setText(selectData.name());
        rightPanel.add(nameLabel);
        rightPanel.add(nameInput);
        // 年龄
        JLabel ageLabel = new JLabel("年龄");
        ageInput.setText(selectData.age());
        rightPanel.add(ageLabel);
        rightPanel.add(ageInput);

        // 最后添加组件
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(sp, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.setSize(500, 300);
        // frame.pack(); // 调整框架的大小以适应其所有的子组件
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TableExample();
    }
}

class MyTableModel extends AbstractTableModel {
    private final List<MyData> data;
    private final String[] columnNames = {"姓名", "年龄", "性别"};

    MyTableModel(List<MyData> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        MyData myData = data.get(row);
        return switch (col) {
            case 0 -> myData.name();
            case 1 -> myData.age();
            case 2 -> myData.sex() ? "男" : "女";
            default -> null;
        };
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public MyData getRowData(int index) {
        return data.get(index);
    }

    public void removeRowData(int index) {
        System.out.println("删除:" + getRowData(index).name());
        data.remove(index);
        // 最后调用这个方法来刷新整个表格数据
        fireTableDataChanged();
    }

    public void updateRowData(int index, MyData myData) {
        System.out.println("更新:" + getRowData(index).name());
        data.set(index, myData);
        // 最后调用这个方法来刷新一行数据
        fireTableRowsUpdated(index, index);
    }
}