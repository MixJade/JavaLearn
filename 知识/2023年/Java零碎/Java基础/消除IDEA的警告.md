# 消除IDEA的警告

> IDEA总会提示一些无伤大雅的警告，这种警告没有实际影响，但累计多了会让人忽略真正值得注意的警告，所以要使用SuppressWarnings注解来消除

## 一、SuppressWarnings介绍

Java的@SuppressWarnings注解被用于指示编译器忽略特定的警告。它在类、方法或者具体的一行代码上使用。

使用方法示例：

```java
interface English {
    @SuppressWarnings("unused")
    void speakEnglish();
}
```
这段代码中，这个`English`接口是为了约束实现类必须有speakEnglish方法，但它自己并不会被多态调用(单纯的约束接口)，所以会被警告说未使用的方法。通过在方法上添加@SuppressWarnings("unused")，编译器会忽略这个警告。

常见的@SuppressWarnings的值如下：

|  值  |  描述  |
| ---- | ---- |
| "deprecation" | 使用了不赞成使用的类或方法时的警告 |
| "unchecked" | 执行了未检查的类型转换时的警告 |
| "unused" | 未使用方法的警告(约束接口，或反射执行的方法) |
| "rawtypes" | 使用了原始类型时的警告 |
| "serial" | 可序列化类上缺少serialVersionUID时的警告 |
| "NonAsciiCharacters" | 使用非ASCII字符的警告 |
| "SpellCheckingInspection" | 拼写检查警告 |
| "all" | 关于以上所有情况的警告 |

注意的是， 上述值可能因不同编译器环境而有所不同，应根据实际的编译环境进行选择。



## 二、消除代码中使用中文的警告

可以在代码中添加 `@SuppressWarnings("NonAsciiCharacters")` 这个注解来局部禁止这个警告。

```java
@SuppressWarnings("NonAsciiCharacters") // 这样就不会提示："非ASCII字符"了
public class 休眠线程唤醒 {
}
```