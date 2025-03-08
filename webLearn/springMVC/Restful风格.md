# Restful风格尝试

> 通过vue,axios,element进行前端的搭建

* 值得注意
* 由于springMvc会默认拦截所有资源
* 所以需要新建一个配置类以继承WebMvcConfigurationSupport类
* 在之下对相关静态资源进行放行

```java

@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {
    //设置静态资源访问过滤，当前类需要设置为配置类，并被扫描加载
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问/pages/????时候，从/pages目录下查找内容
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
    }
}
```

* 附带restful风格的请求

```
//添加
saveBook() {
    axios.post("http://localhost:8080/restfulTest/books", this.formData).then((res) => {

    });
},

//主页列表查询
getAll() {
    axios.get("http://localhost:8080/restfulTest/books").then((res) => {
        this.dataList = res.data;
    });
},
```