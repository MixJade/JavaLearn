# Git统计提交数量

> 2024年3月8日15:04:06

你可以使用git的`rev-list`命令来统计你的提交数量。以下是一个适用于默认情况（即统计你在main分支上的提交数）的示例：

```bash
git rev-list --count main
```

要统计你在特定分支上的提交数，只需将`master`替换为你想要查询的分支名即可。如下：

```bash
git rev-list --count <branch-name>
```

如果你想要统计所有分支的提交次数，你可以这样做：

```bash
git rev-list --count --all
```