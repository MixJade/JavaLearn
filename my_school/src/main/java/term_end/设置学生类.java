package term_end;


/**
 * 3. 用Java语言定义一个学生类 Student，要求：
 * 1) 学生类 Student 属性有：
 * id long型，代表编号
 * name String类对象，代表姓名
 * sex  boolean型，代表性别（true表示男，false表示女）
 * age int型，代表年龄
 * 2) 学生类 Student的方法有：
 * （1）Student(long i , String n , boolean s  ,int a)：有参构造函数。
 * （2）分别取得每个属性值的方法，命名方式为getXX()。其中XX表示类中的各属性名。
 * （3）分别设置每个属性值的方法，命名方式为setXX(T d)。
 * （4）void print()方法：以编号、姓名、性别和年龄的顺序输出学生的全部信息，性别用“男”或 “女”表示。
 *
 * @since 2022-6-13
 */
@SuppressWarnings("NonAsciiCharacters")
public class 设置学生类 {
    public static void main(String[] args) {
        Student cdy = new Student(9527, "李辉若", true, 21);
        cdy.print();
        System.out.println(cdy.id());
    }

    private record Student(long id, String name, boolean sex, int age) {
        void print() {
            String sexStr = sex ? "男" : "女";
            System.out.println("该学生编号为:" + id
                    + " 姓名为：" + name
                    + " 性别为:" + sexStr
                    + " 年龄为:" + age);
        }
    }
}

