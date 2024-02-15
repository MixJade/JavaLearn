# Linux创建快捷方式

* 创建文件

```
cd /usr/share/applications

sudo touch IDEA.desktop

sudo gedit Text IDEA.desktop
```

* 写入内容

```
[Desktop Entry]
Type=Application
Name=IDEA
Comment=Run IDEA
Icon=/home/mixjade/mixDown/idea-IU-222.3739.54/bin/idea.png
Exec=/home/mixjade/mixDown/idea-IU-222.3739.54/bin/idea.sh
Terminal=false
Path=
StartupNotify=false
```
