# 外网同步Git记录到内网

> 2026-05-27 15:26:50

## 外网执行

* 查看提交历史，找到你开始修改之前的那个 commit 的 hash 值（假设是`df3c03e5`）

```bash
git log --oneline
```

* 生成从`df3c03e5`之后到当前最新的所有 commit 的补丁。*（注：不包括`df3c03e5`本身）*
* 这会在当前目录下生成一个 patches 文件夹，里面包含 0001-xxx.patch、0002-xxx.patch 等文件。把这些 .patch 文件拷到内网

```bash
git format-patch df3c03e5 -o ./patches
```

## 内网执行
* 正式应用补丁（会在当前分支新增对应的 commit，保留作者和时间）
```bash
git am ./patches/*.patch
```
* 注：如果 git am 中途因为冲突失败，需要手动解决冲突后，执行`git add .`然后`git am --continue`。