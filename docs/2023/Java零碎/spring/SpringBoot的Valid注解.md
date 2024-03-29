# SpringBoot的Valid注解

> 2024年3月15日22:05:57

## 一、介绍

`@Valid`注解主要用于数据校验。使用`@Valid` 注解可以使实体类中的约束生效。它需要配合`@NotNull`，`@Size`，`@Pattern`等约束注解一起使用。

如：

```java
@Data
class InputData {
    @NotNull(message = "The name of the user should not be null.")
    private String username;
}

public class UserController {
 
    public User createUser(@Valid InputData inputData) {
        //your business logic here
    }
}
```

在上述例子中，先在`InputData`中声明了一个`username`字段，并使用`@NotNull`注解表示此字段不能为空。

然后，在`UserController`的`createUser`方法中，使用`@Valid`注解表示对输入的`inputData`进行有效性校验。如果`inputData`的`username`字段为`null`，那么将会**抛出异常**，而不会继续执行后续的业务逻辑。

## 二、Spring中使用

### 2.1 引入依赖

* pom.xml

```xml
<!-- 处理valid注解校验的库,springboot2.3以上需要手动引入-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### 2.2 异常切片

```java
/**
 * 处理参数校验的异常切片
 */
@RestControllerAdvice
public class ValidExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ValidExceptionAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid注解数值校验异常
    public ResponseEntity<String> doValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            String message = String.format("%s[%s]:%s"
                    , fieldError.getObjectName()
                    , fieldError.getField()
                    , fieldError.getDefaultMessage());
            log.error(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("属性校验出错", HttpStatus.BAD_REQUEST);
        }
    }
}
```

## 三、校验注解

当然也可以添加其他验证信息的要求：

|限制|	说明|
|:---|:---|
|@Null | 限制只能为null|
|@NotNull|	限制必须不为null|
|@AssertFalse | 限制必须为false|
|@AssertTrue |	限制必须为true|
|@DecimalMax(value)|	限制必须为一个不大于指定值的数字|
|@DecimalMin(value) | 限制必须为一个不小于指定值的数字|
|@Digits(integer,fraction)|限制必须为一个小数，且整数部分的位数不能超过integer，小数部分|
|@Future|限制必须是一个将来的日期|
|@Max(value)|限制必须为一个不大于指定值的数字|
|@Min(value)|限制必须为一个不小于指定值的数字|
|@Past|限制必须是一个过去的日期|
|@Pattern(value)|限制必须符合指定的正则表达式|
|@Size(min,max)|限制字符长度必须在min到max之间|
|@Past|验证注解的元素值（日期类型）比当前时间早|
|@NotEmpty|验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）|
|@NotBlank|验证注解的元素值不为空（不为null、去除首位空格后长度为0）|
|@Email | 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式|