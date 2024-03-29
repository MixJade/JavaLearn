# 关于通过改Host上GitHub

> 所谓的墙，即是DNS污染。修改HOST文件可以解决，但只能解决一时

## 1. 工具与文件位置

HOST文件位置

```text
C:\Windows\System32\drivers\etc
```

查询IP的工具

```text
https://ip.tool.chinaz.com/
https://site.ip138.com
```

## 2.所需查询的域名

最后在HOST文件末尾加上：

这些IP地址需要自行去IP查询网站上查询。

另外的，`raw.githubusercontent.com`是用于显示仓库中的图片文件的服务器，不在意的可以不用开。

```text
# github
20.205.243.166  github.com
185.199.108.153  assets-cdn.github.com
157.240.7.8  github.global.ssl.fastly.net
185.199.110.133  raw.githubusercontent.com
```

## 3.刷新DNS缓存

> 感觉这个没有意义，但聊胜于无

在CMD中输入：

```bash
ipconfig /flushdns
```

参考网站：[修改host文件来访问GitHub_github改host_CodePs的博客-CSDN博客](https://blog.csdn.net/weixin_55727019/article/details/123037510)