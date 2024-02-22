package course_02;

/**
 * 【2.5】【题】面向对象描述
 * 用类描述计算机中CPU速度和硬盘容量，要求程序有4个类:PC,CPU,HardDisk,Test。见教材第4章课后编程题。
 *
 * @since 2022-3-23
 */
@SuppressWarnings("NonAsciiCharacters")
public class 面向对象描述 {
    public static void main(String[] args) {
        CPU cpu = new CPU(2200);
        HardDisk disk = new HardDisk(200);
        PC pc = new PC();
        pc.setCPU(cpu);
        pc.setHardDisk(disk);
        pc.show();
    }
}

/**
 * CPU对象
 *
 * @param speed CPU速度，单位GHz(千兆赫)
 */
record CPU(int speed) {
}

/**
 * 硬盘类
 *
 * @param amount 存储空间，单位GB
 */
record HardDisk(int amount) {
}

/**
 * 电脑类，集成了硬盘和CPU
 */
class PC {
    HardDisk HD;
    CPU cpu;

    void setCPU(CPU c) {
        cpu = c;
    }

    void setHardDisk(HardDisk h) {
        HD = h;
    }

    void show() {
        System.out.println("CPU的速度是" + cpu.speed() + "GHz");
        System.out.println("硬盘的容量是" + HD.amount() + "GB");
    }
}