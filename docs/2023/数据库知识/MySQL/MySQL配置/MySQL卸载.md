# MySQL卸载

> 2024年4月7日15:05:47

## 一、停止MySQL服务

【任务管理器】-【服务】-【MySQL】-【停止】

## 二、卸载相关软件

【控制面板】-【程序和功能】，找到一切和Mysql相关的软件，右键卸载掉(可能有多个，但都是MySQL开头)

## 三、删除MySQL数据文件

删除以下文件夹：

```
C:\ProgramData\MySQL
```

## 四、清空注册表

**windows+R运行“regedit”文件，打开注册表**

然后在编辑器中搜索“MySQL”，删除所有相关的键。

* 补充：只删下面这个就行了

```text
HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Services\Eventlog\Application\MySQL
```

