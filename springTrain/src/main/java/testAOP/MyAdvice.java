package testAOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void testAOP.AOPTest.*Method())")//芝士匹配所有以Method的方法
    private void needMethod() {
    }

    @Around("needMethod()")
    public Object adviceMethod(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();//获取切入点签名
        System.out.println("所引用类型为:" + signature.getDeclaringType());
        System.out.println("所引用的类为:" + signature.getDeclaringTypeName());
        System.out.println("切入点名为:" + signature.getName());
        System.out.println("这是切片");
        long beginTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        Thread.sleep(200);
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - beginTime;
        System.out.println("所耗费的时间为" + spendTime);
        return obj;
    }
}
