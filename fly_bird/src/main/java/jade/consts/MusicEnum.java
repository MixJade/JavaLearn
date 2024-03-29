package jade.consts;

import javax.sound.sampled.*;
import java.io.File;
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

    private String getPath() {
        return "src/main/resources/audio/" + fileName;
    }

    @Override
    public void run() {
        File musicFile = new File(getPath());
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            // 让音频播放完毕
            clip.drain();
            //  clip.close();  // 加上这个会导致音乐播放断断续续的
        } catch (UnsupportedAudioFileException e) {
            System.err.println("不支持的音频文件格式: " + musicFile.getPath());
        } catch (IOException e) {
            System.err.println("无法读取音频文件: " + musicFile.getPath());
        } catch (LineUnavailableException e) {
            System.err.println("音频剪辑行不可用");
        }
    }
}
