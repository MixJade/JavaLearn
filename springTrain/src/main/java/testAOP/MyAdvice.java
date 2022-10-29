package testAOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void testAOP.AOPTest.firstMethod())")
    private void needMethod(){}

    @Before("needMethod()")
    public void adviceMethod(){
        System.out.println("这是切片");
        System.out.println(System.currentTimeMillis());
    }
}
