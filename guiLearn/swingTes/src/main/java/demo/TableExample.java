package demo;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Swing表格样例
 *
 * @since 2024-08-30 16:09:57
 */
public class TableExample {
    public static void main(String[] args) {
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
                // 打印选中行的数据
                System.out.println(tableModel.getRowData(row));
            }
        });

        // 设置删除按钮
        JButton delSixBtn = new JButton("删除老六");
        delSixBtn.addActionListener(e -> tableModel.removeRowData(4));
        // 设置更新按钮
        JButton updSixBtn = new JButton("更新星君");
        updSixBtn.addActionListener(e -> tableModel.updateRowData(2));
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
        JTextField nameInput = new JTextField();
        rightPanel.add(nameLabel);
        rightPanel.add(nameInput);
        // 年龄(数字输入框)
        JLabel ageLabel = new JLabel("年龄");
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // 如果你想要提交编辑，你也可以在这里调用 formatter.setCommitsOnValidEdit(true);
        JFormattedTextField ageInput = new JFormattedTextField(formatter);
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
}

record MyData(String name, int age, boolean sex) {
    static List<MyData> getMyDataList() {
        ArrayList<MyData> myDataList = new ArrayList<>();
        myDataList.add(new MyData("张三", 23, true));
        myDataList.add(new MyData("李四", 34, true));
        myDataList.add(new MyData("亢龙星君", 323, false));
        myDataList.add(new MyData("王五", 55, true));
        myDataList.add(new MyData("老六", 66, false));
        return myDataList;
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

    public void updateRowData(int index) {
        System.out.println("更新:" + getRowData(index).name());
        data.set(index, new MyData("恶堕星君", 324, false));
        // 最后调用这个方法来刷新一行数据
        fireTableRowsUpdated(index, index);
    }
}