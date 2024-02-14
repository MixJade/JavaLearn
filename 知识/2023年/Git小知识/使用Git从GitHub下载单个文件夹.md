# 使用Git从GitHub下载单个文件夹

命令行：

```shell
git init
git branch -M main  # 切换分支
git remote add -f origin https://github.com/MixJade/MixJadeWareHouse.git # 添加远程仓库
git config core.sparsecheckout true # 启用sparse checkout模式
# 将需要下载的文件夹添加到sparse checkout列表
echo "experiment/fly_bird/" >> .git/info/sparse-checkout
git pull origin main
```

