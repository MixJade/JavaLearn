# html学习笔记

## html学习笔记

> 多媒体的添加、表格写法，表单写法

```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>html学习笔记</title>
</head>
        <details>
            <summary style="color: #0a0;;font-size: large;text-align: center;">多媒体的添加</summary>
            <h2>html部分语法</h2>
            <center>hr加分割线,br换行</center>
            <hr>
            <br>
            <h2>多媒体的添加</h2>
            <img src="钟离.jpg" height="300" width="300">
            <video src="扬旗鸣鼓.mp4" controls height="300" width="500"></video>
            <audio src="饿狼传说.mp3" controls></audio>
            <br>
            <!-- 注意：loop的意思是循环播放，control的意思是添加控制条 -->
            大氿歌-循环播放<br><audio src="C:\Users\11141\Music\大氿歌-洛天依.m4a" loop="true" controls></audio>
        </details>
        <details>
            <summary style="color:#0a0;font-size: large;text-align: center;">表格尝试</summary>
            <!-- border是边框样式，cellspacing是边线间隔(间隔有默认值，不用写也行) -->
            <table width="50%" border="2" cellspacing="2">
                <tr height="50">
                    <!-- th表示加黑加粗居中 -->
                    <th>表头一</th>
                    <th>表头二</th>
                    <th>表头三</th>
                    <th>表头四</th>
                </tr>
                <tr>
                    <td>001</td>
                    <td>002</td>
                    <td>003</td>
                    <td>004</td>
                </tr>
                <tr>
                    <td>005</td>
                    <td>006</td>
                    <td>007</td>
                    <td>008</td>
                </tr>
            </table>
            <br>
            <table width="50%" border="1">
                <tr height="50">
                    <!-- colspan="2"表示单元格可以横跨两列
    rowspan="2"表示单元格可以横跨两行 -->
                    <th colspan="2">表头五</th>
                    <th>表头七</th>
                    <th>表头八</th>
                </tr>
                <!-- align定义内容对齐样式 -->
                <tr align="center">
                    <td>001</td>
                    <td>002</td>
                    <td>003</td>
                    <td rowspan="2">004</td>
                </tr>
                <tr>
                    <td>005</td>
                    <td>006</td>
                    <td>007</td>
                </tr>
            </table>
        </details>
        <details>
            <summary style="color:#0a0;font-size: large;text-align: center;">表单标签</summary>
            <!-- form定义表单;action表单提交的url,其中#表示提交到当前网页;
            method表示发送表单方式,
                默认get表示:发送到表单的action url之后,大小限制到4kb;
                post表示:发送到http协议的请求方法体中，无大小限制。 -->
            <form action="#" method="post">
                <!-- input表示表单项，通过type控制形式 
                    label只是给字段的标签，意思是跟for后面相同id的表单项联系起来-->
                <p style="color:#009900;">请打开网页调试-网络-document文件-负载</p>
                <!-- text文本框 -->
                <label for="userName">用户名:</label>
                <input type="text" name="userName" id="userName">
                <!-- submit提交按钮，不设置value则默认“提交” -->
                <input type="submit" value="开始注册">
                <br>
                <!-- password密码框 -->
                <label for="password">密码:</label>
                <input type="password" name="password" id="password">
                <br>
                性别：
                <!-- radio单选按钮，设置两个一样的name就实现非此即彼了，记得设value，就是传递的值 -->
                <input type="radio" name="gender" value="1" id="female"><label for="female">男</label>
                <input type="radio" name="gender" value="0" id="male"><label for="male">女</label>
                <br>
                爱好：
                <!-- checkbox多选框，一样的name也可以多选，记得设置value -->
                <input type="checkbox" name="hobby" value="1">爬山
                <input type="checkbox" name="hobby" value="2">看书
                <input type="checkbox" name="hobby" value="3">看片
                <br>
                <!-- file是上传文件 -->
                上传头像:<input type="file">
                <br>
                <!-- reset清空表单内容 -->
                <input type="reset">
                <br>
                <!-- button按钮 -->
                <input type="button" value="单纯的小按钮">
                <br>
                <!-- select下拉列表，option选项，返回的是文本哦，也可以通过value来设置返回值 -->
                国籍：
                <select name="country">
                    <option>魏</option>
                    <option>蜀</option>
                    <option value="wu">吴</option>
                </select>
                <br>
                <!-- textarea文本域,cols列宽，rows行高 -->
                个人介绍：
                <textarea name="introduction" cols="30" rows="5">
                大家好，
                </textarea>
            </form>
            </details>
```



------

## JS学习笔记

