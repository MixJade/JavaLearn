# SpringMVC学习笔记

> * SpringMVC是隶属于Spring框架，用于Web开发，封装了Servlet
> * 基于java实现MVC模型的web框架
> * 是表现层框架技术,主要进行表现层开发

## 关于三层架构

* 将后端服务器Servlet拆分成三层，分别是`web`、`service`和`dao`
    * web层主要由servlet来处理，负责页面请求和数据的收集以及响应结果给前端
    * service层主要负责业务逻辑的处理
    * dao层主要负责数据的增删改查操作
* 针对web层进行了优化，采用了MVC设计模式，将其设计为`controller`、`view`和`Model`
    * controller负责请求和数据的接收，接收后将其转发给service进行业务处理
    * service根据需要会调用dao对数据进行增删改查
    * dao把数据处理完后将结果交给service,service再交给controller
    * controller根据需求组装成Model和View,Model和View组合起来生成页面转发给前端浏览器

## 快速上手

* 先导入坐标
* 不会忘记设置打war包吧

```html

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.23</version>
</dependency>
```

* 再建立一个文件夹，叫webapp，里面放一个文件夹叫WEB-INF
* 这是为了让汤姆猫识别它，不然会报错
* 以往这里面还有一个web.xml，不过SpringMVC不用
* 接着创建一个Controller，注意注解
* MyController.java

```java

@Controller
public class MyController {
    @RequestMapping("/first")
    @ResponseBody//设置返回值为相应内容,不然会认为返回的是路径，从而报404
    public String first() {
        System.out.println("This is a first");
        return "{'info','springMVC'}";
    }
}
```

* 然后建立SpringMVC的环境，就是普通的配置类
* SpringMvcConfig.java

```java

@Configuration
@ComponentScan("controller")
public class SpringMvcConfig {
}
```

* 接下来让这个配置类变得不普通
* 变成SpringMVC的专属
* ServletContainerInitConfig.java

```java
public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
  @Override
  protected WebApplicationContext createServletApplicationContext() {
    //初始化WebApplicationContext对象
    AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    //加载指定配置类
    ctx.register(SpringMvcConfig.class);
    return ctx;
  }

  @Override
  protected String[] getServletMappings() {
    //设置由springmvc控制器处理的请求映射路径
    return new String[]{"/"};
  }

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    //加载spring配置类
    return null;
  }
}
```

* 接下来运行就行，不过这样不能加载index了
* 只能手动输路径