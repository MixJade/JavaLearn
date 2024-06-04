# U盘自定义图标

> 2024年6月4日16:42:15

* 先在U盘的根目录下放一个**ICO**图片，比如`yu-logo.ico`
* 然后再在旁边新建一个`autorun.inf`文件，文件内容如下：

```ini
[autorun]
ICON=yu-logo.ico,0
```