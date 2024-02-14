package proxy_annotate;

import java.lang.reflect.Proxy;

interface PracticeSkill {
    String getPracticeYear();

    void sing();

    void dance();

    void rap();

    void basketball();
}

public class CXKProxy {
    public static void main(String[] args) {
        //第一种代理的方法，通过代理类的对象
        CXKPractice wyf = new CXKPractice("吴亦凡", 13.0, "铁窗泪");
        PracticeSkill wyfAgent = StarAgent.getProxy(wyf);
        System.out.println(wyfAgent.getPracticeYear());
        wyfAgent.sing();

        //第二种代理的方法，在建立被代理对象时就加入代理,注意用多态的写法，前面用接口承接对象
        PracticeSkill cxk02 = StarAgent.getProxy(new CXKPractice("蔡徐坤", 2.5, "姬霓太美"));
        System.out.println(cxk02.getPracticeYear());
        cxk02.sing();
        cxk02.dance();
        cxk02.rap();
        cxk02.basketball();
    }
}

record CXKPractice(String name, Double trainYear, String song) implements PracticeSkill {
    @Override
    public String getPracticeYear() {
        return "这位学员练习" + trainYear + "年";
    }

    @Override
    public void sing() {
        System.out.println(name + "开始唱歌" + song);
    }

    @Override
    public void dance() {
        System.out.println(name + "开始跳舞：铁山靠，抖动，白带异常");
    }

    @Override
    public void rap() {
        System.out.println(name + "开始rap：迎面走来的你让我如此蠢蠢欲动");
    }

    @Override
    public void basketball() {
        System.out.println(name + "开始打篮球：接球，胯下运球，食指抛球");
    }
}

class StarAgent {
    //PracticeSkill 这个接口可以换成 <T> T ，后面的CXKPractice可以换成T ，这样可以把代理类变成泛型
    public static PracticeSkill getProxy(CXKPractice cxkStar) {
        return (PracticeSkill) Proxy.newProxyInstance(cxkStar.getClass().getClassLoader(), cxkStar.getClass().getInterfaces(), (proxy, method, args) -> {
            long beginTime = System.currentTimeMillis();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object rs = method.invoke(cxkStar, args);//args代表传递过来方法的参数
            long endTime = System.currentTimeMillis();
            System.out.println(method.getName() + "耗时" + (endTime - beginTime));
            return rs;
        });
    }
}