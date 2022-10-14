package operateFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestXmlAnalysis {
    @Test
    public void parseXmlData() {
        SAXReader saxReader = new SAXReader();//创建解析器对象
        // 下面这句话代表是从这个类所在文件夹开始找指定文件
        // 如果文件前面加正斜杠代表源根路径：从源文件夹(src)往下的文件
        InputStream ips = TestXmlAnalysis.class.getResourceAsStream("SaveCodeDictionary.xml");
        try {
            Document document = saxReader.read(ips);//引入xml文件
            Element root = document.getRootElement();//获取根元素对象
            List<Element> sonNode = root.elements();
            for (Element sonElement : sonNode) {
                System.out.println(sonElement.getName());
                List<Element> grandsonNode = sonElement.elements();
                for (Element grandsonElement : grandsonNode) {
                    String grandsonText = grandsonElement.getText();
                    System.out.println(grandsonElement.getName() + " " + grandsonText);
                }
            }
        } catch (DocumentException e) {
            System.out.println("文件路径不对");
        }

    }

    @Test
    public void testAppointNode() {
        SAXReader saxReader = new SAXReader();
        try {
            //其实read可以直接读取url的
            Document document = saxReader.read("src/main/resources/operateFile/SaveCodeDictionary.xml");
            Element root = document.getRootElement();
            //注意：element是得到指定字段的子节点（默认第一个），并且不能够跨代。
            // 比如下面要是直接拿fileChineseName会报错(空的String)，因为root的子节点是FileManager
            Element chineseName = root.element("FileManager").element("FileChineseName");
            System.out.println("用getText获取文本： " + chineseName.getText());
            //得到文本还有一种写法，用elementText来获取文本
            String chineseNameText = root.element("FileManager").elementText("FileChineseName");
            System.out.println("用elementText获取文本： " + chineseNameText);
        } catch (DocumentException e) {
            System.out.println("文件不存在，可能是路径出问题了");
        }
    }

    @Test
    public void testXmlToMap() {
        Map<String,ArrayList<String>> xmlDictionary = new HashMap<>();
        ArrayList<String> chineseList = new ArrayList<>();
        SAXReader saxReader = new SAXReader();//创建解析器对象
        // 下面这句话代表是从这个类所在文件夹开始找指定文件
        // 文件前面加正斜杠代表源根路径：从源文件夹(src)往下的文件
        InputStream ips = TestXmlAnalysis.class.getResourceAsStream("/operateFile/SaveCodeDictionary.xml");
        try {
            Document document = saxReader.read(ips);//引入xml文件
            Element root = document.getRootElement();//获取根元素对象
            List<Element> sonNode = root.elements();
            for (Element sonElement : sonNode) {
                    String fileNameText = sonElement.elementText("FileName");
                    String fileChineseNameText = sonElement.elementText("FileChineseName");
                    String introduceText = sonElement.elementText("Introduce");
                    // 开始添加
                    chineseList.add(fileChineseNameText);
                    chineseList.add(introduceText);
                    xmlDictionary.put(fileNameText,chineseList);
            }
            String reallyName = "HelloWorld.java";
            System.out.println(reallyName+"\n"+xmlDictionary.get(reallyName).get(0)+"\n"+xmlDictionary.get(reallyName).get(1));
        } catch (DocumentException e) {
            System.out.println("文件路径不对");
        }
    }
}
