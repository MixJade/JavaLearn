# JavaFx样式学习

> 2024-08-21 15:55:05

## 一、HBox和VBox的区别

HBox和VBox是JavaFX中的两种布局面板。

1. HBox：这种布局面板会把子节点按照水平方向进行布局，也就是说子节点会在面板中从左到右一次排列。比如你在Hbox中添加了三个按钮，那么这三个按钮就会从左到右依次排列。

2. VBox：这种布局面板会把子节点按照垂直方向进行布局，也就是说子节点在面板中会从上到下依次排列。比如你在Vbox中添加了三个按钮，那么这三个按钮就会从上到下依次排列。

简而言之，HBox是水平布局面板，VBox是垂直布局面板。

## 二、一行的标签输入框

* 标签和输入框处于同一行：HBox的作用
* `alignment="CENTER"`让其中的元素居中
* `spacing="10"`让其中元素彼此有空隙

```xml
<HBox spacing="10" alignment="CENTER">
    <Label text="Your Label"/>
    <TextField fx:id="myTextField"/>
</HBox>
```