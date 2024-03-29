# Feign对应服务未启动报错

> 虽然说公司的Feign是通过被调用方暴露接口，然后在调用方通过sdk方式来引入，但对应服务还是得启动。

报错：

```text
异常原因:com.netflix.client.ClientException: Load balancer does not have available server for client: "调用的项目服务"
java.lang.RuntimeException: com.netflix.client.ClientException: Load balancer does not have available server for client: "调用的项目服务"
```

其实是因为对应服务未注册到nacos，即：对应服务未启动。