# Maven打包源代码

> 2024年3月15日21:51:53

一些maven项目使用`package`命令会生成一下两个文件：

* ship-0.0.1-SNAPSHOT.jar【这是整个项目的class文件】
* ship-0.0.1-SNAPSHOT-sources.jar 【这个sources.jar是源代码文件,用来看的】

源代码JAR包(source jar)是一个编译好的JAR文件，它包含了项目的源代码，通常这个JAR文件主要用于发布和共享，可以方便其他开发者阅读和理解代码。

源代码JAR包一般用于开源第三方库，当你将项目发布到公共Maven仓库时，通常需要提供源代码JAR包。

在Maven项目中，可用maven-source-plugin插件来生成源代码JAR包。具体配置如下：

```xml
<build>
    <!-- Maven打包插件(不会打包其他依赖),一般用于第三方库的打包-->
    <!-- 我这种的用SpringBoot的打包插件就行,那个会打包依赖,打完了包能独立运行-->
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>17</source>
                <target>17</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
        <!-- 加入这个插件,会把源代码文档给打包进去-->
        <plugin>
            <artifactId>maven-source-plugin</artifactId>
            <configuration>
                <attach>true</attach>
            </configuration>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
