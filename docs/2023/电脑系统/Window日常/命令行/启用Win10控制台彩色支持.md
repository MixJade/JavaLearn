# 启用Win10控制台彩色支持

> 2025-03-26 20:05:39

* 问题：在win10环境，通过控制台启动springboot程序后，彩色日志显示乱码

从 Windows 10 开始，系统已经支持 ANSI 转义序列，但默认是关闭的。你可以通过以下方式启用：

以管理员身份运行 PowerShell，然后执行以下命令

```shell
Set-ItemProperty HKCU:\Console VirtualTerminalLevel -Type DWORD 1
```

执行完这个命令之后，重新启动 CMD 窗口，再运行 Spring Boot 应用程序，就可以正常显示彩色日志了。

* 附录：对于springboot可以直接把idea启动它的命令复制下来弄成cmd，前提是它没有被idea缩短过类路径，以及已经进行过一次编译