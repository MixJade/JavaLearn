# 启动tomcat项目

> 写作时间：2024年2月15日22:33:49

因为我想启动以前写的学生管理系统2.0，却突然不会启动了，只好现场百度，现在写下启动教程

## 一、下载tomcat

> 下载的版本要低于10，因为tomcat10实现的api的主要包已经从javax变成jakarta

我下载的是8.5.98的window64位zip版本

下载地址：[Apache Tomcat 8 Software Downloads](https://tomcat.apache.org/download-80.cgi#8.5.98)

## 二、配置tomcat

1. 找个目录解压tomcat的zip即可
2. 在idea中加入tomcat的插件，专业版会自带，叫 TomcatAndTomEE，社区版的要自己下载插件，叫 SmartTomcat
3. 在idea运行配置处，配置tomcat的各项配置，并指定项目编译后的war(如何配置自行百度)
4. 中文乱码解决方案：
   打开conf/logging.properties文件
   修改java.util.logging.ConsoleHandler.encoding = GBK
5. 默认的本地端口http://localhost:8080/，但斜杠后面一般会加上项目名

## 三、启动项目

如果tomcat的配置中，指定了项目启动后==》打开浏览器==》默认打开Edge，

那么只要在idea中正常启动就行。

(不然就得maven编译，然后将war包扔到tomcat中才行)