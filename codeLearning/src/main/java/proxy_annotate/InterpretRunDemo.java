package proxy_annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface MyTest {
}

public class InterpretRunDemo {
    public static void main(String[] args) {
        InterpretRunDemo runDemo = new InterpretRunDemo();
        Class<InterpretRunDemo> c02 = InterpretRunDemo.class;
        Method[] methods = c02.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                try {
                    method.invoke(runDemo);//执行该方法
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //只有加了MyTest注解的方法才会运行，为什么会这样？秘诀在Main方法里
    @MyTest
    @SuppressWarnings("unused")
    public void explainAttempt01() {
        System.out.println("===Method-First===");
    }

    @SuppressWarnings("unused")
    public void explainAttempt02() {
        System.out.println("===Method-Second===");
    }

    @MyTest
    @SuppressWarnings("unused")
    public void explainAttempt03() {
        System.out.println("===Method-Third===");
    }

}