> JS的弹窗、网页输出、控制台输出、五种原始数据类型

```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>JS学习笔记</title>
</head>

<body>
    <script>
        var a = 20
        var b = 90
        window.alert("window.alert:写入警告框\ndocument.write写入html\nconsole.log写入控制台\n")
        document.write("document.write写入html:&nbsp&nbsp")
        document.write(a + b)
        console.log("console.log写入控制台: " + a + b)
    // 变量命名一般使用var关键字，可以重复定义，且作用范围不拘泥于代码块
        /* 后来定义了let关键字来声明变量，只在代码块内生效
        还有const关键字来声明只读的常量，一旦声明就不能改变 */
        document.write("<br>JS中有五种原始数据类型:<br>number:数字<br>sring:字符串<br>boolean布尔<br>null空对象<br>undefined:声明的变量没有初始化")
        document.write("<br>使用typeof可以获取数据类型:&nbsp&nbsp"+typeof(a))
        var a=20
        var a2="20"
        document.write("<br>等于号会自动转化数据类型,&nbsp&nbsp"+(a==a2))
        document.write("<br>全等于号不会,&nbsp&nbsp"+(a===a2)+"<br>当然，字符串加数字还是那样：")
        document.write(a2+1)
        document.write("<br>可以用parseInt,毕竟老朋友了:"+parseInt(a2)+1)
        var a2=+"20"
        document.write("<br>不过如果在字符串前面加上一个加号,会转化成数字"+typeof(a2))
        document.write(a2+1)
        document.write("<br><br>一个比较好的地方是，可以将字符串或者其他类型数据直接当boolean类型使用：<br>")
        var str="啊咧"
        // var str=""
        if(str){
            document.write("判断条件为真,因为字符串不为空")
        }else{
            document.write("判断为假")
        }
    </script>

</body>
```



------

## JS学习笔记其二

> - 函数的定义、参数格式
> - 关于数组的操作

```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>JS学习笔记</title>
</head>

<body>
    <script>
        function twoAdd(a, b) {
            return a + b
        }
        document.write("两个数的和是" + twoAdd(2, 3))
        document.write("<br>其实并不限定参数格式，可以多，但只有前两个" + twoAdd(2, 3, 3))
        document.write("<br>也可以少，但会报错" + twoAdd(2))
        // 函数的另外一种定义方式
        var multiplication = function (a, b) {
            return a * b
        }
        document.write("<br>实际上一样的" + multiplication(8, 9))
    </script>
    <script>
        // 定义数组的方式其一
        var arr = [2, 3, 3]
        document.write("<br><br>数组定义<br>其一："+arr)
        // 定义数组方式其二
        var arr1=new Array(9,8,5,7)
        document.write("<br>其二："+arr1)
        document.write("<br>索引访问："+arr1[0])
        document.write("<br>数组长度："+arr1.length)
        // 数组添加方法
        arr1.push(23)
        document.write("<br>添加之后数组："+arr1)
        // 数组删除元素、索引与数量
        arr1.splice(0,2)
        document.write("<br>删除之后数组："+arr1)
    </script>
</body>

```



------

## JS学习笔记其三

> - 其实JS也有对象，可以直接通过对象调用里面的数据和方法
> - 关于定时器的设置



```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>JS学习笔记之三</title>
</head>

<body>
    <script>
        // 关于对象，注意成员后面的是逗号
        // 注意调用方法不要忘了后面的括号
        var cat = {
            name: "咪咪",
            weight: 3,
            voice: function () {
                document.write("喵喵喵")
            }
        }

        document.write("猫的名字叫：" + cat.name + "<br>它重达" + cat.weight + "KG<br>叫声是：")
        cat.voice()

        // window对象，值得注意的是确认对话框会返回bool值
        var verify = window.confirm("这是确认对话框")
        if(verify){
            document.write("<br>你点了确认")
        }else{
            document.write("<br>你点了取消")
        }

        // 定时器对象执行function，setTimeout指定时间后执行一次,setInterval指定的时间间隔循环
        // 为什么会清空前面的输出啊
        setTimeout(function(){
            document.write("<br>三秒后执行一次")
        },3000)
        setInterval(function(){
            document.write("<br>间隔两秒的循环")
        },2000)
    </script>
</body>

```



------

## JS学习笔记其四

> - 获取历史记录进行跳转网页(只能前一个后一个这样的跳)
> - 直接跳转

