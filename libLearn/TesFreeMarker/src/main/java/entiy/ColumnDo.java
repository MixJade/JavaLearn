package entiy;

import lombok.Getter;

@Getter
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
}