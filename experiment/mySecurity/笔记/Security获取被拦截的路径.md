# SpringSecurity获取登录信息的两种方式

## 1 配置类打开debug模式

> 如果不用，记得把`debug = true`给去掉，但不要去掉前面的`EnableWebSecurity`

```java
/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
}
```

## 2 在YAML文件中打开debug日志

```yaml
logging:
  pattern:
    dateformat: MM-dd HH:mm
  level:
    org:
      springframework:
        security: DEBUG
```

## 3 查看被拦截的路径

> 后面莫名其妙的多了一个`?continue`，怪耶

```text
06-10 23:39 DEBUG 13508 --- [io-8080-exec-10] o.s.s.w.s.HttpSessionRequestCache        
    : Saved request http://localhost:8080/demo/no?continue to session
```