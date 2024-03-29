# 在LinuxMint上安装微信

> 本文来自网络，因为很重要，特地留档

## 一、切换软件源

> 本质上是使用优麒麟的软件源来安装

输入如下命令，创建一个优麒麟的软件源

```bash
sudo xed /etc/apt/sources.list.d/ukapps.list
```

然后在打开的文件中输入：

```text
deb http://archive.ubuntukylin.com/ubuntukylin focal-partner main
```

然后使用如下命令导入软件源的 key。

```bash
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 56583E647FFA7DE7
```

## 二、安装微信

然后刷新 APT 缓存、安装微信：

```bash
sudo apt update
sudo apt install weixin
```

注意：在 LinuxMint 中，桌面微信的启动器在菜单之“附件”中。