# Git从历史记录删除文件夹

在Git中如果已经误提交了不应该提交的文件或文件夹，可以通过以下几步进行删除：

你可以使用`git filter-branch`命令将某个文件从所有提交历史中删除。

步骤如下：

1. 打开Git命令行。

2. 导航到你的仓库。

3. 使用以下命令删除所有历史提交中的特定文件：
```sh
git filter-branch --force --prune-empty --index-filter 'git rm -rf --cached --ignore-unmatch forgePetWX/node_modules' --tag-name-filter cat -- --all
```
其中，将`forgePetWX/node_modules`替换为要从提交历史中删除的文件的路径。

4. 使用以下两个命令清除Git缓存：
```sh
git for-each-ref --format='delete %(refname)' refs/original | git update-ref --stdin
git reflog expire --expire=now --all
```

5. 最后，收集并删除未使用的数据：
```sh
git gc --prune=now
```

6. 接着你需要使用`git push origin --force --all`来将更改推送到远端仓库。如果你在GitHub上有任何打标签的操作，需要使用`git push origin --force --tags`。

注意：这种操作是不可逆的，所以在执行之前请确保你有一个备份，或者完全确定你想要删除这个文件。