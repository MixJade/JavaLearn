package com.doc.outdocx.controller;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.doc.outdocx.util.DocGenera;
import com.doc.outdocx.vo.MyParam;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class DocController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/downloadDoc")
    public String downloadDoc(HttpServletResponse response, @RequestBody MyParam param) throws Exception {
        // 此处省略生成doc对象的操作
        Document doc = DocGenera.getExampleDoc(param);
        // 设置文件名
        String fileName = "my-doc.docx";
        // 设置响应信息
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "form-data;fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        logger.info(URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        OutputStream os = response.getOutputStream();
        doc.save(os, SaveFormat.DOCX);
        os.flush();
        os.close();
        logger.info("下载");
        return fileName;
    }
}
