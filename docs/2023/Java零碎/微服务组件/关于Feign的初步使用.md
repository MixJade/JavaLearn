# 关于Feign的初步使用

> 我收到一个任务，要求在一个spring项目中定义一个接口，在另外一个spring项目中通过feign调用。

## 一、Maven依赖

* 当然这是早就被别人配好的

```xml
<!-- Feign HTTP客户端 -->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

* 然后在启动类上加注解：`@EnableFeignClients`

```java
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MyApplication {
    public static void main(String[] args) {
    	SpringApplication.run(MyApplication.class, args);
    }
}
```

## 三、feign的使用

### 3.1 被调用方暴露接口

* 先引入依赖

```xml
<!-- Feign HTTP客户端 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

* 然后定义接口
* **这里接口的地址要与controller中的地址一样**

```java
@Component("MyApi2")
@FeignClient(name = "我的服务名" ,contextId = "我的上下文", configuration = FeignConfiguration.class, fallbackFactory =MyFallbackFactory.class)
public interface MyApi {
	
    @GetMapping(value = serviceName+"/api/v1/query-dogs")
	Res<Page<Dog>> queryByLike(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize);

}
```

* 异常处理
* 继承上面的接口

```java
@Slf4j
@Component("MyFallbackFactory2")
public class MyFallbackFactory implements FallbackFactory<MyApi> {
	@Override
	public MyApi create(Throwable throwable) {
		  log.error("异常原因:{}", throwable.getMessage(), throwable);
			return new MyApi(){
				@Override
				public Res<Page<Dog>> queryByLike(
				           Integer pageNum,
				           Integer pageSize){
						return Res.error("出现异常");
					}
				
			};
        
	}
}
```

### 3.2 调用方引入依赖

被调用的服务要与调用方同级，这样可以在pom中导入对应的服务。

```xml
<!-- 调用其它服务接口-->
<dependency>
    <groupId>com.boot</groupId>
    <artifactId>我的服务名</artifactId>
    <version>2.0.0-SNAPSHOT</version>
</dependency>
```

然后直接用接口中的方法就行。

### 3.3 引用服务直接调用方法

```java
public class 另一个项目的类 {

    public void hhh() {
        Res<Page<Dog>> dogs = MyApi.queryByLike(1,5);
    }
}
```

