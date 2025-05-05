package pack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 在打包完成之后，手动执行
 *
 * @since 2025-05-05 10:46:51
 */
public class AfterPack {
    public static void main(String[] args) {
        // 这个路径是校验当前配置是不是默认路径的，不要改
        String defaultOutDir = System.getProperty("user.dir") + "\\target";
        if (defaultOutDir.equals(PackConfig.OUT_DIR)) {
            System.out.println("默认路径，无需迁移");
            return;
        }
        File outDirFile = new File(PackConfig.OUT_DIR);
        if (!outDirFile.exists()) {
            System.out.println("对应目录不存在，请手动迁移");
            return;
        }
        // 开始复制
        try {
            Files.copy(Paths.get("target/ROOT.war"), Paths.get(PackConfig.OUT_DIR, "ROOT.war"));
            Files.copy(Paths.get("target/server.xml"), Paths.get(PackConfig.OUT_DIR, "server.xml"));
            Files.copy(Paths.get("target/startWar.bat"), Paths.get(PackConfig.OUT_DIR, "startWar.bat"));
        } catch (IOException e) {
            System.out.println("IO异常：" + e.getMessage());
        }
    }
}
