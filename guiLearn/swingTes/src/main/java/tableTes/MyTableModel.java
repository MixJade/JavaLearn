package tableTes;

import javax.swing.table.AbstractTableModel;
import java.util.List;

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
        if (index == -1) return;
        System.out.println("删除:" + getRowData(index).name());
        data.remove(index);
        // 最后调用这个方法来刷新整个表格数据
        fireTableDataChanged();
    }

    public void updateRowData(int index, MyData myData) {
        if (index == -1) return;
        System.out.println("更新:" + getRowData(index).name());
        data.set(index, myData);
        // 最后调用这个方法来刷新一行数据
        fireTableRowsUpdated(index, index);
    }

    public void addRowData(MyData myData) {
        System.out.println("新增:" + myData.name());
        data.add(myData);
        // 最后调用这个方法来刷新整个表格数据
        fireTableDataChanged();
    }
}
