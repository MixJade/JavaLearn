# SpringBoot对象属性复制

> 2024年2月22日16:30:57

* 有这么一种需求，我基于一个类，建立了另一个类，但他们是两个不同的对象。
* 我需要将一个对象中的属性，一键赋予另一个对象，而不必繁琐的get、set。
* 在Springboot中,可以使用BeanUtils类中的copyProperties(target, source)方法实现。

## 一、对象字段展示

* 源对象：ObjA

```java
@Data
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
public class ObjA {
    private long aId;
    private String name;
    private int age;
}

```

* 目标对象：ObjB

```java
@Data
public class ObjB {
    private Long bId;
    // ObjA中的aId是long(复制时会自动赋值)
    private Long aId;
    private String name;
    // ObjA中的age是int(复制时不会获取)
    private String age;
    private String notes;
}
```

## 二、BeanUtils使用

```java
public class TestCopyObj {
    public static void main(String[] args) {
        ObjA objA = new ObjA(123, "wc原", 12);
        ObjB objB = new ObjB();

        // 注意：这个工具类是springframework的
        // 只有A中与B相同的字段才会赋予B
        BeanUtils.copyProperties(objA, objB);

        // 此时B已经获取了A的值,甚至包装类也能自动赋值
        // 但类型不同就不会赋,比如objB中String类型的age,就获取不到objA中int类型的age
        System.out.println(objB);
    }
}
```

* 输出结果
* (复制了ObjA中的`aId`、`name`属性，而类型不同、或名字不同的其它字段没有复制)

```text
ObjB(bId=null, aId=123, name=wc原, age=null, notes=null)
```

## 三、使用语法

> 以前也涉猎过，但当时只记录了语法，现在放在这里当一个参考。

```java
import org.springframework.beans.BeanUtils;

BeanUtils.copyProperties(源对象, 目标对象);
```

