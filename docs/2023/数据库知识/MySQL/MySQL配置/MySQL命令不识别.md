# MySQL命令不识别

> 2024年4月7日15:41:02

在命令行启动mySQL时，出现如下报错：

```bash
'mysql' 不是内部或外部命令，也不是可运行的程序
或批处理文件。
```

* 访问如下路径，找到`mysql.exe`

```text
C:\Program Files\MySQL\MySQL Server 8.3\bin
```

* 上面的路径可能版本号不同，也可能在`Program Files (x86)`文件夹中

* 然后把`bin`目录的路径放入环境变量(`Path`)就好。

