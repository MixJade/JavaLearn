# IDEA编译项目报错合集

> 2024年6月24日10:39

## import突然找不到自己项目的包

是否有过以下情况：打开一个本来能运行却从来没有动过的项目，突然提示一堆报错，一看是自己的类在import语句上报错，但是哪怕是报错了，仍然可以运行。

事实上，只是因为IDEA的文件缓存乱了，可能是你之前用IDEA打开了这个项目的上级目录。只要打开**「文件」-「文件缓存」-「清除文件系统缓存」**即可

## 启动项目报错【内部java编译器错误】

* 本质是maven没有设置好目标字节码版本，只能自己手动弄
* 以下以java8来举例：

解决办法:

【文件】-【构建、执行、部署】-【编译器】-【Java编译器】

将目标字节码版本 全都改成 1.8 即可

改成 8 也行

## 启动项目报错【java不支持发行版本6】

* 这个报错也是因为maven没有设置语言级别导致的。

* 打开【项目结构】-【模块】
* 为每个模块分别设置好自己项目的语言级别即可。

## 编译空间不足

参考[idea启动项目报错java.lang.OutOfMemoryError: WrappedJavaFileObject-CSDN博客](https://blog.csdn.net/menghuannvxia/article/details/128966738)

报错如下：

```text
java: java.lang.OutOfMemoryError: WrappedJavaFileObject[org.jetbrains.jps.javac.InputFileObject[...具体的java文件]]@pos200: Java h
```

* 打开【文件】-【设置】-【编译器】
* 修改【共亨构建进程堆大小(MB):】，从默认的700改成1500