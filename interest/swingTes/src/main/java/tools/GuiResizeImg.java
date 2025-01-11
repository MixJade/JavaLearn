package tools;

import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 重新设置图片尺寸，通过拖拽输入图片
 */
public class GuiResizeImg extends JFrame implements ActionListener {
    private final JTextArea area = new JTextArea();
    private final JTextField weightText = new JTextField(5), heightText = new JTextField(5);

    public GuiResizeImg() {
        area.setEditable(false);
        area.setLineWrap(true);
        weightText.setText("375");
        heightText.setText("100");
        JScrollPane jScrollPane = new JScrollPane(area);
        jScrollPane.setSize(200, 200);
        JFrame jf = new JFrame("重新设置图片尺寸");
        jf.add(jScrollPane);
        JPanel panel3 = new JPanel();
        // 创建一个按钮，点击后获取文本框中的文本
        panel3.add(new JLabel("宽"));
        panel3.add(weightText);
        panel3.add(new JLabel("高"));
        panel3.add(heightText);
        JButton btn = new JButton("转化");
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
        new GuiResizeImg();
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
                    tde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);// 接收拖拽来的数据
                    List<File> list = new ArrayList<>();
                    Object tranData = tde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (tranData instanceof List<?> tranList) {
                        tranList.forEach(o -> {
                            if (o instanceof File f) list.add(f);
                        });
                    }
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
        String path = area.getText(),
                weightS = weightText.getText(),
                heightS = heightText.getText();
        if ("转化成功".equals(path) || "文件转存失败".equals(path) || path.isEmpty()) return;
        if (weightS.isBlank() || heightS.isBlank()) return;
        int weight = Integer.parseInt(weightS),
                height = Integer.parseInt(heightS);
        resize(path, weight, height); // 修改图片尺寸
        area.setText("转化成功");
    }

    /**
     * 重写图片的尺寸
     *
     * @param path 图片路径
     * @param newW 新的宽度
     * @param newH 新的高度
     */
    private void resize(String path, int newW, int newH) {
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        System.out.println("文件后缀是：" + formatName);
        File input = new File(path);
        try {
            BufferedImage img = ImageIO.read(input);
            // 开始转化图片
            BufferedImage resized = new BufferedImage(newW, newH, img.getType());
            Graphics2D g2d = resized.createGraphics();
            boolean drawImage = g2d.drawImage(
                    img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH),
                    0, 0, null
            );
            System.out.println("图片处理：转化" + (drawImage ? "成功" : "失败"));
            g2d.dispose();
            // 转化完成，将图片转存回去
            ImageIO.write(resized, formatName, input);
        } catch (IOException e) {
            System.out.println("图片读取失败");
        }
    }
}