package work.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import work.model.dto.SheetDo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 生成excel
 *
 * @since 2025-07-19 18:36:59
 */
public final class ExcelGen {
    public static void creatExcel(String excelName, List<SheetDo> sheetDos) {
        // 创建新的工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建表头样式（加粗）
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            setBorderStyle(headerStyle);

            // 创建普通单元格样式（有边框）
            CellStyle normalStyle = workbook.createCellStyle();
            setBorderStyle(normalStyle);

            for (SheetDo sheetDo : sheetDos) {
                // 使用POI工具类自动处理：替换非法字符 + 截断到31字符
                String sheetName = WorkbookUtil.createSafeSheetName(sheetDo.sheetName());
                // 创建sheet页并命名
                Sheet se = workbook.createSheet(sheetName);
                createSheet(se, sheetDo.headers(), sheetDo.data(), headerStyle, normalStyle);
            }

            // 写入文件
            try (FileOutputStream fileOut = new FileOutputStream("生成结果/" + excelName)) {
                workbook.write(fileOut);
                System.out.printf("Excel文件:【生成结果/%s】已成功生成！%n", excelName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setBorderStyle(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private static void createSheet(Sheet sheet, String[] headers, List<String[]> data, CellStyle headerStyle, CellStyle normalStyle) {
        // 创建表头行
        Row headerRow = sheet.createRow(0);

        // 设置表头
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 添加数据行
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data.get(i).length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data.get(i)[j]);
                cell.setCellStyle(normalStyle);
            }
        }

        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            // 手动增加列宽，这里乘以2作为示例
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 2);
        }
    }
}