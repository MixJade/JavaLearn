# Git笔记

**打开 git bash**

**设置用户名与邮箱**
这个配置文件保存在"用户"目录下的gitconfig文件中

```bash
$ git config --global user.name "MixJade"
$ git config --global user.email 1114185977@qq.com
$ git config --global list
```

**查看配置信息**
可以直接打开文件，或者

```bash
$ git config --list
$ git --version
```

**查看所有文件**

```bash
$ ls
$ touch helloWorld.txt
$ git status
$ git add .
$ git commit -m "GIT的第一步"
$ ssh -T git@github.com
$ git remote add origin git@github.com:MixJade/MixJadeWarehouse.git
$ git rm -r --cached .
$ git remote
$ git push -u origin main
$ git pull --rebase origin main
```

**设置远程跟踪分支**

* 后续直接`git pull`就能拉取

```bash
$ git branch --set-upstream-to=origin/main main
$ git pull
```

**git六连**

```bash
$ git init
$ git branch -M main
$ git remote add origin git@github.com:MixJade/MyNotes.git
$ git add .
$ git commit -m "MixJade"
$ git push -u origin main
```