package testXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Java原生读取xml文件
 *
 * @since 2024-10-21 09:42:34
 */
public class ReadXMLFile {

    public static void main(String[] argv) {

        try {
            File fXmlFile = new File("src/main/resources/testXml/SaveCodeDictionary.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("FileManager");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                // 获取指定索引的节点
                Node nNode = nList.item(temp);
                // 获取节点下对应的元素
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String fileName = eElement.getElementsByTagName("FileName")
                            .item(0).getTextContent();
                    String fileCnName = eElement.getElementsByTagName("FileCnName")
                            .item(0).getTextContent();
                    String introduce = eElement.getElementsByTagName("Introduce")
                            .item(0).getTextContent();
                    System.out.println("节点" + temp);
                    System.out.println(fileName + " " + fileCnName + " " + introduce);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}