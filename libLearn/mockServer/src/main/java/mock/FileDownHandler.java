package mock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件下载处理器
 * <p>当 ApiConf 中 jsonName 以 "file://" 开头时，访问该接口会触发浏览器下载对应文件。</p>
 * <p>示例：new ApiConf("/sa", "file://xxx.json") → 访问 /sa 会下载 resources/mock/xxx.json</p>
 *
 * @since 2026-06-05
 */
public class FileDownHandler implements HttpHandler {

    /** 要下载的文件的绝对路径 */
    private final String filePath;

    public FileDownHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath  = exchange.getRequestURI().getPath();
        String requestMethod = exchange.getRequestMethod();
        long timestamp = System.currentTimeMillis();

        System.out.printf("""
                        \n[%d] 请求方法: %s, 路径: %s
                            文件下载: %s
                        """,
                timestamp, requestMethod, requestPath, filePath);

        Path file = Paths.get(filePath);
        if (!Files.exists(file)) {
            byte[] err = ("{\"error\":\"Download file not found: " + filePath + "\"}").getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(404, err.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(err);
            }
            return;
        }

        // 取文件名，用于 Content-Disposition
        String fileName = file.getFileName().toString();
        // RFC 5987 编码，支持中文文件名
        String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

        // 探测 MIME 类型，探测失败时回退到 application/octet-stream
        String mimeType = Files.probeContentType(file);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        long fileSize = Files.size(file);

        exchange.getResponseHeaders().set("Content-Type", mimeType);
        // attachment 触发浏览器下载；filename* 支持非 ASCII 文件名（RFC 5987）
        exchange.getResponseHeaders().set("Content-Disposition",
                "attachment; filename=\"" + encodedName + "\"; filename*=UTF-8''" + encodedName);
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, fileSize);

        try (OutputStream os = exchange.getResponseBody();
             InputStream is = Files.newInputStream(file)) {
            byte[] buf = new byte[8192];
            int read;
            while ((read = is.read(buf)) != -1) {
                os.write(buf, 0, read);
            }
        }
    }
}
