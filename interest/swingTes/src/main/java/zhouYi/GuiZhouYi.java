package zhouYi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiZhouYi extends JFrame implements ActionListener {
    private final JTextField resultText;
    private final JTextField number1, number2, number3;
    private final JTextArea resultArea;

    public static void main(String[] args) {
        new GuiZhouYi();
    }

    public GuiZhouYi() {
        setTitle("六十四卦");
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
        int randInt;
        if ("求余".equals(e.getActionCommand())) {
            if (isThreeInt(number1.getText()) && isThreeInt(number2.getText()) && isThreeInt(number3.getText())) {
                int num1 = Integer.parseInt(number1.getText()) % 4;
                int num2 = Integer.parseInt(number2.getText()) % 4;
                int num3 = Integer.parseInt(number3.getText()) % 4;
                System.out.println(num1 + " " + num2 + " " + num3);
                randInt = (num1 << 4) + (num2 << 2) + num3;
            } else {
                JOptionPane.showMessageDialog(this, "请输入三个三位数", "出错", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            Random rand = new Random();
            randInt = rand.nextInt(64); // 生成0-63之间的数
        }
        // 读取xml文件
        List<Gua> guaList = getGua();
        Gua gua = guaList.get(randInt);
        resultText.setText(gua.name());
        resultArea.setText(gua.context());
    }

    /**
     * 从xml文件中读取卦象与卦辞
     */
    private List<Gua> getGua() {
        List<Gua> guaList = new ArrayList<>();
        try (var in = getClass().getResourceAsStream("64gua.xml")) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(in);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Gua");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                // 获取指定索引的节点
                Node nNode = nList.item(temp);
                // 获取节点下对应的元素
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nNode;
                    // 取出每一个值
                    guaList.add(new Gua(
                            e.getAttribute("name"),
                            e.getTextContent()));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "对应的XML不见了", "最终结果", JOptionPane.ERROR_MESSAGE);
        }
        return guaList;
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
