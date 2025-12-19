import example.FavDataImpl;
import example.IFavData;
import utils.FtlUtil;

import java.util.HashMap;
import java.util.Map;

public class GenFavMain {
    public static void main(String[] args) {
        IFavData favData = new FavDataImpl();
        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("lv1DirList", favData.lv1DirList());
        dataModel.put("htmlTit", favData.title());
        dataModel.put("mainIco", favData.mainIco());

        // 加载模板，输出文本
        String resStr = FtlUtil.fillTempStr(dataModel, "tableFav.html.ftl");
        System.out.println(resStr);
    }
}
