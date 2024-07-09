# 登录玩贪吃蛇华容道

> 完成时间：2022-6-25

* 来自暑假期间所搜寻的源码
* 第一次接触MVC架构与数据库连接
* 现在来看有许多的不好
* 但是关于通过cmd操作java的经验却一直得到保留
* 因为数据库的表改了，所以现在迁移过来，有很多地方不能运行
* 如果打包，记得把package改了

## 下面是一些脚本类的文件

* A编译器.bat

```bash
javac handle\*.java view\*.java model\*.java recognition\*.java
pause
```

* A登录界面普通启动器.bat

```bash
java -cp mysqlcon.jar; recognition.MainWindow
pause
```

* 登录界面JAR启动器.bat

```bash
@echo off
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("""%~nx0"" h",0)(window.close)&&exit
:begin
java -jar 登录玩贪吃蛇华容道.jar
```

* 登录界面打包.mf

```manifest
Manifest-Version: 1.0
Main-Class: recognition.MainWindow
Class-Path: mysqlcon.jar
Created-By: sanyu

```

* 登录界面打包器.bat

```bash
jar -cfm 登录玩贪吃蛇华容道.jar 登录界面打包.mf handle\*.class view\*.class model\*.class recognition\*.class
pause
```

* 删除CLASS文件.bat

```bash
del/f/s/q  handle\*.class view\*.class model\*.class recognition\*.class
pause
```

## 作者的话

* 关于这个小游戏，当时我是为了给自己在大学里面学的javaSE立一个丰碑
* 现在来看，在大学课堂上可以说是什么都没有学到，一些从书上拷贝而来的源码，现在看完全是错漏百出
* 关于swing的操作倒是学了很多通过自学没有学到的东西，但那是因为这个技术已经被淘汰了
* 我曾经因为只有java给我提供这种窗口而学的java，因为很像游戏，现在来看我应该去学c#
* 可惜c#学校完全不教，万恶的应试教育
* 也许唯一有价值的，就是通过cmd来操作java吧
* 同时还有给项目打jar包，也是没有自学到的东西

## BUG修复

> 2023年4月24日22:36:21

* 优化了项目结构，移处了冗余的设计，
* 当时抄的时候非常懵懂，现在发现许多冗余，有的层层调用，有的代码重复，有的刚封装就拆封。
* 修复了贪吃蛇不能停止的BUG(这个很重要)

**贪吃蛇不能停止的bug修复**

* 抄的那一份是直接Thread.stop的，早就过时了，我当时的解决方法是：直接sleep(7000000)
* 现在看了一下，发现是定时器任务的原因，取消就好了

```
private void repaintEveryPeriod() {
    Timer t = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
        // ...乱七八糟的代码
                t.cancel(); // 取消定时器任务，很重要
            }
        }
    };
    t.schedule(task, 0, 200);
}
```