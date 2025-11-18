# 关于本项目nacos服务

> 2025-11-18 16:48:52

* 必须使用nacos 2.x 版本，不然会和本地依赖不兼容

* nacos启动脚本示例：

```bash
cd D:\nacos\nacos-server-2.5.2\nacos\bin
./startup.cmd -m standalone
```

* 启动以后，访问nacos地址：`http://localhost:8848/nacos/index.html#/configurationManagement`
* 在【命名空间】新建命名空间，其命名空间ID为：`2abb2c43-42f3-4234-93cb-d88c34a7a083`
* 在项目启动后，可于【服务管理】-【服务列表】中查看，列表不为空则说明注册成功
