# 通过vbs运行class文件

> 2024年7月3日21:19:23

* 要求：通过vbs执行命令`java Calculator`，且过程中不能出现命令窗口
* 与vbs同级的已经有一个`Calculator.class`文件了

可以使用WScript.Shell对象的Run方法。这个方法的第二个参数是窗口样式，设为0来阻止窗口的出现。

以下是一个在VBS脚本中使用这个方法的例子：

```vbs
Set WshShell = CreateObject("WScript.Shell")
WshShell.Run "java Calculator", 0
```

用这种方法，你就可以在VBS脚本中运行java命令，而不会显示命令行窗口。