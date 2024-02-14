package operateFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 绝对路径：/根元素/子元素/孙元素"
 * 全文检索：//指定元素
 * 属性查找：//@属性名 、 //元素[@属性名] 、 //元素//[@属性名='值']
 */
public class TestXPath {
    @Test
    public void xpathParse01() {
        SAXReader saxReader = new SAXReader();
        InputStream is = TestXPath.class.getResourceAsStream("SaveCodeDictionary.xml");
        try {
            Document document = saxReader.read(is);
            List<Node> chineseNames = document.selectNodes("//FileChineseName");
            for (Node chineseName : chineseNames) {
                System.out.println(chineseName.getText());
            }
        } catch (DocumentException e) {
            System.out.println("xml路径有问题");
        }
    }

    @Test
    public void xpathParse02() {
        SAXReader saxReader = new SAXReader();
        InputStream is = TestXPath.class.getResourceAsStream("SaveCodeDictionary.xml");
        try {
            Document document = saxReader.read(is);
            List<Node> fileManagers = document.selectNodes("/FileGather/FileManager");
            for (Node fileManager : fileManagers) {
                Element mixFile = (Element) fileManager;
                String fileName = mixFile.element("FileName").getTextTrim();//这个方法意思是去掉空格获取文本
                String fileChineseName = mixFile.element("FileChineseName").getTextTrim();
                String introduce = mixFile.element("Introduce").getTextTrim();
                System.out.println(fileName);
                System.out.println(fileChineseName);
                System.out.println(introduce);
            }
        } catch (DocumentException e) {
            System.out.println("xml路径有问题");
        }
    }

}
