# Git重置到根提交

> 2026-01-04 20:46:24

## 命令示例

```bash
git reset --soft $(git rev-list --max-parents=0 HEAD)
```

如果你想将当前分支重置到仓库的**第一个提交**（根提交），可以使用以下方法：

## 原理讲解

是以下两个命令的混合

1. 查找根提交的哈希值：
   ```bash
   git rev-list --max-parents=0 HEAD
   ```
   输出示例：
   ```
   a1b2c3d4e5f6...
   ```
2. 重置到该提交：
   ```bash
   # soft 保留文件变更
   git reset --soft a1b2c3d4e5f6
   ```

