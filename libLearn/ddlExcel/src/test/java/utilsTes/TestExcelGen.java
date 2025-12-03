package utilsTes;

import org.junit.Test;
import work.model.dto.SheetDo;
import work.utils.ExcelGen;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试关于excel文件的生成
 *
 * @since 2025-07-19 18:36:59
 */
public class TestExcelGen {
    @Test
    public void testGenExcel() {
        // 设置sheet1表头
        String[] sheet1Head = {"ID", "姓名", "职位", "工资"};
        // 设置sheet1数据
        List<String[]> sheet1Data = new ArrayList<>();
        sheet1Data.add(new String[]{"1", "张三", "开发工程师", "12000"});
        sheet1Data.add(new String[]{"2", "李四", "产品经理", "15000"});
        sheet1Data.add(new String[]{"3", "王五", "测试工程师", "11000"});

        // 设置sheet2表头
        String[] sheet2Head = {"部门ID", "部门名称", "负责人"};
        // 设置sheet1数据
        List<String[]> sheet2Data = new ArrayList<>();
        sheet2Data.add(new String[]{"101", "技术部", "赵经理"});
        sheet2Data.add(new String[]{"102", "产品部", "钱经理"});
        sheet2Data.add(new String[]{"103", "测试部", "孙经理"});

        SheetDo sheet1Do = new SheetDo("员工信息", sheet1Head, sheet1Data);
        SheetDo sheet2Do = new SheetDo("部门数据", sheet2Head, sheet2Data);

        List<SheetDo> sheetDoList = new ArrayList<>(2);
        sheetDoList.add(sheet1Do);
        sheetDoList.add(sheet2Do);
        ExcelGen.creatExcel("测试生成_结果.xlsx", sheetDoList);
    }
}
