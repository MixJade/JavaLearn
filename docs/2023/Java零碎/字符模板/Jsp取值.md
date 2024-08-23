# Jsp取值

> 2024-08-23 15:35:21

在struts2中，Action类与jsp页面通过值栈进行交互，我们可以在jsp页面通过OGNL表达式获取到Action的传值。

具体步骤如下：

1. 在Action类中定义要传递的属性和对应的getter、setter方法。例如：
```java
public class MyAction extends ActionSupport {
  private String myValue;

  public String execute() throws Exception {
    myValue = "Hello Struts2!";
    return SUCCESS;
  }

  public String getMyValue() {
    return myValue;
  }

  public void setMyValue(String myValue) {
    this.myValue = myValue;
  }
}
```
2. 在jsp页面中，使用OGNL表达式获取Action类中的属性值。例如：
```jsp
<s:property value="myValue"/>

或者

${myValue}
```
该语句最终会被解析成：`<s:property value="%{#action.myValue}"/>`，它会从值栈中查找Action类的`myValue`属性的值。如果找到，就将其输出；如果没有找到，则输出为空。

* 在script标签中，则通过`console.log("ddsdasa", '<s:property value="myValue"/>')`，注意在取值的两边加了单引号
* 这是因为Jsp本质是个类似FreeMarker的模板，所以放在Js中取值，需要在两侧加上引用作为字符串引用，不然就被js当成变量了