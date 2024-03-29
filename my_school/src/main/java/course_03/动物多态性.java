package course_03;

interface Animal {
    void shout();

    String getName();
}

/**
 * 【3.1】【题】动物多态性
 * 完成第6章习题5编程题：
 * <p>使用接口设计一个动物的“模拟器”，希望可以模拟许多动物的叫声,要求使用接口回调实现运行时多态。</p>
 *
 * @since 2022-4-6
 */
@SuppressWarnings("NonAsciiCharacters")
public class 动物多态性 {
    public static void main(String[] args) {
        playSound(new Dog());
        playSound(new Cat());
    }

    static void playSound(Animal animal) {
        System.out.print("现在播放" + animal.getName() + "类的声音:");
        animal.shout();
    }
}

class Dog implements Animal {
    @Override
    public void shout() {
        System.out.println("汪汪...汪汪");
    }

    @Override
    public String getName() {
        return "狗";
    }
}

class Cat implements Animal {
    @Override
    public void shout() {
        System.out.println("喵喵...喵喵");
    }

    @Override
    public String getName() {
        return "猫";
    }
}

