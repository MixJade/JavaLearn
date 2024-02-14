package trial;

/**
 * 测试Instanceof的用法
 */
public class WhatInstanceof {
    public static void main(String[] args) {
        Me me=new Me();
        Dream dream=new You();
        dream.hello();
        System.out.println("测试它左边的对象是否是它右边的类的实例");
        System.out.println("子父为true");
        System.out.println(me instanceof Dream);
        Integer a=10;
        System.out.println(a instanceof Object);
        System.out.println("但是同一个类的不同实现不行");
        System.out.println(dream instanceof Me);
        System.out.println("同时父子也不可以");
        Object b="好好";
        System.out.println(b instanceof Double);
    }
}

interface Dream {
    void hello();
}

class Me implements Dream {
    @Override
    public void hello() {
        System.out.println("你好世界");
    }
}

class You implements Dream{

    @Override
    public void hello() {
        System.out.println("LOVE");
    }
}