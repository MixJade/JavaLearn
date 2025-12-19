package example;

import entiy.FavUrl;
import entiy.Lv1Dir;
import entiy.Lv2Dir;
import enums.IcoEnum;

import java.util.ArrayList;
import java.util.List;

public class FavDataImpl implements IFavData {
    @Override
    public String title() {
        return "收藏表格";
    }

    @Override
    public IcoEnum mainIco() {
        return IcoEnum.webPage;
    }

    @Override
    public List<Lv1Dir> lv1DirList() {
        List<Lv1Dir> lv1DirList = new ArrayList<>();
        // 一级菜单：初始教程
        lv1DirList.add(new Lv1Dir("初始教程", "2024-6-11 16:32", List.of(
                new Lv2Dir("参考网站", List.of(
                        new FavUrl("https://cn.vuejs.org/guide/quick-start.html", "Vue上手", IcoEnum.vue, "一切根基", "测试复制"),
                        new FavUrl("https://element-plus.org/zh-CN/", "Element-Plus", IcoEnum.elementPlus, "项目组件库", ""),
                        new FavUrl("https://element-plus.org/zh-CN/component/icon.html", "Element的Icon", IcoEnum.elementPlus, "美化图标", ""))),
                new Lv2Dir("样式参考", List.of(
                        new FavUrl("https://www.sass.hk/docs/", "Sass官网教程", IcoEnum.sass, "但不建议看这个", ""),
                        new FavUrl("https://www.runoob.com/sass/sass-install.html", "Sass菜鸟教程", IcoEnum.sass, "这个写的不错", ""),
                        new FavUrl("https://www.bootstrap.cn/doc/read/113.html", "bootstrap官网", IcoEnum.Bootstrap, "一些样式要从这里来", ""))),
                new Lv2Dir("框架官网", List.of(
                        new FavUrl("https://www.axios-http.cn/docs/intro", "Axios官网", IcoEnum.axios, "前端请求", ""),
                        new FavUrl("https://zh-hans.eslint.org/docs/latest/use/getting-started", "ESLint官网", IcoEnum.Eslint, "格式化工具", "")))
        )));
        // 一级菜单：补充知识
        lv1DirList.add(new Lv1Dir("补充知识", "2024-6-11 17:52", List.of(
                new Lv2Dir("前端知识", List.of(
                        new FavUrl("https://blog.csdn.net/qq_42231156/article/details/124506627", "计算属性传参", IcoEnum.csdn, "", ""),
                        new FavUrl("https://blog.csdn.net/GraySakura/article/details/104821218", "div换行设置", IcoEnum.csdn, "", ""),
                        new FavUrl("https://juejin.cn/post/7074448287352225823", "Vue3关于路由动画的注意事项", IcoEnum.juejin, "", ""),
                        new FavUrl("https://juejin.cn/post/7137460519497105439", "Vue3关于滚动条的细节", IcoEnum.juejin, "包括flex翻转，用做聊天页面", ""),
                        new FavUrl("https://juejin.cn/post/7132656370901336101", "Vue3的h函数", IcoEnum.juejin, "", ""))),
                new Lv2Dir("样式知识", List.of(
                        new FavUrl("https://developer.mozilla.org/zh-CN/docs/Web/CSS/Using_CSS_custom_properties", "CSS自定属性", IcoEnum.webPage, "方便调配色", ""),
                        new FavUrl("https://www.jianshu.com/p/d875f680fc6b", "CSS波浪加号大于号", IcoEnum.webPage, "一个有用的语法", ""),
                        new FavUrl("https://zhuanlan.zhihu.com/p/96721026", "前端尺寸vw|vh|rem|em", IcoEnum.webPage, "了解即可", ""))),
                new Lv2Dir("杂项", List.of(
                        new FavUrl("https://blog.csdn.net/hunt_er/article/details/81427804", "无Java环境下运行jar文件", IcoEnum.csdn, "感觉不如IDE", ""),
                        new FavUrl("https://gitee.com/haojunchuan/web_every_day/tree/master/parallax_website", "别人的仓库", IcoEnum.webPage, "我会吸收一些东西", "")))
        )));
        // 一级菜单：小小工具
        lv1DirList.add(new Lv1Dir("小小工具", "2024-6-11 21:26", List.of(
                new Lv2Dir("配色", List.of(
                        new FavUrl("https://element-plus.gitee.io/zh-CN/component/color.html", "ElementPlus配色", IcoEnum.elementPlus, "一个规范", ""),
                        new FavUrl("https://www.cnblogs.com/5H5H/p/9784015.html", "颜色代码", IcoEnum.webPage, "各种颜色的英文名称", ""))),
                new Lv2Dir("样式", List.of(
                        new FavUrl("https://uiverse.io/cards", "UI_Verse", IcoEnum.webPage, "不错的css样式库，但代码有冗余，记得清理", ""),
                        new FavUrl("https://cdn.bootcdn.net/ajax/libs/bootstrap/5.2.3/css/bootstrap.css", "BootStrap的css", IcoEnum.Bootstrap, "老大哥，直接copy", ""))),
                new Lv2Dir("工具", List.of(
                        new FavUrl("https://tooltt.com/json2typescript/", "Json转Ts接口", IcoEnum.webPage, "在线网站，JSON键要加双引号", "")))
        )));

        return lv1DirList;
    }
}
