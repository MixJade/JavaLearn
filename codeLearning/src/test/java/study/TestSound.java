package study;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * 测试使用Java发出声音
 *
 * @author MixJade
 * @since 2024-5-29 17:59:39
 */
public class TestSound {
    public static void main(String[] args) {
        final int SAMPLE_RATE = 16 * 1024; // ~16KHz
        final int HZ = 440;
        final double CYCLE_PER_SAMPLE = HZ * 2 * Math.PI / SAMPLE_RATE;

        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
            line.open(af, SAMPLE_RATE);
            line.start();

            byte[] buf = new byte[1];

            for (int i = 0; i < SAMPLE_RATE; i++) {
                double angle = i * CYCLE_PER_SAMPLE;
                buf[0] = (byte) (Math.sin(angle) * 127);
                line.write(buf, 0, 1);
            }

            line.drain();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}