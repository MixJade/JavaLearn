package pojo;

import java.util.List;

public class PageBean<T> {
    private int totalItem;//总记录数
    private List<T> rows;//携带数据

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
