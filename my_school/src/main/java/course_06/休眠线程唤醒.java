package course_06;

/**
 * 【6.4】【题】休眠线程唤醒
 * 参照例12-6，要求有3个线程：student1、student2和teacher，
 * 其中student1准备睡10分钟后再开始上课，
 * 其中student2准备睡一小时后再开始上课。
 * teacher在输出3句“上课”后，吵醒休眠的线程student1；
 * student1被吵醒后，负责再吵醒休眠的线程student2。
 *
 * @since 2022-5-11
 */
@SuppressWarnings("NonAsciiCharacters")
public class 休眠线程唤醒 {
    public static void main(String[] args) {
        ClassRoom cl = new ClassRoom();
        cl.student1.start();
        cl.student2.start();
        cl.teacher.start();
    }
}

class ClassRoom implements Runnable {
    final Thread student1;
    final Thread student2;
    final Thread teacher;

    ClassRoom() {
        teacher = new Thread(this);
        student1 = new Thread(this);
        student2 = new Thread(this);
        teacher.setName("老师");
        student1.setName("刘海柱");
        student2.setName("白傻子");
    }

    public void run() {
        String name = Thread.currentThread().getName();
        switch (name) {
            case "刘海柱" -> {
                try {
                    System.out.println(name + "说：“我睡10分钟再上课”");
                    Thread.sleep(1000 * 10 * 60);
                } catch (InterruptedException e) {
                    System.out.println(name + "被" + teacher.getName() + "叫醒了");
                }
                student2.interrupt();
                System.out.println(name + "继续上课");
            }
            case "白傻子" -> {
                try {
                    System.out.println(name + "说：“我睡1小时再上课”");
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException e) {
                    System.out.println(name + "被" + student1.getName() + "叫醒了");
                    student2.interrupt();
                }
                System.out.println(name + "继续上课");
            }
            case "老师" -> {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("上课！");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                student1.interrupt();
            }
        }
    }
}