package mbti;

import com.formdev.flatlaf.FlatDarkLaf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MBTI性格测试
 *
 * @since 2023-10-19 21:21
 */
@SuppressWarnings("SpellCheckingInspection")
public class GuiMBTI extends JFrame {
    private final JButton button1;
    private final JButton button2;
    private final JLabel label;
    private final List<QuizQuestOption> quizQuestOptions = getQuestion();
    private int questIndex = 0,
            ei = 0, // E外向,I内向
            sn = 0, // S实感,N直觉
            tf = 0, // T思维,F情感
            jp = 0; // J判断,P知觉

    public GuiMBTI() {
        setTitle("MBTI职业性格测试");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));

        label = new JLabel("开始答题", SwingConstants.CENTER);

        button1 = new JButton("选项1");
        button1.addActionListener(e -> nextQuest(true));

        button2 = new JButton("选项2");
        button2.addActionListener(e -> nextQuest(false));

        add(label);
        add(button1);
        add(button2);
        setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(GuiMBTI::new);
    }

    /**
     * 从XML中获取对应的标签描述
     *
     * @return 对应的内容
     */
    private String getMbtXML() {
        // 获取结果的二进制标识
        String model = (ei > 0 ? "1" : "0")
                + (sn > 0 ? "1" : "0")
                + (tf > 0 ? "1" : "0")
                + (jp > 0 ? "1" : "0");
        // 将二进制转换为十进制
        int modelIndex = Integer.parseInt(model, 2);
        // 读取结果
        try (var in = getClass().getResourceAsStream("guiMBTI.xml")) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(in);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Result");
            return nList.item(modelIndex).getTextContent();
        } catch (Exception e) {
            return "文件读取有问题";
        }
    }

    /**
     * 从xml文件中读取问题和对应答案
     *
     * @return 问题及答案
     */
    private List<QuizQuestOption> getQuestion() {
        List<QuizQuestOption> qqoList = new ArrayList<>();
        try (var in = getClass().getResourceAsStream("quizQuest.xml")) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(in);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Option");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                // 获取指定索引的节点
                Node nNode = nList.item(temp);
                // 获取节点下对应的元素
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nNode;
                    // 取出每一个值
                    QuizQuestOption qqo = new QuizQuestOption(
                            e.getAttribute("quest"),
                            e.getAttribute("A"),
                            e.getAttribute("aType"),
                            e.getAttribute("B"),
                            e.getAttribute("bType"));
                    qqoList.add(qqo);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "问题的XML不见了", "最终结果", JOptionPane.ERROR_MESSAGE);
        }
        return qqoList;
    }

    private void nextQuest(boolean isOp1) {
        if (questIndex < quizQuestOptions.size()) {
            QuizQuestOption questOption = quizQuestOptions.get(questIndex);
            label.setText(questOption.quest());
            button1.setText(questOption.a());
            button2.setText(questOption.b());
            if (isOp1) {
                addScope(questOption.aTp());
            } else {
                addScope(questOption.bTp());
            }
            questIndex++;
        } else {
            showResult();
        }
    }

    private void addScope(String eightChar) {
        switch (eightChar) {
            case "E" -> ei++;
            case "I" -> ei--;
            case "S" -> sn++;
            case "N" -> sn--;
            case "T" -> tf++;
            case "F" -> tf--;
            case "J" -> jp++;
            case "P" -> jp--;
        }
    }

    private void showResult() {
        String resText = getMbtXML();
        JTextArea textArea = new JTextArea(resText);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500)); // 你自己设定的滚动面板大小
        JOptionPane.showMessageDialog(null, scrollPane, "最终结果", JOptionPane.PLAIN_MESSAGE);
    }

}

/**
 * 各种问题的文本与选项
 *
 * @param quest 问题文本
 * @param a     A选项文本
 * @param aTp   A选项类型
 * @param b     B选项文本
 * @param bTp   B选项类型
 */
record QuizQuestOption(String quest, String a, String aTp, String b, String bTp) {
}
