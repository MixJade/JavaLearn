package demo;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
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
        frame.setLayout(new FlowLayout()); // 流式布局
        frame.setTitle("表格样例尝试");

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

        frame.add(delSixBtn);
        frame.add(sp);
        frame.setSize(500, 300);
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
        // 最后调用这个方法来刷新数据
        fireTableDataChanged();
    }
}