# Java以UTF8编译文件

> 2024年7月12日11:02:16

* 需在`javac`命令后面加上 `-encoding UTF-8` 参数

```bash
@echo off
chcp 65001 >nul
javac -encoding UTF-8 GuiShortcut.java
java GuiShortcut
pause
```

