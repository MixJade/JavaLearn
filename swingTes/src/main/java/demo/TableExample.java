package demo;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

/**
 * Swing表格样例
 *
 * @since 2024-08-30 16:09:57
 */
public class TableExample {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        JFrame frame = new JFrame();

        Object[][] rowData = {
                {"John", 20, "Male"},
                {"Doe", 25, "Female"},
                {"Jane", 23, "Female"},
                // add more rows as needed
        };

        String[] columnNames = {"Name", "Age", "Gender"};

        JTable table = new JTable(rowData, columnNames);

        JScrollPane sp = new JScrollPane(table);

        frame.add(sp);
        frame.setSize(500, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ListSelectionModel model = table.getSelectionModel();

        model.addListSelectionListener(e -> {
            // 如果还在调整中，直接返回
            if (e.getValueIsAdjusting()) {
                return;
            }

            int row = table.getSelectedRow();
            if (row != -1) {
                // 打印选中行的数据
                System.out.println(rowData[row][0] + ", " + rowData[row][1]);
            }
        });
    }
}