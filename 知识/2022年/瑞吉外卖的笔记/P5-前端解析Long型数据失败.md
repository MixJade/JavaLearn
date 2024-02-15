# P5-前端解析Long型数据失败

## 前端Bug处理

* 前端会自动将Long型的数据四舍五入(超过16位的部分),经常会出现点击对应的员工信息,结果出现找不到这个人的情况
* 到后端一看,几个人的id都是雪花数,但是最后两位都是0,这是因为,前端自动将long型数据四舍五入
* 处理方法就是通过在对应字段上加JsonSerialize注解,将long型传出时变成String,相应String传入变long型
* 但是太多long型变量需要改,后面会定义一个类统一处理(大概)

```
@JsonSerialize(using= ToStringSerializer.class)
private Long id;
```

## 前端Bug处理(统一处理)

> 编写一个类来统一处理,只是比上面那个麻烦

* 先建立文件JacksonMapper.java
* 虽然我只是想要转化Long数据,但LocalTime这些必须要,不然报错

```java
/**
 * 对象映射器:处理java对象转化json时的部分逻辑
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */
public class JacksonMapper extends ObjectMapper {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}
```

* 接着在WebMvcConfigurer类中配置它(就是前面所配置拦截器的类)
* 注意要采用实现接口的方式,不然又会报错
* 最后的converters.add方法,第一个参数是转换器的顺序
* spring自己有8个转换器,不将自己的放在前面,会导致传到前端之后才转换

```java

@Configuration
public class MyInterceptConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器,放行所有静态资源,以及登录相关的controller
     *
     * @param registry 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckIntercept())
                .addPathPatterns("/**")
                .excludePathPatterns("/employee/login", "/employee/logout", "/backend/**", "/front/**", "/error", "/favicon.ico");
    }

    /**
     * 扩展mvc框架的消息转换器
     *
     * @param converters mvc框架的转换器
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0, messageConverter);
    }
}

```

* 然后把之前的注解删除,可以发现成功解析到long数据