# reflog操作

> 2024年5月11日12:09:39

如果你刚好使用 `git reset`并且误删了一些你需要的提交，你可以使用如下命令：

```bash
git reflog
```

`git reflog` 命令会显示所有的提交，包括你错误地删除的提交。找到你需要的提交哈希码，然后使用 `git cherry-pick` 或 `git reset --hard` 命令来恢复提交。

如果使用 `git cherry-pick`，你可以在当前分支上应用之前误删的提交。其命令如下：

```bash
git cherry-pick <commit-hash>
```

而如果你想恢复整个分支的状态，你可以使用 `git reset --hard` 命令：

```bash
git reset --hard <commit-hash>
```

注意，使用 `git reset --hard` 会丢弃自从 `<commit-hash>` 以来的所有更改。在使用这个命令之前，确保你不再需要这些更改。