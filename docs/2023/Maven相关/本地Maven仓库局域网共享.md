# 本地Maven仓库局域网共享

> 2026-04-13 15:16:08

把本机的 Maven 仓库当成局域网镜像，给同网段其他电脑临时使用

---

## 直接开 HTTP 共享
### 1. 你本机操作
1. 找到你的 Maven 本地仓库目录
   默认路径：
   - Windows：`C:\Users\你的用户名\.m2\repository`
   - Linux/macOS：`~/.m2/repository`

2. 在这个 `repository` 目录下，**启动一个简易 HTTP 服务**
   任选一种你电脑上有的命令：

   - Python 3（最常用）
     ```bash
     python -m http.server 8080
     ```

   执行后，你的 Maven 仓库就通过 **http://本机IP:8080** 暴露出去了。

### 2. 其他电脑配置
修改其他电脑的 `settings.xml`，添加一个镜像：

```xml
<mirror>
  <id>local-repo-mirror</id>
  <mirrorOf>central</mirrorOf>
  <url>http://你的本机IP:8080</url>
</mirror>
```

例如：
```xml
<url>http://192.168.1.105:8080</url>
```

然后正常执行 `mvn` 命令，就会优先从你这台电脑拉包。

---

## 注意事项
1. **必须在同一局域网**，能互相 ping 通
2. 防火墙记得放行 8080 端口
3. 这种方式**只提供你本地已经下载过的包**
   本地没有的 jar，其他电脑也下不到
