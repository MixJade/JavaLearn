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

## session笔记

> SessionAdd.java和SessionGet.java  
> 服务器端创建一个Session，同时会创建一个特殊的Cookie给浏览器  
> 这个cookie: name为JSESSIONID的固定值，value为session对象的ID

1. **session与cookie不同,它是保存在服务端的，且默认保存30分钟**(它是基于cookie实现的)    
   它可以  
   `setAttribute`设置键值对、`getAttribute`根据键取值、`removeAttribute`根据键删值

2. **session保存在服务端**
   > 但浏览器的cookie没了一样找不到session

   **钝化**:服务器正常关闭后，tomcat会将session写入磁盘  
   **活化**:再次启动服务器，从文件加载进session

|       操作        |          反馈          |
|:---------------:|:--------------------:|
| 直接关闭tomcat不关浏览器 |     session无法保存      |
|  不关tomcat关浏览器   | cookie没了,session就没找到 |
| 正常关闭tomcat不关浏览器 |  重新启动服务端可以找到session  |

3. **session默认保存30分钟**
   > 但是可以通过web.xml设置

   比如存活32分钟
   ```
    <session-config>
        <session-timeout>32</session-timeout>
    </session-config>
   ```

4. **session的销毁**
   > session.invalidate();

## session与cookie的区别

> 都是为了完成一次会话内多次数据共享

区别:

1. 存储位置：cookie在浏览器，session在服务器
2. 安全性：cookie不安全，容易泄露
3. 数据大小: cookie不能超过3k,session无限制
4. 存储时间: cookie可以长期存储,session默认30分钟
5. 服务器性能: session会占用服务器资源,cookie不会