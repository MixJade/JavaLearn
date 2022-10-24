# 注解开发

## 注解开发初入手

* 在类上写Component注解
* Component分了三个衍生注解
    * Controller:表现层bean
    * Service:业务层bean
    * Repository:数据层bean
* 注解里面的别名可以不写，到时候会按照类型来自动装配

```java

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService {
    public BookServiceImpl() {
        System.out.println("hhhhhhhhhhh");
    }

    @Override
    public void deposit() {
        System.out.println("You are saving a book");
    }
}
```

* 在配置文件中如此定义(这个扫描包在context命名空间，不能改)
* 这个可以被另外一种方式(配置类)替代

```html
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
">
    <context:component-scan base-package="dao service"/>
</beans>
```

* 通过配置类来彻底干掉配置文件
* 创建一个配置类
* `@Configuration`声明配置类
* `@ComponentScan`定义包扫描路径

```java

@Configuration
@ComponentScan({"dao", "service"})
public class SpringConfig {
}
```

* 接着把加载配置文件的语句换成

```
ApplicationContext acx=new AnnotationConfigApplicationContext(SpringConfig.class);
```

* 然后就可以将配置文件删除了
* 使用这种方法默认延迟加载

## bean管理

> * 关于注解开发，怎么管理作用域以及生命周期
> * 管理作用域:`@Scope("prototype")`或者`@Scope("singleton")`
> * 生命周期`@PostConstruct`(在构造方法后)初始化，`@PreDestroy`(在销毁之前)销毁方法
> * 注意的是,关于生命周期的这两个注解在jdk9中被弃用，在jdk11中被删除
> * 现在使用需要引入一个包

```html
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```
* 代码示例如下
```java
@Service()
@Scope("singleton")
public class BookServiceImpl implements BookService {
  public BookServiceImpl() {
    System.out.println("hhhhhhhhhhh");
  }
  @Override
  public void deposit() {
    System.out.println("You are saving a book");
  }

  @PostConstruct
  public void init(){
    System.out.println("这是在构造方法之后");
  }
  @PreDestroy
  public void destroy(){
    System.out.println("只有单例作用域才会执行销毁方法");
    System.out.println("这是在调用关闭钩子后，在销毁之前");
  }
}
```
```
public void testIoC() {
    AnnotationConfigApplicationContext acx = new AnnotationConfigApplicationContext(SpringConfig.class);
    BookService bookService = acx.getBean(BookService.class);
    BookService bookService01 = acx.getBean(BookService.class);
    System.out.println(bookService01);
    System.out.println(bookService);
    bookService.deposit();
    acx.close();
}
```