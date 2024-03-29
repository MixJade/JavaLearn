package trial;

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
                needUpdate.setMsg("已更改");
            }
        }
        System.out.println(needUpdates); // 能够更改
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