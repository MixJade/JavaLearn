# Window文件夹预设变量

> 这样可以做一个通用的攻略

Windows的目录路径还可以用`%userprofile%`这样的方法来表示当前用户的用户文件夹。

例如，`%userprofile%\Documents`代表当前用户的Documents文件夹。

并且相应的，Windows上有许多的这种预设环境变量，例如`%windir%`代表Windows系统所在的文件夹，`%temp%`代表当前用户的临时文件夹，等等。

* 比如我想访问音乐文件夹：

```url
%userprofile%\Music
```

* 甚至这样也行

```url
C:\Users\%userprofile%\Music
```

