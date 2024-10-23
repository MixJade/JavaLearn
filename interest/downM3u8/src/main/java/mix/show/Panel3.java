package mix.show;

import mix.entiy.Panel3Vo;
import mix.utils.CheckM3u8;
import mix.utils.ReadTsFromM3u8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;

/**
 * 将ts文件打包
 */
public class Panel3 extends JPanel implements ActionListener {
    private final JTextField saveDir, m3u8Name, movieName;
    private final JButton tranBtn;

    private final Panel1 panel1;

    public Panel3(Panel1 panel1) {
        this.panel1 = panel1;
        // 设置布局
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gbc.fill = GridBagConstraints.NONE; // 如果组件所在的区域比组件大,不调整组件大小。

        // 加入组件
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(new JLabel("转换文件夹"), gbc);
        // 对输入框进行设置
        saveDir = new JTextField("example\\sss", 20);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        add(saveDir, gbc);

        // 保存地址lab
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(new JLabel("m3u8文件名"), gbc);
        // m3u8地址输入框
        m3u8Name = new JTextField("index.m3u8", 20);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        add(m3u8Name, gbc);

        // 电影名称
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("电影名称"), gbc);
        // 电影名称输入框
        movieName = new JTextField("下载电影", 20);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        add(movieName, gbc);

        // 同步按钮
        JButton syncBtn = new JButton("同步配置");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        syncBtn.setActionCommand("SYNC");
        syncBtn.addActionListener(this);
        add(syncBtn, gbc);

        // 检查按钮
        JButton checkBtn = new JButton("检查广告");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        checkBtn.setActionCommand("CHECK");
        checkBtn.addActionListener(this);
        add(checkBtn, gbc);

        // 下载按钮
        tranBtn = new JButton("开始转换");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        tranBtn.setActionCommand("TRAN");
        tranBtn.addActionListener(this);
        add(tranBtn, gbc);

        // 同步配置
        syncConfigFormPanel1();
    }

    public static void writeNewM3u8(String m3u8Path, String newM3u8Path) {
        try (var reader = new BufferedReader(new FileReader(m3u8Path));
             var writer = new BufferedWriter(new FileWriter(newM3u8Path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 读取
                if (line.startsWith("http") || line.startsWith("/"))
                    line = ReadTsFromM3u8.getNameFromUrl(line);
                else if (line.startsWith("#EXT-X-KEY:"))
                    line = ReadTsFromM3u8.getKeyLineForLine(line);
                // 写入
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("TRAN".equals(e.getActionCommand())) {
            tranBtn.setEnabled(false); // 禁止多次点击
            tranTsToMp4();
        } else if ("SYNC".equals(e.getActionCommand()))
            syncConfigFormPanel1();
        else if ("CHECK".equals(e.getActionCommand())) {
            checkAdvertisement();
        }
    }

    /**
     * 检查是否有广告
     */
    private void checkAdvertisement() {
        String saveDirText = saveDir.getText();
        String m3u8Path = Paths.get(saveDirText, m3u8Name.getText()).toString();
        // 设置要显示的长文本
        String dupTsGroup = CheckM3u8.checkDupTsGroup(m3u8Path);
        // 设置显示的文本区域
        JTextArea textArea = new JTextArea(8, 20);
        textArea.setText(dupTsGroup);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        // 设置滚动条
        JScrollPane scrollPane = new JScrollPane(textArea);
        // 在点击按钮时显示带有长文本的消息对话框
        JOptionPane.showMessageDialog(this, scrollPane, "疑似广告", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 使用ffmpeg将ts文件转为mp4
     */
    private void tranTsToMp4() {
        // 规范ts文件名
        String saveDirText = saveDir.getText();
        String m3u8Path = Paths.get(saveDirText, m3u8Name.getText()).toString();
        String newM3u8Path = Paths.get(saveDirText, "newPlay.m3u8").toString();
        writeNewM3u8(m3u8Path, newM3u8Path);
        // 执行转换命令
        try {
            // ffmpeg -allowed_extensions ALL -i index.m3u8 -c copy xxx.mp4
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-allowed_extensions", "ALL", "-i",
                    newM3u8Path, "-c", "copy", movieName.getText() + ".mp4");
            final Process p = pb.start();
            //为了避免阻塞，启动一个新的线程来接受错误流/输出流
            new RunProcess(p.getErrorStream()).start();
            new RunProcess(p.getInputStream()).start();

            // 等待命令执行完毕
            p.waitFor();
            JOptionPane.showMessageDialog(null, "转换成功", "反馈", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "转换失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 从第一面板同步数据过来
     */
    private void syncConfigFormPanel1() {
        Panel3Vo dataToPanel3 = panel1.getDataToPanel3();
        saveDir.setText(dataToPanel3.saveDir());
        m3u8Name.setText(dataToPanel3.m3u8Name());
    }
}

/**
 * 专门处理命令行输出流的线程
 */
class RunProcess extends Thread {
    private final InputStream ioStream;

    RunProcess(InputStream ioStream) {
        this.ioStream = ioStream;
    }

    @Override
    public void run() {
        // 仅仅只是象征性的读一下，防止命令行的缓冲区爆炸
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ioStream))) {
            //noinspection StatementWithEmptyBody
            while (reader.readLine() != null) {
            }
        } catch (IOException ignored) {
        }
    }
}
