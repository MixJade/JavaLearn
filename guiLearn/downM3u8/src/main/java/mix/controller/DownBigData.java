package mix.controller;

/**
 * 下载线程的计数器对象
 */
public class DownBigData {
    // 对象索引
    private final Integer name;
    // 下载的字节数
    private long downKB = 0;

    public DownBigData(Integer name) {
        this.name = name;
    }

    public long getDownKB() {
        return downKB;
    }

    public void addKB(long downKB) {
        this.downKB += downKB >> 10; // 2^10 = 1024
    }

    // todo 需要删除
    @Override
    public String toString() {
        return "Down{" +
                "name=" + name +
                ", downKB=" + downKB +
                '}';
    }
}