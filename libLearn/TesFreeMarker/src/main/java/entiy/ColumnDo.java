package entiy;


public class ColumnDo {
    String columnName;
    String comment;

    public ColumnDo(String columnName, String comment) {
        this.columnName = columnName;
        this.comment = comment;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getComment() {
        return comment;
    }
}