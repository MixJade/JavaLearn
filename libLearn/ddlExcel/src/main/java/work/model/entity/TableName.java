package work.model.entity;

/**
 * 表的名称
 */
public class TableName {
    private String tableName; // 表的英文名
    private String comments; // 表注释

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
