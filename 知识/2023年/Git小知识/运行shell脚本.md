# 运行shell脚本

可以在window上运行shell脚本

```text
打开文件资源管理器，在地址栏输入：

C:\Program Files\Git\bin\sh.exe

然后在弹窗中输入【必须手打】

sh up.sh
```

具体的shell脚本【请慎重，`push -f`表示会强制覆盖远程分支】

```shell
git init
git add .
git commit -m "MixJade"
git branch -M main
git remote add origin git@github.com:MixJade/MixJadeWareHouse.git
git push -f origin main
```

