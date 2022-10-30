package testAOP;

import org.springframework.stereotype.Component;

@Component
public class AOPTestImpl implements AOPTest {
    @Override
    public void firstMethod() {
        System.out.println("这是第一个方法");
    }

    @Override
    public void secondMethod(int number) {
        System.out.println("This is a second method,and the param is "+number);
    }

    @Override
    public void thirdMethod() {
        System.out.println("第三个方法");
    }
}
