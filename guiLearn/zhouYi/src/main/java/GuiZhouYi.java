import com.formdev.flatlaf.FlatLightLaf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GuiZhouYi extends JFrame implements ActionListener {
    private final JTextField resultText;
    private final JTextField number1, number2, number3;
    private final JTextArea resultArea;

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new GuiZhouYi();
    }

    public GuiZhouYi() {
        setTitle("六十四卦");
        // 设置图标
        try (InputStream favor = getClass().getResourceAsStream("favor.jpg")) {
            if (favor != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(favor));
                setIconImage(icon.getImage());
            }
        } catch (IOException ignored) {
        }
        // 使用东西南北布局
        setLayout(new BorderLayout());

        // 上方面板
        JPanel topPanel = new JPanel();
        resultText = new JTextField("卦象名称");
        resultText.setEditable(false);
        JButton genBtn = new JButton("生成卦象");
        genBtn.addActionListener(this);
        topPanel.add(resultText);
        topPanel.add(genBtn);

        // 中间面板
        JPanel centerPanel = new JPanel();
        resultArea = new JTextArea(7, 48);
        resultArea.setEditable(false);
        // 创建边框
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);
        resultArea.setBorder(border);
        centerPanel.add(resultArea);

        // 下方面板
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("数字起卦"));
        number1 = new JTextField(3);
        number2 = new JTextField(3);
        number3 = new JTextField(3);
        bottomPanel.add(number1);
        bottomPanel.add(number2);
        bottomPanel.add(number3);
        JButton numberBtn = new JButton("求余");
        numberBtn.addActionListener(this);
        bottomPanel.add(numberBtn);

        // 布局设置
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        // 窗口设置
        setSize(580, 280);
        setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        // 启动窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int num1, num2, num3;
        if ("求余".equals(e.getActionCommand())) {
            if (isThreeInt(number1.getText()) && isThreeInt(number2.getText()) && isThreeInt(number3.getText())) {
                num1 = Integer.parseInt(number1.getText()) % 8;
                num2 = Integer.parseInt(number2.getText()) % 8;
                num3 = Integer.parseInt(number3.getText()) % 6;
            } else {
                JOptionPane.showMessageDialog(this, "请输入三个三位数", "出错", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            Random rand = new Random();
            num1 = rand.nextInt(8); // 生成0-7之间的数
            num2 = rand.nextInt(8); // 生成0-7之间的数
            num3 = rand.nextInt(6); // 生成0-7之间的数
        }
        // 读取xml文件
        Gua gua = getGua(String.valueOf(num1), String.valueOf(num2), String.valueOf(num3));
        resultText.setText(gua.name());
        resultArea.setText(gua.name() + "\n" + gua.context());
    }

    /**
     * 从xml文件中读取指定卦象与卦辞
     *
     * @param above 上卦索引(0-7)
     * @param below 下卦索引(0-7)
     * @param yao   爻辞索引(0-5)
     */
    private Gua getGua(String above, String below, String yao) {
        try (var in = getClass().getResourceAsStream("64gua.xml")) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(in);
            // 规范化文档
            doc.getDocumentElement().normalize();
            // 获取所有 Gua 元素
            NodeList guaList = doc.getElementsByTagName("Gua");
            // 遍历 Gua 元素
            for (int i = 0; i < guaList.getLength(); i++) {
                Node guaNode = guaList.item(i);
                if (guaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element guaElement = (Element) guaNode;
                    // 检查 Gua 元素的 above 和 below 属性
                    if (above.equals(guaElement.getAttribute("above")) && below.equals(guaElement.getAttribute("below"))) {
                        // 获取 Gua 元素下所有 yao 元素
                        NodeList yaoList = guaElement.getElementsByTagName("yao");
                        // 遍历 yao 元素
                        for (int j = 0; j < yaoList.getLength(); j++) {
                            Node yaoNode = yaoList.item(j);
                            if (yaoNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element yaoElement = (Element) yaoNode;
                                // 检查 yao 元素的 num 属性
                                if (yao.equals(yaoElement.getAttribute("num")))
                                    // 获取 yao 元素的文本内容
                                    return new Gua(guaElement.getAttribute("name"), yaoElement.getTextContent());
                            }
                        }
                    }
                }
            }
            return new Gua("异常", "未找到对应卦辞");
        } catch (Exception e) {
            e.printStackTrace();
            return new Gua("异常", "出现异常");
        }
    }

    /**
     * 检验一个字符串是否为三位数字
     *
     * @param str 数字字符串
     * @return 是三位数字
     */
    private boolean isThreeInt(String str) {
        if (str.length() != 3)
            return false;
        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
}

record Gua(String name, String context) {
}
