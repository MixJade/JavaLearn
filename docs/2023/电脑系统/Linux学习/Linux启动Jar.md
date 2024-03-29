# Linux启动Jar

后面的jar包名可以直接将对应的jar包拖入终端获得。

```bash
java -jar ship-0.0.1-SNAPSHOT.jar
```

## 端口占用解决方法

* 查找8085端口的占用程序的PID
* 发现是16329,为了保险，查看一下
* 然后将之kill掉

```bash
netstat -anp|grep 8085
ps -aux|grep -v grep|grep 16329
kill -9 16329
```
