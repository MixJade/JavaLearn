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

## 三、引用css文件

要在JavaFX中使用CSS样式表，您需要执行以下步骤：

1. 创建一个CSS文件，如`styles.css`。
2. 定义您的样式，如：
```
.root {
    -fx-font-size: 16px;
}

.button {
    -fx-background-color: lightblue;
}
```
3. 在你的FXML文件中引用你的CSS文件。其方法是在FXML文件顶部的`<Scene>`（或者任何其他元素）节点中添加`stylesheets`属性，并指定CSS文件路径，比如：

如果CSS文件放在src的同级目录resources中：
```xml
<Scene stylesheets="@../../../resources/styles.css">
```
如果CSS文件是放在当前FXML文件路径中：
```xml
<Scene stylesheets="@styles.css">
```