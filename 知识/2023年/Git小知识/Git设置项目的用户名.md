# Git设置项目的用户名

> 用于在其他电脑上推送自己的项目，这样不用担心账号冲突

在命令行中键入以下命令：

1. `git config user.name "新的用户名"`
2. `git config user.email "新的邮箱"`

修改完成后可以用以下命令查看当前项目的设定：

- `git config user.name`
- `git config user.email`

如果返回的是你刚设置的用户名和邮箱，那么说明设置成功。

一般是这样：

```bash
git config user.name "MixJade"
git config user.name
```

## 小参考：设置全局用户名

在命令行中键入以下命令：

1. `git config --global user.name "新的用户名"`
2. `git config --global user.email "新的邮箱"`

注意，替换 "新的用户名" 和 "新的邮箱" 为你实际要修改的用户名和账号。

这两行命令将会改变 Git 全局配置，适用于所有的 Git 项目。如果只想在当前项目里作出更改，请去掉 `--global` 参数。

修改完成后可以用以下命令查看效果：

- `git config --global user.name`
- `git config --global user.email`
