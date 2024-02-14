# Java桌面类

可以在一定程度上取代cmd

可以百度Desktop.class

参考：[Java中Desktop类浅析_desktop.getdesktop()_ldcaws的博客-CSDN博客](https://blog.csdn.net/leijie0322/article/details/125758658)

参考：[Swing Desktop.getDesktop() 打开本地文件、文件夹_jazwoo的博客-CSDN博客](https://blog.csdn.net/jazywoo123/article/details/7884094)

参考：[Desktop (Java SE 11 & JDK 11 ) (runoob.com)](https://www.runoob.com/manual/jdk1.6/java.desktop/java/awt/Desktop.html)

```java
import java.awt.*;
import java.io.File;

/**
 * 桌面类的测试
 */
public class TestDesktop {
    public static void main(String[] args) {
        // 从系统内获取桌面路径
        String desktop = System.getProperty("user.home") + "\\Desktop\\";
        File file = new File(desktop + "新建 文本文档.txt");
        // 将文件移入回收站
        Desktop.getDesktop().moveToTrash(file);
    }
}
```