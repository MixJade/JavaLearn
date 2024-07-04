# 使用vbs创建快捷方式

> 2024年7月4日09:22:06

* 通过vbs在桌面建一个快捷方式，生成的快捷方式所指向的是与它同级目录的runMyGUI.vbs，所指定的icon是同级目录的myico.ico

```vbscript
Set WshShell = WScript.CreateObject("WScript.Shell")
strDesktopFolder = WshShell.SpecialFolders("Desktop")
strAppPath = WshShell.CurrentDirectory & "\runMyGUI.vbs"
strShortcutPath = strDesktopFolder & "\自定快捷.lnk"
strIconPath = WshShell.CurrentDirectory & "\myico.ico"

Set oShortcut = WshShell.CreateShortcut(strShortcutPath) 
oShortcut.TargetPath = strAppPath 
oShortcut.IconLocation = strIconPath 
oShortcut.Save
```

* 注意要以`ANSI`编码保存,不然生成的快捷方式名称会乱码。