# Maven将依赖一起打包

> 2024年8月5日21:17:06

## 一、捆绑打包

* 将所有的依赖连着源代码一起打成jar包，用于临时分享。

* pom.xml

```xml
<!-- 将依赖一起打包-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <!-- 不生成dependency-reduced-pom.xml文件-->
                <createDependencyReducedPom>false</createDependencyReducedPom>
                <!-- 配置主类-->
                <transformers>
                    <transformer
                                 implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>jade.MainWindow</mainClass>
                    </transformer>
                </transformers>
                <artifactSet/>
                <filters>
                    <filter>
                        <artifact>*:*:*</artifact>
                        <!-- 排除以下文件，防止程序启动启动时，校验错误  -->
                        <excludes>
                            <exclude>META-INF/*.SF</exclude>
                            <exclude>META-INF/*.DSA</exclude>
                            <exclude>META-INF/*.RSA</exclude>
                        </excludes>
                    </filter>
                </filters>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 二、复制打包

* 将所有依赖放在与源代码jar同级的lib目录下，用于长期维护，这样只替换源代码的jar包就行
* 注意：运行jar包时，其依赖也得是同级的lib目录下

```xml
<!--将所有依赖复制进生成jar包旁的lib目录中,这个配置可以只在第一次运行时加-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.6.0</version>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>package</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.directory}/lib</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

