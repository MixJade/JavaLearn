package interpretation;

import org.junit.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Interpretation("这是Junit框架的展示，Junit框架是一个很方便的测试框架。" +
        "要求先建立一个测试类，再添加测试方法，测试方法用被测试类的对象。" +
        "可以进行断言来验证输出结果是否符合预期" +
        "这里有Test,Before,After,BeforeClass,AfterClass五种标注")
public class TestCXKProxy {

    CXKPractice cxk = new CXKPractice("蔡徐坤", 2.5);

    @Before
    public void testBefore() {
        System.out.println("===Before标注的方法会在每一个方法执行之前执行一次");
    }

    @After
    public void testAfter() {
        System.out.println("===After标注的方法会在每一个方法执行之后执行一次");
    }

    @BeforeClass
    public static void testBeforeType() {
        System.out.println("===BeforeClass(只能标注静态方法）标注的方法会在测试类执行之前执行一次");
    }

    @AfterClass
    public static void testAfterType() {
        System.out.println("===AfterClass(只能标注静态方法）标注的方法会在测试类执行之后执行一次");
    }


    @Test
    public void CXKGetPracticeYear() {
        String rs1 = cxk.getPracticeYear();
        //进行预期结果的断言
        Assert.assertEquals("这不是我家坤坤！", "这位学员练习2.5年", rs1);
        CXKPractice sweetCandy = new CXKPractice("糖果超甜", 1.5);
        String rs2 = sweetCandy.getPracticeYear();
        Assert.assertEquals("这不是我家坤坤！", "这位学员练习2.5年", rs2);
    }

    @Test
    public void testSing() {
        cxk.sing();
        CXKPractice sweetCandy = new CXKPractice("糖果超甜", 1.5);
        sweetCandy.sing();
    }

    @Test
    public void testDance() {
        cxk.dance();
    }

    @Test
    public void testRap() {
        cxk.rap();
    }

    @Test
    public void testBasketball() {
        cxk.basketball();
    }


}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)//RetentionPolicy.SOURCE表示只存在java源文件
@interface Interpretation {
    String value();
}