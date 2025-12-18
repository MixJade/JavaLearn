package work.mapper;

import org.apache.ibatis.annotations.Mapper;
import work.model.entity.TableDDL;
import work.model.entity.TableName;

import java.util.List;


@Mapper
public interface DeMapper {

    // 查询表的DDL
    List<TableDDL> queryTableDDL(String tableName);

    // 查询表的名称
    TableName queryTableName(String tableName);
}
