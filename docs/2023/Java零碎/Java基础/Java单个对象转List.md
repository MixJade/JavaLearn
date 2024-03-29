# Java单个对象转List

> 2024年3月15日16:32:21

可以使用Java的Collections类的singletonList方法。这个方法接收一个对象作为参数，然后返回一个只包含这个对象的不可变列表。以下是一个例子：

```java
Dog dog = new Dog();
List<Dog> list = Collections.singletonList(dog);
```

这段代码首先创建了一个Dog对象，然后使用singletonList方法将这个对象转换为一个List。这个List的长度为1，包含的唯一元素就是刚刚创建的这个Dog对象。

需要注意的是，得到的这个List是不可变的，不能向其中添加或删除元素。如果你需要一个可变的List，你可以这样：

```java
Dog dog = new Dog();
List<Dog> list = new ArrayList<>();
list.add(dog);
```
