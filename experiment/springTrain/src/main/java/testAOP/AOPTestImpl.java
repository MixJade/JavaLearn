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
        System.out.println("This is a second method,and the param is " + number);
    }

    @Override
    public String thirdMethod() {
        System.out.println("第三个方法");
        return "我要发动一次第三方法";
    }

    @Override
    public String fourthMethod() {
        System.out.println("这是第四个方法");
        if (true) throw new NullPointerException();
        return "第四个方法";
    }
}
