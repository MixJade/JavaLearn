# MybatisPlus条件构造器

> 2024年3月30日00:28:30

## 一、baseMapper使用

事实上，在MP的IService的实现类中，可以直接使用**baseMapper**字段，

这个字段所对应的就是**我们自己写的**Mapper文件。

```text
例如：在PetServiceImpl实现类中，baseMapper其实就是PetMapper，我们可以通过它调用自己定义的Mapper方法
```

这字段是在实现类所继承的父类中完成了注入，并使用protect所修饰，

所以MP的Service实现层可以直接调用，不必在重复注入，

## 二、Lamda条件更新

是的，通过Service实现类可以调用正宗的LamdaWapper。

形式如下：

*(下面是更新操作，最后的update是Service中的方法，原来往里面传入条件构造器的那个)*

*(但我想你应该会举一反三，知道查询操作怎么弄)*

```java
// 其它类的Service调用，需要注入对应Service之后，通过字段进行调用。
otherService.lambdaUpdate()
    .eq(Other::getName, other.getName())
    .set(Other::getMoney, other.getMoney())
    .update();
// 在自己类中调用，只需要一个this即可
this.lambdaUpdate()
    .eq(Iam::myId, iam.myId())
    .set(Iam::myName, iam.myName())
    .update();
```

## 三、条件删除

这个删除的条件构造器用的是更新操作的，毕竟删除也算一种更新嘛

```java
groupRelService.lambdaUpdate()
    .eq(AuthUgroupRel::getUsrNo, userID)
    .remove();
```

