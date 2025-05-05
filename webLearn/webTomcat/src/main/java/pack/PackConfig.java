package pack;

/**
 * 打包脚本配置,只需要改这里的配置就好
 *
 * @since 2025-05-05 10:49:26
 */
public interface PackConfig {
    String TOMCAT_PATH = "C:/tomcat/apache-tomcat-9.0.104", // Tomcat路径
            STATIC_PATH = "../../../TsLearn/ship-demo/dist", // 静态前端目录
            OUT_DIR = System.getProperty("user.dir") + "\\target"; // 最后war要运行的路径
    String WEB_PORT = "7841"; // 端口号(1024到65535之间)
}
