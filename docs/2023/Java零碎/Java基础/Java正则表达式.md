# Java正则表达式

> 2024-11-11 16:10:20

## 一、匹配两个字符串之间的字符

* 正则表达式如何匹配从`const myData =` 到`;`之间的所有内容
* 匹配的结果不包括其用于定位的首尾字符
* 排除前面的表达式使用`(?<=定位字符)`，后面的使用`(?=定位字符)`

```java
public class test {
    public static void main(String[] args) {
        String string = "const myData = xxxxx;";
        Pattern p = Pattern.compile("(?<=const myData = )([\\s\\S]*?)(?=;)");
        Matcher m = p.matcher(string);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
```

* 输出

```text
xxxxx
```

