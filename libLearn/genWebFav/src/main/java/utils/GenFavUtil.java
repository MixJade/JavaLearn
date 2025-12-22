package utils;

import entiy.FavUrl;
import entiy.Lv1Dir;
import entiy.Lv2Dir;
import enums.IcoEnum;
import example.IFavData;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 生成收藏夹工具
 *
 * @since 2025-12-22 09:17:24
 */
public class GenFavUtil {
    public static void genFav(IFavData favData) {
        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("lv1DirList", favData.lv1DirList());
        dataModel.put("htmlTit", favData.title());
        dataModel.put("mainIco", favData.mainIco());

        // 加载模板，输出文本
        String resStr = FtlUtil.fillTempStr(dataModel, "tableFav.html.ftl");
        ToFileUtil.toFilePath(resStr, Path.of(favData.favDir(), favData.favName()));
        ToFileUtil.toFilePath("此处图片由程序复制而来", Path.of(favData.favDir(), "logos", "readme.txt"));

        // 提取需要复制的图片
        Set<IcoEnum> icoEnumSet = new HashSet<>();
        icoEnumSet.add(favData.mainIco());
        for (Lv1Dir lv1Dir : favData.lv1DirList()) {
            for (Lv2Dir lv2Dir : lv1Dir.lv2Dirs()) {
                for (FavUrl favUrl : lv2Dir.favUrls()) {
                    icoEnumSet.add(favUrl.icoEnum());
                }
            }
        }
        // 复制图片到目标文件夹
        for (IcoEnum icoEnum : icoEnumSet) {
            Path sourcePath = Path.of("src", "main", "resources", "logos", icoEnum.getIcoName());
            Path targetPath = Path.of(favData.favDir(), "logos", icoEnum.getIcoName());
            ToFileUtil.copyImage(sourcePath, targetPath);
        }
    }
}
