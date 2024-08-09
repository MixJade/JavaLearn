# Nacos一直连接本地而非远程

报错：

```text
2022-02-24 14:50:26.778 ERROR 13070 --- [           main] c.a.n.c.config.http.ServerHttpAgent      : [NACOS ConnectException httpGet] currentServerAddr:http://localhost:8848, err : Connection refused (Connection refused)
```

描述：

Nacos在启动之后，一直连接localhost:8848，而非远程的服务器，但打开appication.yaml，却发现服务器地址已经配置了。

**问题解决**

这是nacos读取本身自动配置的优先级高于application文件中的配置时引起的，而nacos本身的自动配置是127.0.0.1:8848端口的nacos服务，所以发生了以上异常，故而需要将配置文件的优先级提升

创建一个bootstrap.properties或bootstrap.yml文件配置nacos地址就可以了。这个配置是系统级的，优先级最高，先从这个文件读取nacos地址就不会报错了。