# 提取IDEA启动项目命令

> 2026-02-12 14:06:14

* 有的时候，我们只需要在本地运行某个项目，而不关心它的具体运行过程。（只是给自己的项目提供一个第三方系统而已）
* 但使用IDEA新开一个窗口又太占内存，所以只能脱离idea启动

## 操作

1. 先使用IDEA启动项目，提取它的启动命令。

2. 在启动文件中如果有类似于**类路径文件的临时文件**，在不停止项目的前提下去提取它

   ```bash
   -- 大概类似下面这样
   %userprofile%\AppData\Local\Temp\idea_arg_file697750477
   ```

3. 将提取到的临时文件保存到一个全英文路径的目录，比如`E:\temp\ms-task`

4. **删除**启动命令中IDEA相关的内容，比如：

   ```bash
   "-javaagent:D:\IDE\IntelliJ IDEA 2022.2.5\lib\idea_rt.jar=49932:D:\IDE\IntelliJ IDEA 2022.2.5\bin"
   ```

5. 整理成一个cmd文件，注意开头需通过`cd /d`切换目录，连带切换盘符

## 最终成果

* cmd启动脚本

```bash
@echo off
chcp 65001 >nul
cd /d D:\proj\backend\ms-task
D:\jdk-17.0.11\bin\java.exe -XX:TieredStopAtLevel=1 -Dspring.profiles.active=local -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true -Dfile.encoding=UTF-8 "@E:\temp\ms-task\idea_arg_file697750477" com.boot.MsTaskApplication
pause
```

* 类路径文件（`idea_arg_file697750477`）

> （以下引入的库路径已省略部分）

```txt
-classpath
D:\proj\backend\ms-task-core\target\classes;D:\proj\backend\ms-task-core\target\classes;D:\proj\backend\ms-task-entity\target\classes;D:\apache-maven-3.8.6\mavRepository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\apache-maven-3.8.6\mavRepository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\apache-maven-3.8.6\mavRepository\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar

```

