# 关于Feign的初步使用

> 我收到一个任务，要求在一个springboot项目中定义一些授权方面的代码，在另外一个项目中通过接口调用，这种情况，我只能临时去学一下Feign

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
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MyApplication {
    public static void main(String[] args) {
    	SpringApplication.run(MyApplication.class, args);
    }
}
```

## 附：feign的使用

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
* 这里接口的地址要与controller中的地址一样

```java
@Component("TestApi2")
@FeignClient(name = TestApi.serviceName ,contextId = TestApi.contextId, configuration = FeignConfiguration.class, fallbackFactory =TestFallbackFactory.class)
public interface TestApi {
	/**
	 * 服务名称
	 */
	String serviceName =  "ms-core";
	/**
	 * 上下文ID
	 */
	String contextId =  "ms-test";
	
    @GetMapping(value = serviceName+"/api/v1/query-proc-ids")
	RestResp<Page<TestUser>> queryByLike(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize);

}
```

* 异常处理
* 继承上面的接口

```java
@Slf4j
@Component("TestFallbackFactory2")
public class TestFallbackFactory implements FallbackFactory<TestApi> {
	@Override
	public TestApi create(Throwable throwable) {
		  log.error("异常原因:{}", throwable.getMessage(), throwable);
		  String errorDetail = throwable.getMessage();
			return (pageNum, pageSize) -> RestResp.failure(CommonErrorCode.SERVICE_OUTTIME,errorDetail);
	}
}
```

### 3.2 调用方引入依赖

被调用的服务要与调用方同级，这样可以在pom中导入对应的服务。

```xml
<!-- 调用其它服务接口-->
<dependency>
    <groupId>com.boot</groupId>
    <artifactId>ms-core-api</artifactId>
    <version>2.0.0-SNAPSHOT</version>
</dependency>
```

然后直接用接口中的方法就行。