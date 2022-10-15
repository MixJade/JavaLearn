# JavaWeb练习

> 本项目基于apache-tomcat-8.5.83运行

因为tomcat10实现的api的主要包已经从javax变成jakarta，与我所搜罗到的教学视频脱节
~~我被这个折磨好久~~

`pom.xml`可以配置插件`tomcat7-maven`启动，比较方便，
> 但就是太方便了，所以我后来用tomcat启动

```
<plugin>
    <groupId>org.apache.tomcat.maven</groupId>
    <artifactId>tomcat7-maven-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <port>8086</port>
        <path>/</path>
        <server>tomcat7</server>
    </configuration>
</plugin>
```

## cookie笔记

> CookieAdd.java 与 CookieGet.java

1. **添加cookie**
    ```
    Cookie cookie=new Cookie("username","MixJade");
    resp.addCookie(cookie);
    ```
2. **设置cookie存活时间**(比如设置存活一个小时)
   ```cookie.setMaxAge(60*60);```
   不设置的话默认为关闭浏览器后销毁

|   时间    | 生命周期         |
|:-------:|:-------------|
|   正数    | 到时间后自动删除     |
| 负数(默认)  | 浏览器关闭后删除     |
|    零    | 当场删除对应cookie |

3. **cookie中文乱码**
   我不知道哇，听说现在浏览器都支持中文了，tomcat8.0的版本也解决中文了