## 学生管理系统2.0 (搭建中)

> * 通过ajax,axios(优化ajax),json,vue
> * 对原来的jsp形式进行升级
> * 同时学习ajax,axios,json,vue

## Ajax

> Asynchronous JavaScript And XML：异步的 JavaScript 和 XML

Ajax的作用:

1. **与服务器进行数据交换**：通过AJAX可以给服务器发送请求，服务器将数据直接响应回给浏览器
2. **异步交互**：可以在不重新加载整个页面的情况下，与服务器交换数据并更新部分网页

关于同步与异步:

1. **同步**：浏览器页面在发送请求给服务器，在服务器处理请求的过程中，浏览器页面不能做其他的操作。只能等到服务器响应结束后才能继续操作。
2. **异步**:浏览器页面发送请求给服务器，在服务器处理请求的过程中，浏览器页面还可以做其他的操作。

Ajax尝试:
前端：

```html

<body>
<div id="myIndexDiv">
    <p>
        <button class="button_press" onclick="myFunction()">AXIOS回应</button>
    </p>
    <p id="wqf">是大V从</p>
</div>
</body>
<script>
    function myFunction() {
        var username="王庆丰"
        //2.1. 创建核心对象
        var x_http;
        if (window.XMLHttpRequest) {
            x_http = new XMLHttpRequest();
        } else {
            //适用于IE6, IE5
            x_http = new ActiveXObject("Microsoft.XMLHTTP");
        }

        //2.2. 发送请求
        x_http.open("GET", "http://localhost:8080/ajaxAttempt/ajaxRespServlet?"+username,true);
        x_http.send();
        //2.3. 获取响应
        x_http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                //弹出后端响应数据
                alert(this.responseText);
                //判断
                if (this.responseText == "NO") {
                    //用户名不存在，显示提示信息
                    document.getElementById("wqf").style.display = 'inline';
                    document.getElementById("wqf").innerHTML = "用户不存在";
                } else {
                    //用户名存在 ，清楚提示信息
                    document.getElementById("wqf").style.display = 'none';
                }
            }
        }
    }







</script>

```

后端:

```

@WebServlet("/ajaxRespServlet")
public class AjaxRespServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("cfvb");
        String username = req.getParameter("username");
        System.out.println(username);
        resp.getWriter().write("NO");
    }
}

```

## Axios

> Axios 对原生的AJAX进行封装，简化书写。

`axios()` 是用来发送异步请求的，小括号中使用 js 对象传递请求相关的参数：

* `method` 属性：用来设置请求方式的。取值为 `get` 或者 `post`。
* `url` 属性：用来书写请求的资源路径。get请求要将请求参数拼接到路径的后面
* `data` 属性：作为`post`请求体被发送的数据。

* `then()` 需要传递一个匿名函数。我们将 `then()` 中传递的匿名函数称为回调函数，意思是该匿名函数在发送请求时不会被调用，而是在成功响应后调用的函数。
* 而该回调函数中的 `resp` 参数是对响应的数据进行封装的对象，通过 `resp.data` 可以获取到响应的数据。

引入js文件
```<script src="js/axios-0.18.0.js"></script>```

发送get或post请求

```html

<script>
    //1. get
    axios({
        method:"get",
        url:"http://localhost:8080/ajaxAttempt/testResp?username=zhangsan"
    }).then(function (resp) {
        alert(resp.data);
    })


    //2. post
    axios({
        method:"post",
        url:"http://localhost:8080/ajaxAttempt/testResp",
        data:"username=zhangsan"
    }).then(function (resp) {
        alert(resp.data);
    })

</script>
```

```
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("This is a response");
    }
```

## JSON

> JavaScript Object Notation。即JavaScript 对象表示法

```xml

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>2.0.9</version>
</dependency>
```