```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>JS学习笔记之四</title>
</head>

<body>
    <button class="MyButton" onclick="myFunction01()">返回上一个网页</button>
    <button class="MyButton" onclick="myFunction02()">返回后一个网页</button>
    <button class="MyButton" onclick="myFunction03()">前往百度</button>
    <script>
        function myFunction01() {
            history.back()
        }
        function myFunction02() {
            history.forward()
        }
        function myFunction03() {
            alert("即将前往百度")
            location.href="https://www.baidu.com"
        }

    </script>
</body>

```

---

## JS学习笔记其五

>  通过JS来校验表单的信息是否符合相应规则

```html
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>JS学习笔记之五</title>
</head>

<body>
    <div class="form-div">
        <div class="reg-content">
            <h1>欢迎注册</h1>
            <span>已有账号?</span><a href="#">登录</a>
        </div>
        <form id="presentForm" action="#" method="get">
            <table>
                <tr>
                    <td>用户名:</td>
                    <td class="inputs">
                        <input type="text" name="userName" id="userName">
                        <span id="usernameErr" class="usernameErr"
                            style="display: none;color:red;font-size: small;">用户名长度不对</span>
                    </td>
                </tr>
                <tr>
                    <td>密码:</td>
                    <td class="inputs">
                        <input type="password" name="password" id="password">
                        <span id="passwordErr" class="passwordErr"
                            style="display: none;color:red;font-size: small;">密码长度应为6-12位且包含数字与字母</span>
                    </td>
                </tr>
            </table>
            <div class="buttons">
                <input value="注册" type="submit">
            </div>
        </form>
    </div>
</body>

<script>
    // 用户名
    var userName01 = document.getElementById("userName")
    userName01.onblur = checkUsername
    function checkUsername() {
        let username02 = userName01.value.trim().length
        let flag01 = username02 >= 6 && username02 <= 12
        if (flag01) {
            document.getElementById("usernameErr").style.display = 'none'
        } else {
            document.getElementById("usernameErr").style.display = ''
        }
        return flag01;
    }
    // 密码
    var password01 = document.getElementById("password")
    password01.onblur = checkpassword
    function checkpassword() {
        let password02 = password01.value.trim()
        let flag02 = /^(?=.*[0-9])(?=.*[a-zA-Z])([0-9a-zA-Z]{6,12})/.test(password02)
        if (flag02) {
            document.getElementById("passwordErr").style.display = 'none'
        } else {
            document.getElementById("passwordErr").style.display = ''
        }
        return flag02;
    }
    // 提交表单，注意是表单的ID
    document.getElementById("presentForm").onsubmit =
        function () {
            if (checkUsername() && checkpassword()) {
                return true
            } else {
                alert("提交失败")
                return false
            }
        }
</script>
```



------

## 表单的默认选择

> - 通过JS来为表单设置默认值
> - 因为实际上是给jsp使用，故不具备普遍性

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>添加学生</title>
</head>

<body>
    <h3 style="text-align:center">添加学生</h3>
    <div style="padding-left:40%;">
        <form action="/addStudent" method="post">
            姓名:<input type="text" name="studentName" required="required" value="${student.studentName}"><br>
            性别:&emsp;
            <input type="radio" name="sex" value="1" id="female" required="required"><label for="female">男&emsp;</label>
            <input type="radio" name="sex" value="0" id="male" required="required"><label for="male">女&emsp;</label>
            <br>
            英语:<input type="number" min="1" max="100" name="englishGrade" required="required"
                value="${student.englishGrade}"><br>
            数学:<input type="number" min="1" max="100" name="mathGrade" required="required"
                value="${student.mathGrade}"><br>
            社团:
            <select name="societyId" id="societyId">
                <option value="1">散人</option>
                <option value="2">头文字D</option>
                <option value="3">轻音部</option>
                <option value="4">黄金之风</option>
                <option value="5">极东魔术昼寝结社</option>
            </select>
            <br>
            身高:<input type="number" min="1.00" max="2.00" step="0.01" name="height" required="required"
                value="${student.height}"><br>
            生日:<input type="date" name="birthday" required="required" value="${student.birthday}"><br>
            &emsp;钱:<input type="text" name="money" oninput="value=value.replace(/[^\d]/g,'')" required="required"
                value="${student.money}"><br>
            <input type="submit">
        </form>
    </div>
</body>
<script>
    window.onload = function () {
        var societyId = document.getElementById("societyId");
        var opts = societyId.getElementsByTagName("option");
        var sId = 3 - 1
        opts[sId].selected = true;
        var studentSex = document.getElementsByName("sex");
        var sex = 1
        if (sex == 1) {
            studentSex[0].checked = true;
        } else {
            studentSex[1].checked = true;
        }

    }

</script>

</html>
```

------