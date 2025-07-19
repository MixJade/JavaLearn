package work.model.entity;

import lombok.Data;

@Data
public class TableDDL {
    private String columnId;
    private String columnName;
    private String dataType;
    private String comments;
    private String isPri;
    private String isNotNull;
}
