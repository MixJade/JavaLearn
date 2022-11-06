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

web容器 --~~包含~~-- ServletContext --~~包含~~-- WebApplicationContext --~~包含~~-- MyController -- ~~包含~~-- /first
--~~包含~~-- first方法

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

## 总的路径映射

> * 有的时候希望按照文件区分service路径
> * 只需要在类的开头加上RequestMapping
> * 这样访问即可
> * http://localhost:8080/springMVC/myController/first

```java

@Controller
@RequestMapping("/myController")
public class MyController {
    @RequestMapping("/first")
    @ResponseBody//设置返回值为相应内容,不然会认为返回的是路径，从而报404
    public String first() {
        System.out.println("This is a first");
        return "{'info','springMVC'}";
    }

    @RequestMapping("/second")
    @ResponseBody//设置返回值为相应内容,不然会认为返回的是路径，从而报404
    public String second() {
        System.out.println("This is a second");
        return "{'info','second'}";
    }
}
```

## 乱码处理

> * tomcat8之后默认编码都是utf-8
> * 如果中文乱码，可以在配置类里进行重写过滤器方法，设置utf-8
> * 仅对post请求有效,get不行

```java
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
    /*
    乱码处理
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        return new Filter[]{filter};
    }*/
}
```

## 参数传递

* 关于json数据，需要引入坐标

```html

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.13.4.2</version>
</dependency>
```

* 然后在mvc的配置类中添加注解EnableWebMvc

```java
@ComponentScan("com.controller")
//开启json数据类型自动转换
@EnableWebMvc
public class SpringMvcConfig {
}
```

* 然后是关于各参数的处理
```java
@Controller
@RequestMapping("/param")
public class ParamTest {
    //普通参数：请求参数与形参名称对应即可完成参数传递
    //common?name=张铁蛋&age=22
    @RequestMapping("/common")
    @ResponseBody
    public String commonParam(String name, int age) {
        System.out.println("普通参数传递 name ==> " + name);
        System.out.println("普通参数传递 age ==> " + age);
        return "{'module':'common param'}";
    }

    //普通参数：请求参数名与形参名不同时，使用@RequestParam注解关联请求参数名称与形参名称之间的关系
    //commonOtherName?name=李铁军&age=89
    @RequestMapping("/commonOtherName")
    @ResponseBody
    public String commonParamDifferentName(@RequestParam("name") String userName, int age) {
        System.out.println("普通参数传递 userName ==> " + userName);
        System.out.println("普通参数传递 age ==> " + age);
        return "{'module':'common param different name'}";
    }

    //POJO参数：请求参数与形参对象中的属性对应即可完成参数传递
    //pojo?name=葛二蛋&age=80
    @RequestMapping("/pojo")
    @ResponseBody
    public String pojoParam(User user) {
        System.out.println("pojo参数传递 user ==> " + user);
        return "{'module':'pojo param'}";
    }

    //嵌套POJO参数：嵌套属性按照层次结构设定名称即可完成参数传递
    //pojoContain?name=股市场&age=78&address.province=四川&address.city=成都
    @RequestMapping("/pojoContain")
    @ResponseBody
    public String pojoContainPojoParam(User user) {
        System.out.println("pojo嵌套pojo参数传递 user ==> " + user);
        return "{'module':'pojo contain pojo param'}";
    }

    //数组参数：同名请求参数可以直接映射到对应名称的形参数组对象中
    //array?likes=吃饭&likes=睡觉&likes=打豆豆
    @RequestMapping("/array")
    @ResponseBody
    public String arrayParam(String[] likes) {
        System.out.println("数组参数传递 likes ==> " + Arrays.toString(likes));
        return "{'module':'array param'}";
    }

    //集合参数：同名请求参数可以使用@RequestParam注解映射到对应名称的集合对象中作为数据
    //list?likes=吃饭&likes=睡觉&likes=打豆豆
    @RequestMapping("/list")
    @ResponseBody
    public String listParam(@RequestParam List<String> likes) {
        System.out.println("集合参数传递 likes ==> " + likes);
        return "{'module':'list param'}";
    }


    //集合参数：json格式
    //1.开启json数据格式的自动转换，在配置类中开启@EnableWebMvc
    //2.使用@RequestBody注解将外部传递的json数组数据映射到形参的集合对象中作为数据
    //在postMan中设置json数据： - Body - raw -JSON
    //["吃饭","碎觉","扣豆豆"]
    @RequestMapping("/listParamForJson")
    @ResponseBody
    public String listParamForJson(@RequestBody List<String> likes) {
        System.out.println("list common(json)参数传递 list ==> " + likes);
        return "{'module':'list common for json param'}";
    }

    //POJO参数：json格式
    //1.开启json数据格式的自动转换，在配置类中开启@EnableWebMvc
    //2.使用@RequestBody注解将外部传递的json数据映射到形参的实体类对象中，要求属性名称一一对应
    //{"name":"额尔丹","age":80}
    @RequestMapping("/pojoParamForJson")
    @ResponseBody
    public String pojoParamForJson(@RequestBody User user) {
        System.out.println("pojo(json)参数传递 user ==> " + user);
        return "{'module':'pojo for json param'}";
    }

    //集合参数：json格式
    //[{"name":"额尔丹","age":80},{"name":"王久丹","age":34}]
    @RequestMapping("/listPojoParamForJson")
    @ResponseBody
    public String listPojoParamForJson(@RequestBody List<User> list) {
        System.out.println("list pojo(json)参数传递 list ==> " + list);
        return "{'module':'list pojo for json param'}";
    }

    //日期参数
    //使用@DateTimeFormat注解设置日期类型数据格式，默认格式yyyy/MM/dd
    @RequestMapping("/dataParam")
    @ResponseBody
    public String dataParam(Date date,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                            @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date2) {
        System.out.println("参数传递 date ==> " + date);
        System.out.println("参数传递 date1(yyyy-MM-dd) ==> " + date1);
        System.out.println("参数传递 date2(yyyy/MM/dd HH:mm:ss) ==> " + date2);
        return "{'module':'data param'}";
    }
}
```