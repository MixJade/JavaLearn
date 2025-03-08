package config;

import freemarker.template.Configuration;

/**
 * FreeMarker公共配置
 *
 * @see <a href="http://freemarker.foofun.cn/ref_directive_if.html">FreeMarker教程</a>
 * @since 2024-08-20 14:28:01
 */
public class FtlConfig {
    public static Configuration cfa;

    static {
        // 创建配置类(顺便指定默认版本)
        cfa = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 设置模板路径，这里我们设置的是class路径下的templates文件夹
        cfa.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "templates");
        // 设置字符集
        cfa.setDefaultEncoding("UTF-8");
    }
}
