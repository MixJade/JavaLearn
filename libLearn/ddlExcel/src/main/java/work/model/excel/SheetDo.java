package work.model.excel;

import java.util.List;

/**
 * 生成xlsx时的sheet页数据
 *
 * @param sheetName sheet页名字
 * @param headers   各列列名
 * @param data      表中数据
 */
public record SheetDo(String sheetName, String[] headers, List<String[]> data) {
}