```java
package myServlet;

import com.alibaba.fastjson.JSON;
import sqlDemo.StudentsDemo;
import pojo.StudentsMessage;

import java.util.List;

public class StudentTs {
    public static void main(String[] args) {
        StudentsDemo studentsDemo = new StudentsDemo();
        List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
        // 对象换成JSON
        String jsonString = JSON.toJSONString(studentsMessages.get(1));
        System.out.println(jsonString);
        // JSON换成对象
        StudentsMessage studentsMessage = JSON.parseObject(jsonString, StudentsMessage.class);
        System.out.println(studentsMessage);
    }
}

```

查询所有学生就是通过json来向前端传递数据

```
StudentsDemo studentsDemo = new StudentsDemo();
List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
// 写入JSON
String jsonString = JSON.toJSONString(studentsMessages);
// 响应数据
resp.setContentType("text/json;charset=utf-8");
resp.getWriter().write(jsonString);
```

而后端如此解析:

```
let studentMessages = resp.data;
for (let i = 0; i < studentMessages.length; i++) {
    let student = studentMessages[i];
    alter(student.id+
        student.studentName+
        student.sex+
        student.englishGrade+
        student.mathGrade+
        student.societyName+
        student.height+
        student.birthday+
        student.money
    )
```

## VUE

> * Vue 是一套前端框架，免除原生JavaScript中的DOM操作，简化书写
> * 基于MVVM(Model-View-ViewModel)思想，实现数据的双向绑定，将编程的关注点放在数据上。


引入js文件

```<script src="js/vue.js"></script>```

### VUE指令
**指令** ：HTML 标签上带有 v- 前缀的特殊属性

常用的指令有：

| **指令**     | **作用**                          |
|:-----------|:--------------------------------|
| v-bind     | 为HTML标签绑定属性值，如设置  href , css样式等 |
| v-model    | 在表单元素上创建双向数据绑定                  |
| v-on       | 为HTML标签绑定事件                     |
| v-if       | 条件性的渲染某元素，判定为true时渲染,否则不渲染      |
| v-else     |                                 |
| v-else-if  |                                 |
| v-show     | 根据条件展示某元素，区别在于切换的是display属性的值   |
| v-for      | 列表渲染，遍历容器的元素或者对象的属性             |

html使用：

```html

<body>
<div id="app">
    <!-- 模型绑定 -->
    <input v-model="username">
    <!--插值表达式-->
    {{username}}
</div>
<br>
<div id="app02">
    <!-- v-bind绑定 -->
    <a v-bind:href="url">点击跳转</a>
    <input v-model="url">
    {{url}}
</div>
<br>
<div id="app03">
    <!-- v-on:click单击事件 -->
    <button v-on:click="my_show">点击</button>
    <!-- v-if:判定 -->
    <p v-if="status==1">这是1</p>
    <p v-else-if="status==2">这是2</p>
    <p v-else>芝士雪豹</p>
    输入一个数字:<input v-model="status">
</div>
<div id="app04">
    <!-- v-for是VUE的for循环 -->
    <p v-for="student in students">{{student}}</p>
    <!-- 注意：索引在第二个值 -->
    <p v-for="(student,i) in students">{{i}}---{{student}}</p>
    <!-- 不然如下,顺便展示加值 -->
    <p v-for="(i,student) in students">{{i+1}}---{{student+1}}</p>
</div>
</body>

<script src="js/vue.js"></script>
<script>
    //1. 创建Vue核心对象
    new Vue({
        el: "#app",
        data() {  // data() 是 ECMAScript 6 版本的新的写法
            return {
                username: "模型绑定与插值"
            }
        }
        //被简化的写法
        /*data: function () {
            return {
                username:""
            }
        }*/
    });

    new Vue({
        el: "#app02",
        data() {
            return {
                url: "http://www.baidu.com"
            }
        }
    })
    new Vue({
        el: "#app03",
        data() {
            return {
                status: 2
            }
        },
        methods: {
            my_show() {
                alert("Vue设置methods实现绑定方法")
            }

        }
    })
    new Vue({
        el: "#app04",
        data() {
            return {
                students: ["张三", "李四", "王五"]
            }
        }
    })

</script>
```

### VUE生命周期
> mounted ：挂载完成，VUE初始化成功，可以做一些ajax操作，mounted只会执行一次

[说明链接](https://www.jianshu.com/p/672e967e201c)