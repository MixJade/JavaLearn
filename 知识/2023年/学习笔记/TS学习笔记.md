# TS学习笔记

> 时间：2023年4月20日09:16:36

# 一、开篇

> 2023-4-20 09:44:55

## 1.1 下载TS

下载TS

```bash
npm install -g typescript
```

查看TS版本

```bash
tsc -V 
```

这时出错`在此系统上禁止运行脚本`

使用管理员身份打开`Windows PowerShell`，

1.  执行：**get-ExecutionPolicy**，显示**Restricted**，表示状态是禁止的;
2.  执行：**set-ExecutionPolicy RemoteSigned**;
3.  这时再执行**get-ExecutionPolicy**，就显示**RemoteSigned;**ss

---

## 1.2 第一个TS程序

```typescript
const hello = (str:string) => str+"World!";
console.log(hello("hello"))
```

在命令行输入：

```bash
PS D:\MyJS\myTS> cd ts01
PS D:\MyJS\myTS\ts01> tsc 第一次.ts
PS D:\MyJS\myTS\ts01> node 第一次.js
helloWorld!
```

编译结果：

```javascript
var hello = function (str) { return str + "World!"; };
console.log(hello("hello"));
```

* 如果在类型注解的函数调用时，输入不一样的参数，会报错，但它的js文件可以运行

---

## 1.3 TS接口

> 这里name报过错，因为变量名不能为 name

```ts
interface ManName {
    firstName:string
    secondName:string
}

const getName = (manName:ManName) => "姓:"+manName.firstName+"，名；"+manName.secondName
const name0q={
    firstName:"李",
    secondName:"四"
}
console.log(getName(name0q))
```

编译结果（接口没了）:

```js
"use strict";
const getName = (manName) => "姓:" + manName.firstName + "，名；" + manName.secondName;
const name0q = {
    firstName: "李",
    secondName: "四"
};
console.log(getName(name0q));
```

---

## 1.4 类

重现java荣光

```
readonly 只读属性 当然只在ts中有效
```

```typescript
class Person {
    readonly name: string
    age: number
    sex: string

    constructor(name: string, age: number, sex: boolean) {
        this.name = name;
        this.age = age;
        this.sex = sex ? "男" : "女";
    }

    say():void{
        console.log(`名字：${this.name}，年龄：${this.age}`)
    }
}

interface PersonMsg {
    name: string
    age: number
    sex: string
}

const setMsg = (msg: PersonMsg):string => `姓名:${msg.name},年龄:${msg.age},性别${msg.sex}`
const person = new Person("张三", 23, true);
console.log(setMsg(person))
person.say()
```

编译结果:

```js
"use strict";
class Person {
    constructor(name, age, sex) {
        this.name = name;
        this.age = age;
        this.sex = sex ? "男" : "女";
    }
    say() {
        console.log(`名字：${this.name}，年龄：${this.age}`);
    }
}
const setMsg = (msg) => `姓名:${msg.name},年龄:${msg.age},性别${msg.sex}`;
const person = new Person("张三", 23, true);
console.log(setMsg(person));
person.say();
```

## 1.5 泛型

- ```typescript
  function test<T>(arg: T): T{
  	return arg;
  }
  ```

- 这里的```<T>```就是泛型，T是我们给这个类型起的名字（不一定非叫T），设置泛型后即可在函数中使用T来表示该类型。所以泛型其实很好理解，就表示某个类型。

- 那么如何使用上边的函数呢？

  - 方式一（直接使用）：

    - ```typescript
      test(10)
      ```

    - 使用时可以直接传递参数使用，类型会由TS自动推断出来，但有时编译器无法自动推断时还需要使用下面的方式

  - 方式二（指定类型）：

    - ```typescript
      test<number>(10)
      ```

    - 也可以在函数后手动指定泛型

- 可以同时指定多个泛型，泛型间使用逗号隔开：

  - ```typescript
    function test<T, K>(a: T, b: K): K{
        return b;
    }
    
    test<number, string>(10, "hello");
    ```

  - 使用泛型时，完全可以将泛型当成是一个普通的类去使用

