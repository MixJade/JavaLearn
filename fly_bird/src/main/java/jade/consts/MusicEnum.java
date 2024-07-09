package jade.consts;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * 音乐的路径枚举
 */
public enum MusicEnum implements Runnable {
    DIE("die.wav"), SCORE("score.wav"),
    BEGIN("start.wav"), HIT("hit.wav"),
    FLY("flap.wav");
    private final String fileName;

    MusicEnum(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (var is = getClass().getResourceAsStream("/audio/" + fileName)) {
            if (is == null) return;
            // 必须使用BufferedInputStream转一次,不然会报错java.io.IOException: mark/reset not supported
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is))) {
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                // 让音频播放完毕
                clip.drain();
                //  clip.close();  // 加上这个会导致音乐播放断断续续的
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("不支持的音频文件格式: " + fileName);
        } catch (IOException e) {
            System.err.println("无法读取音频文件: " + fileName);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("音频剪辑行不可用");
        }
    }
}
