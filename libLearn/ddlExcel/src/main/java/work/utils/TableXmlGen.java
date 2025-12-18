package work.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.model.entity.TableName;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * 生成表结构的xml
 *
 * @since 2025-08-25 20:42:52
 */
public final class TableXmlGen {
    public static void creatXml(List<TabXmlDo> tabXmlDoList, String xmlName) {
        try {
            // 创建文档构建器工厂
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // 创建根元素 tableList
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("tableList");
            doc.appendChild(rootElement);
            for (TabXmlDo tabXmlDo : tabXmlDoList) {
                TableName tableName = tabXmlDo.tableName();
                // 第一个table元素
                Element table1 = doc.createElement("table");
                table1.setAttribute("code", tableName.tableName()); // 可以添加属性
                table1.setAttribute("name", tableName.comments());
                rootElement.appendChild(table1);

                // 表下的字段
                List<TableDDL> ddlList = tabXmlDo.tableDDLList();
                for (TableDDL tableDDL : ddlList) {
                    // 为table添加field元素
                    Element field1 = doc.createElement("field");
                    field1.setAttribute("code", tableDDL.columnName());
                    field1.setAttribute("name", tableDDL.comments());
                    field1.setAttribute("length", tableDDL.dataType());
                    // 追加元素
                    table1.appendChild(field1);
                }
            }
            // 将XML内容写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // 设置输出格式，增加缩进以提高可读性
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);

            // 输出到文件
            StreamResult fileResult = new StreamResult(new File("生成结果/" + xmlName));
            transformer.transform(source, fileResult);

            System.out.println("\nXML文件已成功生成！");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
