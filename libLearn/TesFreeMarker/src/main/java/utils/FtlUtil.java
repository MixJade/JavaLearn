package utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarker工具类
 *
 * @see <a href="http://freemarker.foofun.cn/ref_directive_if.html">FreeMarker教程</a>
 * @since 2024-08-20 14:28:01
 */
public final class FtlUtil {
    // 创建配置类(顺便指定默认版本)
    private static final Configuration cfa = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static {
        // 设置模板路径，这里我们设置的是class路径下的templates文件夹
        cfa.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "templates");
        // 设置字符集
        cfa.setDefaultEncoding("UTF-8");
    }

    /**
     * 填充FreeMarker模板，生成字符串
     *
     * @param dataModel 填充所用参数
     * @param tempName  模板名称
     * @return 填充后的字符串
     */
    public static String fillTempStr(Map<String, Object> dataModel, String tempName) {
        try {
            // 加载模板并渲染
            Template template = cfa.getTemplate(tempName);
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            // 返回生成的文本
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "模板转化失败";
        }
    }
}