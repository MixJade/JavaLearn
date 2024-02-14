package com.doc.outdocx.util;

import com.aspose.words.Font;
import com.aspose.words.*;
import com.doc.outdocx.vo.MyParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class DocGenera {
    private static final Logger logger = LoggerFactory.getLogger(DocGenera.class);

    public static Document getExampleDoc(MyParam param) throws Exception {
        // 创建一个新的Word文档
        Document doc = new Document();
        // 添加文本到文档
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("Hello, world!");
        // 将表格添加到文档中
        builder.insertCell();
        builder.write("Row 1, Cell 1");
        builder.insertCell();
        builder.write("Row 1, Cell 2");
        builder.endRow();
        builder.insertCell();
        builder.write("Row 2, Cell 1");
        builder.insertCell();
        builder.write("Row 2, Cell 2");
        builder.endTable();
        // 写一个自己的文本
        builder.writeln(String.format("""
                尊敬的%s，你好！
                我们有%s，售价%s美元
                ，希望你%s
                """, param.text1(), param.text2(), param.text3(), param.text4()));
        // 将图像插入文档中
         builder.insertImage("https://ts1.cn.mm.bing.net/th/id/R-C.4863a753e2e0bf0c07b3126a4e74aeca?rik=N4InHKa%2bowsHIQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbbpic%2f94%2f4194.jpg&ehk=VdLyn0AXfOtVfJNoRvXVr%2fc8BVNCDSjEmt3r%2b7kQSCg%3d&risl=&pid=ImgRaw&r=0");
        // 将格式应用于文本
        Font font = builder.getFont();
        font.setSize(16);
        font.setBold(true);
        font.setColor(Color.BLUE);
        // 将格式应用于段落
        ParagraphFormat format = builder.getParagraphFormat();
        format.setAlignment(ParagraphAlignment.CENTER);
        // 保存文档
        // doc.save("output.docx");
        logger.info("生成成功");
        return doc;
    }
}
