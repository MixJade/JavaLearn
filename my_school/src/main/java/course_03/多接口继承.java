package course_03;

interface Biology {
    void breath();
}

interface Animal2 extends Biology {
    void hasSex();

    void eat();
}

interface Man extends Animal2 {
    void think();

    void study();
}

/**
 * 【3.3】【题】
 * 定义Biology(生物)、Animal(动物)、Man(人)3个接口，
 * 其中Biology接口声明了 breath()抽象方法，
 * Animal接口声明了hasSex()和eat()抽象方法，
 * Man接口声明了think()和study()抽象方法。
 * 定义NormalMan类实现上述3个接口，实现它们声明的抽象方法(仅显示相应的功能信息)，并定义名称name。完成测试。
 *
 * @since 2022-4-6
 */
@SuppressWarnings("NonAsciiCharacters")
public class 多接口继承 {
    public static void main(String[] args) {
        Man nm = new NormalMan("李辉若");
        nm.breath();
        nm.hasSex();
        nm.eat();
        nm.study();
        nm.think();
    }
}

record NormalMan(String name) implements Man {
    @Override
    public void breath() {
        System.out.println(name + "用鼻子呼吸");
    }

    @Override
    public void hasSex() {
        System.out.println(name + "在涩涩");
    }

    @Override
    public void eat() {
        System.out.println(name + "用嘴吃东西");
    }

    @Override
    public void think() {
        System.out.println(name + "会思考");
    }

    @Override
    public void study() {
        System.out.println(name + "会学习");
    }
}
