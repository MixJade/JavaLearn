# Java以UTF8编译文件

## 使用utf-8编译

> 2024年7月12日11:02:16

* 需在`javac`命令后面加上 `-encoding UTF-8` 参数

```bash
@echo off
chcp 65001 >nul
javac -encoding UTF-8 GuiShortcut.java
java GuiShortcut
pause
```

## 使用utf-8运行

> 2025-08-08 20:46:48

* 防止有解析文件之类的操作，因为编码不对导致乱码

```bash
java -Dfile.encoding=UTF-8 MyServer
```

