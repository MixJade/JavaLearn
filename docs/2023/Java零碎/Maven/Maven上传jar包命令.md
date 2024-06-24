# Maven上传jar包命令

> 2024年6月24日20:57:09

## 一、本地jar上传至本地仓库

> 建议直接使用maven的install插件

### 1.1 样例

```bash
# 本地jar上传

mvn install:install-file -Dfile=D:\ppp\my-jar\ms-config\target\ms-config-1.0.0-SNAPSHOT.jar "-DgroupId=mix.jade" "-DartifactId=my-jar" "-Dversion=1.0.0-SNAPSHOT" "-Dpackaging=jar"
```

### 1.2 语法说明

* `mvn install:install-file`： maven命令，往本地仓库中安装文件
* `-Dfile`：上传源文件路径，可写绝对路径
* `-DgroupId`： groupId名称
* `-DartifactId`：artifactId名称
* `-Dversion`： 依赖包的版本号
* `-Dpackaging`：打包方式，一般为jar

## 二、本地maven的jar上传私服

* 要在 `maven`的`settings.xml`中配置第三方仓库的账号密码信息
* 同时，如果使用`deploy`插件，对应项目pom文件也需设置，详见：`发布jar到Nexus.md`

### 2.1 样例

```bash
mvn deploy:deploy-file "-Dfile=D:\apache-maven-3.6.3\repository\mix\jade\my-jar\1.0.0-SNAPSHOT\my-jar-1.0.0-SNAPSHOT.jar" -DrepositoryId=snapshots -Durl=http://xxxx.com/repository/maven-snapshot -DpomFile=D:\apache-maven-3.6.3\repository\mix\jade\my-jar\1.0.0-SNAPSHOT\my-jar-1.0.0-SNAPSHOT.pom -Dpackaging=jar --settings D:\apache-maven-3.6.3\conf\settings.xml
```

### 2.2 语法说明

* `mvn deploy:deploy-file`：maven版本发布命令
* `-Dfile`：上传源文件路径，可写绝对路径
* `-DgroupId`： groupId名称
* `-DartifactId`：artifactId名称
* `-Dversion`： 依赖包的版本号
* `-settings`： 指定maven的`setting`文件
* `-Dpackaging`：打包方式，一般为jar
* `-DpomFile`: 指定对应的`Pom`文件
* `-Durl` ：私服的地址
* `-DrepositoryId `： 私服地址的id

## 三、本地maven的pom上传私服

* 上传完jar包，记得上传jar对应的`pom`文件，当然也是maven仓库里面的
* 这里的上传

```bash
mvn deploy:deploy-file -Dfile=D:\apache-maven-3.6.3\repository\mix\jade\my-jar\1.0.0-SNAPSHOT\my-jar-1.0.0-SNAPSHOT.pom -DrepositoryId=snapshots -Durl=http://xxxx.com/repository/maven-snapshot -DpomFile=-Dfile=D:\apache-maven-3.6.3\repository\mix\jade\my-jar\1.0.0-SNAPSHOT\my-jar-1.0.0-SNAPSHOT.pom -Dpackaging=pom --settings D:\apache-maven-3.6.3\conf\settings.xml
```

