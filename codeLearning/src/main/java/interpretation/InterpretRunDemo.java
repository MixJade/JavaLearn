package interpretation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InterpretRunDemo {
    //只有加了MyTest注解的方法才会运行，为什么会这样？秘诀在Main方法里
    @MyTest
    public void explainAttempt01(){
        System.out.println("===Method-First===");
    }
     public void explainAttempt02(){
        System.out.println("===Method-Second===");
    }
    @MyTest
     public void explainAttempt03(){
        System.out.println("===Method-Third===");
    }

    public static void main(String[] args) {
        InterpretRunDemo runDemo = new InterpretRunDemo();
        Class<InterpretRunDemo> c02 = InterpretRunDemo.class;
        Method[] methods = c02.getDeclaredMethods();
        for (Method method:methods){
            if (method.isAnnotationPresent(MyTest.class)){
                try {
                    method.invoke(runDemo);//执行该方法
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

@Retention(RetentionPolicy.RUNTIME)
@interface MyTest{ }