# Redis的Spring配置

> 2024年6月19日10:31:25

## 一、普通配置

```yaml
spring:
  redis:
    database: 0         # Redis数据库索引（默认为0）
    host: 127.0.0.1     # Redis服务器地址，注意要开启redis服务，即redis-server.exe
    port: 6379          # Redis服务器端口,默认为6379
    password: xxxx      # Redis服务器连接密码（默认为空)
    timeout: 5000       # 访问超时时间(毫秒)
    jedis:
      pool:
        max-active: 10  # 连接池最大连接数,负值表示没有限制,默认 8
        max-wait: -1    # 连接池最大等待阻塞时间,负值表示没有限制,默认 -1
        max-idle: 8     # 连接池中最大空闲连接数,默认 8
        min-idle: 0     # 连接池中最小空闲数,默认 0
```

## 二、集群配置

* 注意：比普通的多了一个`cluster`，以及其下的`nodes`
* 同时在节点列表里面指定端口，不再单独指定。
* 如果别人发的redis地址是一长串的且带端口的，那么就是集群模式的配置。

```yaml
#yaml配置
spring: 
    redis: 
     database: 0        # Redis数据库索引（默认为0）  
     cluster:
       nodes: 127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003 #集群节点
     password: xxxx     # Redis服务器连接密码（默认为空)
     timeout: 5000      # 访问超时时间(毫秒)
     jedis:
      pool:
        max-active: 10  # 连接池最大连接数,负值表示没有限制,默认 8
        max-wait: -1    # 连接池最大等待阻塞时间,负值表示没有限制,默认 -1
        max-idle: 8     # 连接池中最大空闲连接数,默认 8
        min-idle: 0     # 连接池中最小空闲数,默认 0
```

