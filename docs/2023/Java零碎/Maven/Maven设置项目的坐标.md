# Maven设置项目的坐标

* 在pom.xml中有如下配置

```xml
<project>
    <parent>
        <!--【父依赖坐标】-->
        <groupId>com.boot</groupId>
        <!--【父依赖项目名】-->
        <artifactId>parent-prj</artifactId>
        <!--【父依赖版本】-->
        <version>2.0.1-SNAPSHOT</version>
    </parent>

    <modelVersion>4.0.0</modelVersion>
    <!--【当前项目名】-->
    <artifactId>my-prj</artifactId>
    <!--【当前项目名，作用是描述】-->
    <name>我的项目</name>
    <!--【打包形式，这是打成jar包】-->
    <packaging>jar</packaging>
    <!--【当前项目坐标】-->
    <groupId>com.boot</groupId>
    <!--【当前项目对外暴露版本】-->
    <version>2.0.0-SNAPSHOT</version>

    <dependencies>
        ...各种依赖
    </dependencies>
</project>
```

