package proxy_annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

@Target({ElementType.METHOD, ElementType.FIELD})//Method为方法，Field为成员变量,Type为类
@Retention(RetentionPolicy.RUNTIME)//runtime存活到运行时
@interface MyBook {
    String name();

    String[] authors();

    double price() default 9.9;//default默认值，注解的时候可以不写
}


@Retention(RetentionPolicy.RUNTIME)//有这个注解才能活到反射阶段
@interface JustValue {
    String value();//关键字value，在注解的时候可以不加前缀，但如果不想加前缀，注解里面只能有一个value
}

public class Interpretation01 {
    public static void main(String[] args) {
        Interpretation01 interpretation = new Interpretation01();
        try {
            interpretation.parseMethod();
        } catch (NoSuchMethodException e) {
            System.out.println("没有找到方法");
        }
    }

    public void parseMethod() throws NoSuchMethodException {
        Class<AttemptInterpret> c01 = AttemptInterpret.class;

        //below关于类的注解
        if (c01.isAnnotationPresent(JustValue.class)) {
            JustValue jv = c01.getDeclaredAnnotation(JustValue.class);
            System.out.println("类注解是： ");
            System.out.println(jv.value());
        }

        //below关于方法的注解
        Method m01 = c01.getDeclaredMethod("attemptInterpretation");
        if (m01.isAnnotationPresent(MyBook.class)) {
            MyBook book = m01.getDeclaredAnnotation(MyBook.class);
            System.out.println("方法注解是：");
            System.out.println(book.name() + "  " + Arrays.toString(book.authors()) + "  " + book.price());
        }
    }
}

/*@MyBook(name = "recognition",authors = {"lite","jade"})
----->会报错，这个注解被打了标记只能用于方法和成员变量
 */
@JustValue("这个注解没有前缀哦")
class AttemptInterpret {
    @MyBook(name = "《连城诀》", authors = {"狄云"})//用于成员变量的注解
    static int number = 90;

    @MyBook(name = "《九阴真经》", authors = {"黄棠", "达摩"}, price = 100.0)//用于方法的注解
    @JustValue("只要我不加Target，我就是自由的")//这里不会被类注解反射到，说明注解的反射是非常严格的
    public void attemptInterpretation() {
        int methodNumber = 99;//这里MyBook注解不了，因为不是类变量
        System.out.println("Interpretation注解" + methodNumber + number);
    }
}