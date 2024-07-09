# Java指定JDK启动jar

> 2024年7月9日16:38:05

* 使用这种方法就不必配置环境变量，同时也可以防止已有JDK版本不兼容的情况。
* 比如我下载了JDK放在了D盘，要启动`fly_bird-1.0-SNAPSHOT.jar`

```sh
D:\jdk-17.0.11\bin\java.exe -jar fly_bird-1.0-SNAPSHOT.jar
```

