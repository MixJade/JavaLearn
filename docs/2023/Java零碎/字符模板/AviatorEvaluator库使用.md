# AviatorEvaluator库使用

> 2026-01-21 18:03:58

`com.googlecode.aviator.AviatorEvaluator` 正是一款专为**表达式动态求值**设计的 Java 表达式引擎库，核心作用就是解析和执行运行时动态传入的表达式，而不需要硬编码逻辑。

## 一、核心功能与用途
简单来说，Aviator 就像一个“Java 版的表达式计算器”，你可以把字符串形式的表达式（比如 `"a + b * 2"`、`"user.age > 18 && user.status == 'active'"`）传给它，它能解析并计算出结果，主要解决以下场景的问题：
1. **动态规则配置**：比如业务系统中的风控规则、价格计算规则、权限判断规则，无需修改代码，只需配置表达式字符串即可调整逻辑；
2. **简化条件判断**：替代大量的 if-else 硬编码，把可变的条件逻辑抽离成表达式；
3. **高性能求值**：Aviator 会将表达式编译成字节码执行，比传统的反射/解释执行效率更高，适合高频次表达式计算。

## 二、基础使用示例
先给你一个最简单的示例，直观感受它的用法：

### 1. Maven依赖
首先需要在项目中引入依赖（以 Maven 为例）：
```xml
<dependency>
    <groupId>com.googlecode.aviator</groupId>
    <artifactId>aviator</artifactId>
    <!-- 推荐使用稳定版，比如5.3.0 -->
    <version>5.3.0</version>
</dependency>
```

### 2. 核心代码示例
```java
import com.googlecode.aviator.AviatorEvaluator;
import java.util.HashMap;
import java.util.Map;

public class AviatorDemo {
    public static void main(String[] args) {
        // 1. 简单表达式求值（无上下文）
        Long result1 = (Long) AviatorEvaluator.execute("1 + 2 * 3 - 4");
        System.out.println("1 + 2 * 3 - 4 = " + result1); // 输出：3

        // 2. 带变量的表达式求值（有上下文）
        Map<String, Object> context = new HashMap<>();
        context.put("a", 10);
        context.put("b", 5);
        // 执行表达式：a * (b + 3)
        Long result2 = (Long) AviatorEvaluator.execute("a * (b + 3)", context);
        System.out.println("a * (b + 3) = " + result2); // 输出：80

        // 3. 布尔表达式（业务规则判断）
        context.put("userAge", 20);
        context.put("isVip", true);
        Boolean isQualified = (Boolean) AviatorEvaluator.execute("userAge >= 18 && isVip", context);
        System.out.println("是否符合条件：" + isQualified); // 输出：true
    }
}
```

## 三、关键特性补充
1. **支持的表达式语法**：
   - 基本运算：加减乘除、取模、幂运算等；
   - 逻辑运算：&&、||、!、==、!=、>、< 等；
   - 字符串操作：拼接（+）、长度（str.length()）等；
   - 集合操作：list.get(0)、map.get("key") 等；
   - 自定义函数：可以注册自己的函数扩展表达式能力。
2. **类型安全**：Aviator 会严格检查类型，比如数字和字符串不能直接相加，避免隐式类型转换的坑；
3. **轻量级**：无其他重型依赖，体积小，集成成本低。

## 总结
1. `AviatorEvaluator` 是 Java 表达式引擎，核心能力是**解析并执行字符串形式的动态表达式**；
2. 主要用于业务规则动态配置、简化条件判断等场景，替代硬编码的逻辑；
3. 特点是高性能、轻量级、语法贴近 Java，易于上手和扩展。

简单来说，如果你需要让系统的部分逻辑“可配置”（比如通过配置文件改规则，不用改代码重启），Aviator 就是非常合适的选择。