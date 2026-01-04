package example;

import entiy.FavUrl;
import entiy.Lv1Dir;
import entiy.Lv2Dir;
import enums.IcoEnum;
import utils.GenFavUtil;

import java.util.ArrayList;
import java.util.List;

public class FavDataImpl implements IFavData {
    @Override
    public String favDir() {
        // 在根目录创建文件夹
        return "/genFav/demo";
    }

    @Override
    public String favName() {
        return "favTest.html";
    }

    @Override
    public String title() {
        return "收藏表格";
    }

    @Override
    public IcoEnum mainIco() {
        return IcoEnum.web;
    }

    @Override
    public List<Lv1Dir> lv1DirList() {
        List<Lv1Dir> lv1DirList = new ArrayList<>();
        // 一级菜单：初始教程
        lv1DirList.add(new Lv1Dir("初始教程", "2024-6-11 16:32", new Lv2Dir[]{
                new Lv2Dir("参考网站", new FavUrl[]{
                        new FavUrl("Vue上手", "https://cn.vuejs.org/guide/quick-start.html", IcoEnum.vue, "一切根基", "测试复制"),
                        new FavUrl("Element-Plus", "https://element-plus.org/zh-CN/", IcoEnum.elementPlus, "项目组件库", ""),
                        new FavUrl("Element的Icon", "https://element-plus.org/zh-CN/component/icon.html", IcoEnum.elementPlus, "美化图标", ""),
                }),
                new Lv2Dir("样式参考", new FavUrl[]{
                        new FavUrl("Sass官网教程", "https://www.sass.hk/docs/", IcoEnum.sass, "但不建议看这个", ""),
                        new FavUrl("Sass菜鸟教程", "https://www.runoob.com/sass/sass-install.html", IcoEnum.sass, "这个写的不错", ""),
                        new FavUrl("bootstrap官网", "https://www.bootstrap.cn/doc/read/113.html", IcoEnum.bootstrap, "一些样式要从这里来", ""),
                }),
                new Lv2Dir("框架官网", new FavUrl[]{
                        new FavUrl("Axios官网", "https://www.axios-http.cn/docs/intro", IcoEnum.axios, "前端请求", ""),
                        new FavUrl("ESLint官网", "https://zh-hans.eslint.org/docs/latest/use/getting-started", IcoEnum.eslint, "格式化工具", ""),
                }),
        }));
        // 一级菜单：补充知识
        lv1DirList.add(new Lv1Dir("补充知识", "2024-6-11 17:52", new Lv2Dir[]{
                new Lv2Dir("前端知识", new FavUrl[]{
                        new FavUrl("计算属性传参", "https://blog.csdn.net/qq_42231156/article/details/124506627", IcoEnum.csdn, "", ""),
                        new FavUrl("div换行设置", "https://blog.csdn.net/GraySakura/article/details/104821218", IcoEnum.csdn, "", ""),
                        new FavUrl("Vue3关于路由动画的注意事项", "https://juejin.cn/post/7074448287352225823", IcoEnum.juejin, "", ""),
                        new FavUrl("Vue3关于滚动条的细节", "https://juejin.cn/post/7137460519497105439", IcoEnum.juejin, "包括flex翻转，用做聊天页面", ""),
                        new FavUrl("Vue3的h函数", "https://juejin.cn/post/7132656370901336101", IcoEnum.juejin, "", ""),
                }),
                new Lv2Dir("样式知识", new FavUrl[]{
                        new FavUrl("CSS自定属性", "https://developer.mozilla.org/zh-CN/docs/Web/CSS/Using_CSS_custom_properties", IcoEnum.web, "方便调配色", ""),
                        new FavUrl("CSS波浪加号大于号", "https://www.jianshu.com/p/d875f680fc6b", IcoEnum.web, "一个有用的语法", ""),
                        new FavUrl("前端尺寸vw|vh|rem|em", "https://zhuanlan.zhihu.com/p/96721026", IcoEnum.web, "了解即可", ""),
                }),
                new Lv2Dir("杂项", new FavUrl[]{
                        new FavUrl("无Java环境下运行jar文件", "https://blog.csdn.net/hunt_er/article/details/81427804", IcoEnum.csdn, "感觉不如IDE", ""),
                        new FavUrl("别人的仓库", "https://gitee.com/haojunchuan/web_every_day/tree/master/parallax_website", IcoEnum.gitlab, "我会吸收一些东西", ""),
                }),
        }));
        // 一级菜单：小小工具
        lv1DirList.add(new Lv1Dir("小小工具", "2024-6-11 21:26", new Lv2Dir[]{
                new Lv2Dir("配色", new FavUrl[]{
                        new FavUrl("ElementPlus配色", "https://element-plus.gitee.io/zh-CN/component/color.html", IcoEnum.elementPlus, "一个规范", ""),
                        new FavUrl("颜色代码", "https://www.cnblogs.com/5H5H/p/9784015.html", IcoEnum.web, "各种颜色的英文名称", ""),
                }),
                new Lv2Dir("样式", new FavUrl[]{
                        new FavUrl("UI_Verse", "https://uiverse.io/cards", IcoEnum.web, "不错的css样式库，但代码有冗余，记得清理", ""),
                        new FavUrl("BootStrap的css", "https://cdn.bootcdn.net/ajax/libs/bootstrap/5.2.3/css/bootstrap.css", IcoEnum.bootstrap, "老大哥，直接copy", ""),
                }),
                new Lv2Dir("工具", new FavUrl[]{
                        new FavUrl("Json转Ts接口", "https://tooltt.com/json2typescript/", IcoEnum.web, "在线网站，JSON键要加双引号", ""),
                }),
                new Lv2Dir("测试图标", new FavUrl[]{
                        new FavUrl("container", "#", IcoEnum.container, "", ""),
                        new FavUrl("crc", "#", IcoEnum.crc, "", ""),
                        new FavUrl("dhc", "#", IcoEnum.dhc, "", ""),
                        new FavUrl("excel", "#", IcoEnum.excel, "", ""),
                        new FavUrl("excelplus", "#", IcoEnum.excelPlus, "", ""),
                        new FavUrl("gitlab", "#", IcoEnum.gitlab, "", ""),
                        new FavUrl("fotic", "#", IcoEnum.fotic, "", ""),
                        new FavUrl("kdocs", "#", IcoEnum.kdocs, "", ""),
                        new FavUrl("liucheng", "#", IcoEnum.liucheng, "", ""),
                        new FavUrl("mq", "#", IcoEnum.mq, "", ""),
                        new FavUrl("nacos", "#", IcoEnum.nacos, "", ""),
                        new FavUrl("nexus", "#", IcoEnum.nexus, "", ""),
                        new FavUrl("oracle", "#", IcoEnum.oracle, "", ""),
                        new FavUrl("pdf", "#", IcoEnum.pdf, "", ""),
                        new FavUrl("swagger", "#", IcoEnum.swagger, "", ""),
                        new FavUrl("word", "#", IcoEnum.word, "", ""),
                        new FavUrl("WuliHub", "#", IcoEnum.wuliHub, "", ""),
                        new FavUrl("xxlJob", "#", IcoEnum.xxlJob, "", ""),
                        new FavUrl("禅道", "#", IcoEnum.zentao, "", ""),
                }),
        }));

        return lv1DirList;
    }

    public static void main(String[] args) {
        IFavData favData = new FavDataImpl();
        GenFavUtil.genFav(favData);
    }
}
