package testCircle;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试增强循环能否更改值引用
 *
 * @since 2024-01-24 11:19
 */
public class TestCircleUpdate {
    public static void main(String[] args) {
        List<NeedUpdate> needUpdates = new ArrayList<>();
        needUpdates.add(new NeedUpdate(1, "你好"));
        needUpdates.add(new NeedUpdate(2, "你好"));
        needUpdates.add(new NeedUpdate(3, "你好"));
        for (NeedUpdate needUpdate : needUpdates) {
            if (needUpdate.getNumber() != 3) {
                changeVal(needUpdate, "二等兵" + needUpdate.getNumber());
            }
        }
        System.out.println("在列表中循环可以被更改：");
        System.out.println(needUpdates); // 能够更改

        System.out.println("在其它类中也可以被更改：");
        ChangeNed changeNed = new ChangeNed(needUpdates.get(1));
        changeNed.setNeedUpdateStr("这个LuLu在其它类中被改");
        System.out.println(needUpdates); // 在其它类中,也能够更改
    }

    /**
     * 改变对象的值
     *
     * @param replaceStr 替换值
     */
    private static void changeVal(NeedUpdate needUpdate, String replaceStr) {
        needUpdate.setMsg(replaceStr);
    }
}

class ChangeNed {
    private final NeedUpdate needUpdate;

    public ChangeNed(NeedUpdate needUpdate) {
        this.needUpdate = needUpdate;
    }

    public void setNeedUpdateStr(String msg) {
        this.needUpdate.setMsg(msg);
    }
}

class NeedUpdate {
    int number;
    String msg;

    public NeedUpdate(int number, String msg) {
        this.number = number;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "NU{" +
                "number=" + number +
                ", msg='" + msg + '\'' +
                '}';
    }
}