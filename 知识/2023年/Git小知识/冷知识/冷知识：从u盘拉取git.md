# 冷知识：从u盘拉取git

> 不能联网的权宜之计

## 1、在u盘初始化远程仓库

新建一个文件夹，在里面初始化仓库

```
git init --bare
```

---

## 2、推送到u盘仓库

前面是很正常的初始化本地仓库、添加、提交。

最后添加远程分支时，将原本的url换成U盘仓库的绝对路径即可。

---

## 3、从u盘仓库中拉取

本地新建一个文件夹，然后：

(注意：`D:\MixPet`为u盘仓库路径)

```
git init
git remote add usb D:\MixPet
git pull usb master
```

