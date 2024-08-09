# Feign报错：参数过多

```text
FactoryBean threw exception on object creation; nested exception is java.lang.IllegalStateException: Method has too many Body parameters: public abstract com.boot.model.RestfulResponse com.boot.portal.auth.api.AuthGroupProjClient.queryHeadByPrjOrUsr(java.lang.String,int)
```

我的Feign初始化时报这个错 ，是为什么
**【答案:这个是get请求方法，但是没有给里面的参数加@**
**RequestParam注解，给那两个参数都加上就好了】**