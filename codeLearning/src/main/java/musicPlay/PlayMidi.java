package musicPlay;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Java原生库播放MIDI文件工具类
 *
 * @since 2025-12-15 16:05:02
 */
public class PlayMidi implements AutoCloseable {
    private Sequencer sequencer; // MIDI序列器（播放器核心）
    private CountDownLatch playFinishedLatch; // 用于等待播放完成

    /**
     * 初始化播放器
     *
     * @throws MidiUnavailableException 当MIDI设备不可用时抛出
     */
    public void init() throws MidiUnavailableException {
        // 获取默认序列器（支持播放控制：暂停、停止、定位）
        sequencer = MidiSystem.getSequencer(true); // true：启用合成器
        if (sequencer == null) {
            throw new MidiUnavailableException("当前系统不支持MIDI序列器");
        }
        // 初始化完成信号
        playFinishedLatch = new CountDownLatch(1);
        // 播放前注册回调，替代while循环
        sequencer.addMetaEventListener(meta -> {
            // 47是MIDI的"结束元事件"编号（0x2F）
            if (meta.getType() == 47) {
                sequencer.stop();
                // 通知主线程播放已完成
                playFinishedLatch.countDown();
            }
        });
        // 打开序列器（占用音频资源）
        sequencer.open();
        // 注册JVM关闭钩子，确保资源被释放
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nJVM关闭钩子被触发，清理MIDI资源...");
            close();
        }));
    }

    /**
     * 加载并播放MIDI文件
     *
     * @param midiFilePath MIDI文件路径（绝对/相对路径）
     * @throws InvalidMidiDataException MIDI文件格式错误
     * @throws IOException              文件读取失败
     */
    public void play(String midiFilePath) {
        try {
            // 重置完成信号（用于重复播放）
            if (playFinishedLatch.getCount() == 0) {
                playFinishedLatch = new CountDownLatch(1);
            }
            // 加载MIDI文件为Sequence对象
            File midiFile = new File(midiFilePath);
            Sequence sequence = MidiSystem.getSequence(midiFile);
            // 打印时长：使用微秒转换（最准确）
            long microseconds = sequence.getMicrosecondLength();
            System.out.println("时长：" + microseconds / 1_000_000 + "秒");
            // 将Sequence设置到序列器
            sequencer.setSequence(sequence);
            // 开始播放
            sequencer.start();
        } catch (InvalidMidiDataException e) {
            System.err.println("MIDI文件格式错误：" + e.getMessage());
        } catch (IOException e) {
            System.err.println("读取MIDI文件失败：" + e.getMessage());
        }
    }

    /**
     * 等待播放完成（阻塞当前线程直到播放结束）
     */
    public void waitForCompletion() throws InterruptedException {
        if (playFinishedLatch != null) {
            playFinishedLatch.await();
        }
    }

    /**
     * 释放资源（必须调用，否则可能导致音频资源泄漏）
     */
    @Override
    public void close() {
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.close();
        }
        // 如果播放尚未完成，确保主线程可以继续
        if (playFinishedLatch != null && playFinishedLatch.getCount() > 0) {
            playFinishedLatch.countDown();
        }
    }

    // 测试主方法
    public static void main(String[] args) {
        try (PlayMidi player = new PlayMidi()) {
            // 1. 初始化播放器
            player.init();

            // 2. 播放MIDI文件（替换为你的mid文件路径）
            String midiPath = "src/main/resources/musicPlay/CogWorkCore.mid"; // 示例路径
            System.out.println("开始播放MIDI文件：" + midiPath);
            player.play(midiPath);

            // 3. 等待播放完成
            System.out.println("等待播放完成...");
            player.waitForCompletion();
            System.out.println("播放完成！");
        } catch (MidiUnavailableException e) {
            System.err.println("MIDI设备不可用：" + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("播放被中断：" + e.getMessage());
        } finally {
            // 释放资源（在播放完成后才执行）
            System.out.println("播放器已关闭");
        }
    }
}