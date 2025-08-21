package work.model.entity;

/**
 * 表的字段详情
 */
public class TableDDL {
    private String columnId; // 列排序
    private String columnName; // 列名
    private String dataType; // 数据类型
    private String comments; // 注释
    private String isPri; // 是否主键
    private String isNotNull; // 是否非空

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIsPri() {
        return isPri;
    }

    public void setIsPri(String isPri) {
        this.isPri = isPri;
    }

    public String getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(String isNotNull) {
        this.isNotNull = isNotNull;
    }
}
