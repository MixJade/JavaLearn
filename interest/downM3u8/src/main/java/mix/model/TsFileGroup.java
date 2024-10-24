package mix.model;

import java.util.Objects;

/**
 * 检查m3u8时,存放播放列表所用
 *
 * @param time1  第1个视频时长
 * @param time2  第2个视频时长
 * @param time3  第3个视频时长
 * @param time4  第4个视频时长
 * @param tsName 第1个视频名称
 */
public record TsFileGroup(String time1, String time2, String time3, String time4, String tsName) {
    public int getKey() {
        return Objects.hash(time1, time2, time3, time4);
    }
}
