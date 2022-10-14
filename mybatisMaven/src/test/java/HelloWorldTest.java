import attempt01.MybatisFirstDemo;
import attempt02.MybatisSecondDemo;
import attempt03.MybatisFirthDemo;
import attempt03.MybatisFourthDemo;
import attempt03.MybatisThirdDemo;
import attempt04.SeventhDemo;
import attempt04.SixthDemo;
import attempt05.EighthDemo;
import attempt05.NinthDemo;
import attempt05.TenthDemo;
import attempt06.EleventhDemo;
import attempt06.TwelveDemo;
import attempt07.ThirteenDemo;
import org.junit.Test;

public class HelloWorldTest {
    @Test
    public void theWorld() {
        System.out.println("114514TheWorld");
        System.out.println("炸瓦鲁多！！！！！！！！");
    }

    @Test
    public void demo01Test() {
        // 直接查询全部
        MybatisFirstDemo firstDemo = new MybatisFirstDemo();
        firstDemo.demo01();
    }

    @Test
    public void demo02Test() {
        // 通过接口查询全部，这是正规方法
        MybatisSecondDemo secondDemo = new MybatisSecondDemo();
        secondDemo.demo02();
    }

    @Test
    public void demo03Test() {
        // 单个条件查询，多个条件的散装参数的形式查询
        MybatisThirdDemo thirdDemo = new MybatisThirdDemo();
        thirdDemo.queryById(1);
        thirdDemo.queryBySociety("轻音部");
        thirdDemo.queryByTwoSixty(60, 60);
    }

    @Test
    public void demo04Test() {
        // 模糊查询，且用实体类封装参数
        // 注意是模糊查询，会返回所有名字里面包括了”洪七“的人
        MybatisFourthDemo fourthDemo = new MybatisFourthDemo();
        fourthDemo.queryDimObject("洪七", 30, 30);
    }

    @Test
    public void demo05Test() {
        // 用Map封装参数，注意：映射，接口，实现的java文件，都要改
        MybatisFirthDemo firthDemo = new MybatisFirthDemo();
        firthDemo.queryDimMap("洪七", 30, 30);
    }

    @Test
    public void demo06Test() {
        // 关于缺失条件时的查询。
        // 注意，这里就显示出包装类的重要性了，平常的int根本不能设置为null。
        SixthDemo sixthDemo = new SixthDemo();
        // 查询全部
        sixthDemo.queryLack(null, null, null);
        // 查询所有英语及格人数
        sixthDemo.queryLack(null, 60, null);
    }

    @Test
    public void demo07Test() {
        // 单条件选择查询，事实证明也可以多个条件
        SeventhDemo seventhDemo = new SeventhDemo();
        seventhDemo.queryByName("洪七");
        seventhDemo.queryByEnglish(80);
        seventhDemo.queryByMath(40);
        seventhDemo.queryByEnglishMath(60, 60);
    }

    @Test
    public void demo08Test() {
        // 插入操作,boolean数据会变成0与1，当然也可以反过来
        // SQL中的date数据也可以用String插入
        EighthDemo eighthDemo = new EighthDemo();
        for (int i = 0; i < 5; i++) {
            // 插入失败的话id也会计数，可以看到尚迪的id不是跟叶叔连着的
            eighthDemo.addSQL("叶叔", 1, 50, 40, 1.65, "1999-09-22", 8000);
        }
        eighthDemo.addSQL("尚迪", 1, 60, 60, 1.85, "1900-05-22", 8000);
    }

    @Test
    public void demo09Test() {
        // 静态修改
        NinthDemo ninthDemo = new NinthDemo();
        ninthDemo.updateSQL("叶叔", 100, 100);
    }

    @Test
    public void demo10Test() {
        // 动态修改
        TenthDemo tenthDemo = new TenthDemo();
        tenthDemo.updateVaried("尚迪", null, 80);
        // where用like，是可以做到修改两行的，当然，我用的是等号
        tenthDemo.updateVaried("洪七", null, 80);
    }

    @Test
    public void demo11Test() {
        // 删除单个，当然得提交事务
        EleventhDemo eleventhDemo = new EleventhDemo();
        eleventhDemo.deleteOrigin("叶叔");
    }

    @Test
    public void demo12Test(){
        // 批量删除，涉及到重写默认数组名，以及新的映射文件语法
        TwelveDemo twelveDemo=new TwelveDemo();
        twelveDemo.deleteGroup(new String[]{"尚迪","叶叔","洪七"});
    }

    @Test
    public void demo13Test(){
        // 注解开发，只需要在接口文件中通过注解定义，不用通过映射文件
        ThirteenDemo thirteenDemo=new ThirteenDemo();
        thirteenDemo.queryByExplainId(13);
        thirteenDemo.queryByExplainSociety("轻音部");
    }

    @Test
    public void saveCodeTest() {
        // 保存来自不易的代码
        SaveCode02 c02 = new SaveCode02("C:\\Users\\11141\\Documents\\MarkMixJade\\Mybatis笔记.md");
        System.out.println(c02.prospective());
    }
}