- 类中同样可以使用泛型：

  - ```typescript
    class MyClass<T>{
        prop: T;
    
        constructor(prop: T){
            this.prop = prop;
        }
    }
    ```

- 除此之外，也可以对泛型的范围进行约束

  - ```typescript
    interface MyInter{
        length: number;
    }
    
    function test<T extends MyInter>(arg: T): number{
        return arg.length;
    }
    ```

  - 使用T extends MyInter表示泛型T必须是MyInter的子类，不一定非要使用接口类和抽象类同样适用。



# 二、编译配置

下载vsCode:https://vscode.cdn.azure.cn/stable/379476f0e13988d90fab105c5c19e7abc8b1dea8/VSCode-win32-x64-1.59.0.zip

vsCode开启自动编译ts

1). 生成配置文件tsconfig.json

```bash
 tsc --init
```
2). 修改tsconfig.json配置

```json
"outDir": "./js", // 编译输出目录
"strict": true,   // 开启严格模式
"removeComments": true, // 编译去除注释
"noEmitOnError": true, // 编译出错时不生成编译文件
```
3). 启动监视任务（限vscode): 

```
终端 -> 运行任务 -> 监视tsconfig.json
```

4). 批量编译

直接在有tsconfig.json的目录下，输入`tsc`即可全部编译

# 三、 webpack打包

首先初始化

```bash
npm init -y
tsc --init
```

打包下载依赖,共安装6个包

|   名称   |   简介   |
| :---- | :---- |
| webpack | 构建工具webpack |
| webpack-cli | webpack的命令行工具 |
| webpack-dev-server | webpack的开发服务器 |
| typescript | ts编译器 |
| ts-loader | ts加载器，用于在webpack中编译ts文件 |
| html-webpack-plugin | webpack的html插件，用于创建html文件 |

```bash
npm i -D webpack webpack-cli webpack-dev-server typescript ts-loader html-webpack-plugin
```

修改环境配置：

打开package.json，修改scripts

```json
{
  "name": "ts_pack",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "build": "webpack",
    "start": "webpack serve --open --mode production"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "devDependencies": {
    "ts-loader": "^9.4.2",
    "typescript": "^5.0.4",
    "webpack": "^5.80.0",
    "webpack-cli": "^5.0.1",
    "webpack-dev-server": "^4.13.3",
    "html-webpack-plugin": "^5.5.1"
  }
}
```

新建文件`webpack.config.js`

```js
const path = require("path"); // 拼接路径所需
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = { // webpack中的所有的配置信息都应该写在module.exports中
    optimization: {
        minimize: true, // 代码压缩，可选
    },
    mode: "production",// 指定生产环境，它编译的文件没有注释
    entry: "./src/index.ts", // 指定入口文件
    devServer: {
        static: {
            directory: path.join(__dirname, './dist'), // 服务器所用的目录
        },
        compress: true,
        port: 8081, // 服务器端口
    },

    output: { // 指定打包后的目录
        path: path.resolve(__dirname, "dist"), // 指定打包文件所在目录
        filename: "bundle.js",  // 打包后文件的文件名
        clean: true, // 每次编译都清除上次的文件
    },

    resolve: {
        extensions: [".ts", ".js"] // 用来设置引用模块，这两个文件都能被作为模块被其它引用
    },

    module: { // 指定webpack打包时要使用模块
        rules: [ // 指定要加载的规则
            {
                test: /\.ts$/, // test指定的是规则生效的文件
                use: {
                    loader: "ts-loader" // 必须指定ts加载器
                },
                exclude: /node_modules/ // 要排除的文件
            }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({ // 可以在这里配置生成的html
            template: "./template/ts-1.html" // 模板文件
        }),
    ]

}
```

新建文件夹`src`，在下面新建文件`index.ts`

```ts
console.log("Bih")
const fun1 = (x:string):string => x+"World"
console.log(fun1("a")) // 这是注释
console.log(fun1("sds"))
```

新建文件夹`template`，下面新建文件`ts-1.html`

```html
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <title>测试网页模板</title>
</head>
<body>
<div>HELLO</div>
</body>
</html>
```

运行

```bash
npm run build
```

