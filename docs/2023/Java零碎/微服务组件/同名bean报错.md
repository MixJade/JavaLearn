# 同名bean报错

* 项目中有两个同名类都叫MyComponent，只是放在不同的包名下

```java
package com.pack01;

@Component
public class MyComponent {
}
```
```java
package com.pack02;

@Component
public class MyComponent {
}
```

* 当项目启动时会报bean冲突的错误

```text
bean name 'MyComponent' for bean class [com.pack02.MyComponent] conflicts with existing, non-compatible bean definition of same name and class [com.pack01.MyComponent]
	at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:188)
```

* 问题分析：

* 由于以上两个同名类在用@Component注解的时候未指定value值，所以Spring Boot默认会以类名作为bean name，导致bean name相同的冲突

* 可以通过为同名类指定不同的bean name来解决问题，上面两个类随便改一个

```java
package com.pack01;

@Component("MyComponent1")
public class MyComponent {
}
```
