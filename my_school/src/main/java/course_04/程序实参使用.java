package course_04;

/**
 * 现有职工类Employee,
 * 其中的四个变量分别表明职工的姓名、编号、年龄、薪水。
 * 要求编写类EmployeeTest，
 * 在类中使用数组存储若干职工的信息，并有方法分别计算职工年龄、薪水的最大值、最小值和平均值。
 * 程序运行时在命令行中输入两个参数，根据参数返回职工年龄或薪水的信息。
 * <p>第一个参数若是age表明计算年龄信息，若是salary表明计算薪水信息。</p>
 * <p>第二个参数若是avg表明计算平均值，若是min表明计算最小值，若是max表明计算最大值。</p>
 * 例如,
 * 若输入以下信息，则表明求职工年龄的平均值：java Employee age avg
 * 若输入以下信息，则表明求职工薪水的最大值：java Employee salary max
 * 其他输入都显示输入错误。
 *
 * @since 2022-4-20
 */
@SuppressWarnings("NonAsciiCharacters")
public class 程序实参使用 {
    static final Employee[] emo = new Employee[3];

    public static void main(String[] args) {
        emo[0] = new Employee("刘备", 1, 45, 5000);
        emo[1] = new Employee("关羽", 2, 48, 8000);
        emo[2] = new Employee("张飞", 3, 42, 2000);
        if (args.length == 2) {
            if (args[0].equals("age")) {
                switch (args[1]) {
                    case "min" -> System.out.println("年龄最小" + minAge("age") + "岁");
                    case "max" -> System.out.println("年龄最大" + maxAge("age") + "岁");
                    case "avg" -> System.out.println("平均年龄" + aveAge("age") + "岁");
                    default -> System.out.println("(1)第二参数输入错误");
                }
            } else if (args[0].equals("salary")) {
                switch (args[1]) {
                    case "min" -> System.out.println("最低工资" + minAge("salary") + "万元");
                    case "max" -> System.out.println("最高工资" + maxAge("salary") + "万元");
                    case "avg" -> System.out.println("平均工资" + aveAge("salary") + "万元");
                    default -> System.out.println("(2)第二参数输入错误");
                }
            } else System.out.println("(3)输入错误");
        } else {
            System.out.println("(4)程序不是这么运行的。");
            System.out.println("如果你是命令行用户，运行时输入java Employee age avg");
            System.out.println("如果你是IDE用户，在“运行配置”-“程序实参”输入“age avg”再运行");
        }
    }

    public static int aveAge(String type) {
        int sum = 0;
        for (Employee employee : emo) {
            if (type.equals("age")) sum = sum + employee.age();
            if (type.equals("salary")) sum = sum + employee.salary();
        }
        return sum / emo.length;
    }

    public static int minAge(String type) {
        int min = 0;
        if (type.equals("age")) {
            min = emo[0].age();
            for (int i = 1; i < emo.length; i++) {
                if (emo[i].age() < min)
                    min = emo[i].age();
            }
        }
        if (type.equals("salary")) {
            min = emo[0].salary();
            for (int i = 1; i < emo.length; i++) {
                if (emo[i].salary() < min) min = emo[i].salary();
            }
        }
        return min;
    }

    public static int maxAge(String type) {
        int max = 0;
        if (type.equals("age")) {
            max = emo[0].age();
            for (int i = 1; i < emo.length; i++) {
                if (emo[i].age() > max)
                    max = emo[i].age();
            }
        }
        if (type.equals("salary")) {
            max = emo[0].salary();
            for (int i = 1; i < emo.length; i++) {
                if (emo[i].salary() > max)
                    max = emo[i].salary();
            }
        }
        return max;
    }
}

record Employee(String name, int number, int age, int salary) {
}