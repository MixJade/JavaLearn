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

        // 布局设置
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        // 窗口设置
        setSize(580, 280);
        setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        // 启动窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random rand = new Random();
        int randInt = rand.nextInt(64); // 生成0-63之间的数
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
}

record Gua(String name, String context) {
}
