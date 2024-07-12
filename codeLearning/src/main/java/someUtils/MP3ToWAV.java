package someUtils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将MP3转化为WAV
 * <p>必须在Pom中引入mp3spi</p>
 *
 * @since 2023年6月27日 20:52
 */
public class MP3ToWAV extends JFrame implements ActionListener {
    private final JTextArea area = new JTextArea();
    private String nowFilePath;

    public MP3ToWAV() throws HeadlessException {
        area.setEditable(false);
        area.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(area);
        jScrollPane.setSize(200, 200);
        JFrame jf = new JFrame("将MP3转化WAV");
        jf.add(jScrollPane);
        JPanel panel3 = new JPanel();
        // 创建一个按钮，点击后开始转化
        JButton btn = new JButton("转化");
        panel3.add(new JLabel("将MP3文件拖入窗口"));
        panel3.add(btn);
        jf.add(panel3);
        new DropTarget(area, DnDConstants.ACTION_COPY_OR_MOVE, dropTargetAdapter());
        jf.setSize(500, 350);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new GridLayout(4, 10));
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        //监听按钮
        btn.addActionListener(this);
    }

    public static void main(String[] args) {
        new MP3ToWAV();
    }

    /**
     * 拖拽适配器，用于处理拖拽事件
     * <p>拖拽文件时读取文件路径</p>
     *
     * @return 拖拽适配器
     */
    private DropTargetAdapter dropTargetAdapter() {
        return new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent tde) {
                try {
                    // 接收拖拽来的数据
                    tde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    List<File> list = new ArrayList<>();
                    Object tranData = tde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (tranData instanceof List<?> tranList) {
                        tranList.forEach(o -> {
                            if (o instanceof File f) list.add(f);
                        });
                    }
                    nowFilePath = list.get(0).getAbsolutePath();
                    area.setText(list.get(0).getAbsolutePath());
                    tde.dropComplete(true);// 指示拖拽操作已完成
                } catch (IOException | UnsupportedFlavorException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = nowFilePath;
        if ("转化成功".equals(path) || "文件转存失败".equals(path) || path.isEmpty()) return;
        // 使用正则表达式转化mp3后缀为wav
        String wavFilePath = path.replaceAll("\\.mp3$", ".wav");
        // 将MP3转化为WAV，并输出结果
        area.append("\n" + convertMP3ToWAV(path, wavFilePath));
    }

    /**
     * 将MP3转化为WAV
     *
     * @param mp3FilePath 输入的MP3路径
     * @param wavFilePath 输出的WAV路径
     */
    private String convertMP3ToWAV(String mp3FilePath, String wavFilePath) {
        try {
            // 读取MP3文件
            File mp3File = new File(mp3FilePath);
            AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3File);
            AudioFormat sourceFormat = mp3Stream.getFormat();
            // 设置WAV转换格式
            AudioFormat.Encoding targetEncoding = AudioFormat.Encoding.PCM_SIGNED;
            AudioFormat targetFormat = new AudioFormat(targetEncoding, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
            // 验证通知是否支持MP3到WAV转换
            if (!AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
                throw new IllegalArgumentException("不支持MP3到WAV转换");
            }
            // 执行转换
            AudioInputStream wavStream = AudioSystem.getAudioInputStream(targetFormat, mp3Stream);
            File outputFile = new File(wavFilePath);
            AudioSystem.write(wavStream, AudioFileFormat.Type.WAVE, outputFile);
            // 关闭流
            mp3Stream.close();
            wavStream.close();
            // 返回结果
            return "转化成功，输出路径：" + wavFilePath;
        } catch (IOException e) {
            return "文件IO异常: " + e.getMessage();
        } catch (UnsupportedAudioFileException e) {
            return "不支持的音频文件格式: " + mp3FilePath;
        }
    }
}