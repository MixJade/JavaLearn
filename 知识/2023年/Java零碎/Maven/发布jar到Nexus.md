# 发布jar到Nexus

* 需要在pom文件中指定私服的URL位置
* 需要在Maven的setting.xml文件中，设置私服的账号密码
* 参考：https://developer.aliyun.com/article/1285846

## pom设置参考

```xml
<project>
    <parent>
        <groupId>com.boot</groupId> <!--【父依赖坐标】-->
        <artifactId>parent-prj</artifactId><!--【父依赖项目名】-->
        <version>2.0.1-SNAPSHOT</version><!--【父依赖版本】-->
    </parent>

    <modelVersion>4.0.0</modelVersion>
    <artifactId>my-prj</artifactId><!--【当前项目名】-->
    <name>我的项目</name><!--【当前项目名，作用是描述】-->
    <packaging>jar</packaging><!--【打包形式，这是打成jar包】-->
    <groupId>com.boot</groupId><!--【当前项目坐标】-->
    <version>2.0.0-SNAPSHOT</version><!--【当前项目对外暴露版本】-->

    <!--【Nexus私服地址】-->
    <distributionManagement>
      <repository>
          <id>nexus-releases</id>
          <name>Local Nexus Repository</name>
          <url>http://192.168.1.99:8081/content/repositories/releases</url>
      </repository>
      <snapshotRepository>
          <id>nexus-snapshots</id>
          <name>Local Nexus Repository</name>
          <url>http://192.168.1.99:8081/content/repositories/snapshots</url>
      </snapshotRepository>
  	</distributionManagement>
     <!-- ...各种依赖 -->
    <dependencies>
        ...各种依赖
    </dependencies>
</project>
```

## setting设置参考

```xml
<servers>
	<server>
      <id>nexus-releases</id>
      <username>deployment</username>
      <password>deploydv89</password>
    </server>
 
    <server>
      <id>nexus-snapshots</id>
      <username>deployment</username>
      <password>deploydv89</password>
   </server>
</servers>
```

