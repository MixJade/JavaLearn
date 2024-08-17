package toy;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MBTI性格测试
 *
 * @since 2023-10-19 21:21
 */
@SuppressWarnings("SpellCheckingInspection")
public class GuiMBTI {
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
        JFrame frame = new JFrame("MBTI职业性格测试");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1));

        label = new JLabel("开始答题", SwingConstants.CENTER);

        button1 = new JButton("选项1");
        button1.addActionListener(e -> nextQuest(true));

        button2 = new JButton("选项2");
        button2.addActionListener(e -> nextQuest(false));

        frame.add(label);
        frame.add(button1);
        frame.add(button2);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiMBTI::new);
    }

    /**
     * 从XML中获取对应的标签描述
     *
     * @param tagName XML中的标签名字
     * @return 对应的内容
     */
    private String getMbtXML(String tagName) {
        try (var in = getClass().getResourceAsStream("guiMBTI.xml")) {
            assert in != null;
            StringBuilder result = new StringBuilder();
            try (var reader = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    result.append("\n");
                }
            }
            String content = result.toString();

            Pattern pattern = Pattern.compile("<" + tagName + ">(.*?)</" + tagName + ">", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                return matcher.group(1);
            } else return "没找到" + tagName + "标签";

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
        try {
            SAXReader saxreader = new SAXReader();
            Document dom = saxreader.read(getClass().getResourceAsStream("quizQuest.xml"));
            // 获取根节点
            Element rootEle = dom.getRootElement();
            // 从根节点读取元素标签
            List<Element> quizQuest = rootEle.elements("Option");
            // 取出每一个值
            for (Element e : quizQuest) {
                QuizQuestOption qqo = new QuizQuestOption(
                        e.elementText("Question"),
                        e.elementText("A"),
                        e.elementText("AType"),
                        e.elementText("B"),
                        e.elementText("BType"));
                qqoList.add(qqo);
            }
        } catch (DocumentException e) {
            System.out.println("问题的XML不见了" + e);
            System.exit(0);
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
        String model = (ei > 0 ? "E" : "I")
                + (sn > 0 ? "S" : "N")
                + (tf > 0 ? "T" : "F")
                + (jp > 0 ? "J" : "P");
        System.out.println(model);
        String resText = getMbtXML(model);
        JTextArea textArea = new JTextArea(resText);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500)); // 你自己设定的滚动面板大小
        JOptionPane.showMessageDialog(null, scrollPane, "最终结果" + model, JOptionPane.PLAIN_MESSAGE);
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
