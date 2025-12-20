package example;

import entiy.Lv1Dir;
import enums.IcoEnum;

import java.util.List;

/**
 * 获取收藏夹的数据
 *
 * @since 2025-12-19 16:50:32
 */
public interface IFavData {
    // 输出目录
    String favDir();

    // 生成的html名字
    String favName();

    String title();

    IcoEnum mainIco();

    List<Lv1Dir> lv1DirList();
}
