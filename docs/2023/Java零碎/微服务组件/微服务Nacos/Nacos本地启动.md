# Nacos本地启动

> 2024-08-09 15:12:23

* nacos官网：[Nacos官网](https://nacos.io/)
* nacos的1.x版本启动文档[Nacos 快速开始](https://nacos.io/docs/v1/quick-start/)

## 一、下载

* 从百度网盘或GitHub下载：官网（https://github.com/alibaba/nacos）
* 建议下载版本小于2.1.0的，因为2.2.0版本以后会强制鉴权，配置很麻烦。
* 下载了：`nacos-server-1.4.5.zip`；本地解压，比如我解压到D盘

## 二、运行

* 自己新建一个cmd文件(注意cd后面的是自己nacos的bin文件夹路径)，内容如下：

```sh
cd D:\nacos\nacos-server-1.4.5\nacos\bin
./startup.cmd -m standalone
```

* 然后双击即可启动
* 启动成功后，浏览器打开网址：http://localhost:8848/nacos/index.html#/login
* 默认用户名/密码：nacos/nacos
* 接下来去【命名空间】新建一个命名空间，把生成的“命名空间ID”放自己配置文件里就行
* 当然相应的nacos地址也得改成`127.0.0.1:8848`

## 三、可能的报错

* 可能启动的时候，咱项目的控制台会报错：

```
java.net.ConnectException: [NACOS HTTP-POST] The maximum number of tolerab
```

* 是因为`application.properties`和`bootstrap.properties`文件的nacos配置地址不统一。
* 都统一一下就好了。

