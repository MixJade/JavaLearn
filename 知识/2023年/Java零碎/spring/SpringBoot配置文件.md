# SpringBoot配置文件

## 一、配置文件介绍 

* **application.yml**:基础配置文件，里面存放的都是不会更改的配置
* 且可以在其中指定默认激活的环境配置文件(只有激活了，另外那个配置文件才会生效)

```yaml
spring:
  profiles:
    active: dev # 默认使用dev配置文件
```

* **application-dev.yml**:Dev环境的配置文件，里面的参数都是需要更改的，一般是部署到dev环境才用，或者作为local文件的备份
* **application-local.yml**:本地开发所用，一般都是通过`gitignore`忽略掉，防止提交到仓库中。里面都是本地化的配置，或者是一些不方便公开的参数。

## 二、local文件的使用

> -Dspring.profiles.active=local

使用local的配置文件一般是通过idea的运行配置来设置的。

* 打开“运行配置”，选择“编辑配置”
* 点击“修改选项”，选择“添加VM选项"
* 输入VM选项`-Dspring.profiles.active=local`
* 这个表示启用**application-local.yml**文件，且这个指定优先级最高，甚至会覆盖**application.yml**所指定的。
* 一般都是这样启用local文件，因为这样最环保(不会污染git提交列表，同时别人使用dev时，无需做任何更改就能直接启动)

## 三、小补充

如果在Springboot中，application.yml与application-dev.yml都有相同的配置，但配置的值不同，那么以当前激活的环境配置文件为准，也就是说如果当前激活的环境是dev，那么就以application-dev.yml中的配置为准。