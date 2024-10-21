package testXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Java原生读取xml文件的标签属性
 *
 * @since 2024-10-21 11:09:59
 */
public class ReadXMLAttr {
    public static void main(String[] args) {

        try {
            File fXmlFile = new File("src/main/resources/testXml/testAttr.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("People");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                // 获取指定索引的节点
                Node nNode = nList.item(temp);
                // 获取节点下对应的元素的属性
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getAttribute("name");
                    String age = eElement.getAttribute("age");
                    String like = eElement.getAttribute("like");
                    System.out.println(name + " " + age + " " + like);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
