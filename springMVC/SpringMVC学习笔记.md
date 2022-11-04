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
@ComponentScan("com/controller")
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

## SpringMVC执行流程
共分两个阶段来分析，分别是`启动服务器初始化过程`和`单次请求过程`

web容器 --~~包含~~-- ServletContext --~~包含~~-- WebApplicationContext --~~包含~~-- MyController -- ~~包含~~-- /first --~~包含~~-- first方法
### 启动服务器初始化过程

1. 服务器启动，执行ServletContainersInitConfig类，初始化web容器
    * 功能类似于以前的web.xml
2. 执行createServletApplicationContext方法，创建了WebApplicationContext对象
    * 该方法加载SpringMVC的配置类SpringMvcConfig来初始化SpringMVC的容器
3. 加载SpringMvcConfig配置类
4. 执行@ComponentScan加载对应的bean
    * 扫描指定包及其子包下所有类上的注解，如Controller类上的@Controller注解
5. 加载UserController，每个@RequestMapping的名称对应一个具体的方法
    * 此时就建立了 `/save` 和 save方法的对应关系
6. 执行getServletMappings方法，设定SpringMVC拦截请求的路径规则
    * `/`代表所拦截请求的路径规则，只有被拦截后才能交给SpringMVC来处理请求

### 单次请求过程

1. 发送请求`http://localhost/save`
2. web容器发现该请求满足SpringMVC拦截规则，将请求交给SpringMVC处理
3. 解析请求路径/save
4. 由/save匹配执行对应的方法save(）
    * 上面的第五步已经将请求路径和方法建立了对应关系，通过/save就能找到对应的save方法
5. 执行save()
6. 检测到有@ResponseBody直接将save()方法的返回值作为响应体返回给请求方

## 关于排除mvc的bean

> 在扫描所有包时，要排除mvc的bean
> 通过excludeFilters注解,来排除特定注解的bean

```java

@Configuration
@ComponentScan(value = "com",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Controller.class
        )
)
@Import(SpringMvcConfig.class)
public class SpringConfig {
}
```

最后一个问题，有了Spring的配置类，要想在tomcat服务器启动将其加载，我们需要修改ServletContainersInitConfig

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
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringConfig.class);
        return ctx;
    }
}
```

对于上述的配置方式，Spring还提供了一种更简单的配置方式，可以不用再去创建`AnnotationConfigWebApplicationContext`
对象，不用手动`register`对应的配置类，如何实现?

```java
//web配置类简化开发，仅设置配置类类名即可
public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```