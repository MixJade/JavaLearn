# 多节点Mq不消费的问题

> 2024-10-18 11:00:50

* 前排提示：纯属少了一个配置的问题

## 一、问题场景

* 我在配置文件中，配置好了发送者、消费者的topic\group【以下为发送者的配置】

```yaml
    stream:
      rocketmq:
        binder:
          name-server: 10.2.3.32:9876;10.2.3.33:9876;10.2.3.34:9876
          vipChannelEnabled: true
          enabled: false
        bindings:
          # 生产者
          xxxxx-out-0: # 生产者命名格式：xxx-out-0
            producer:
              group: dev_xxxxx_group
              sync: false # 开启异步发送
      bindings:
        #生产者
        xxxxx-out-0: # 生产者命名格式：xxx-out-0
          destination: dev_xxxxx_topic # topic,与MQ网站的相同
```

* 我在Mq的网页上配置好了发送者的topic
* 但是我发送消息时，消费者没有接受到
* 而我去往Mq网页的【Messsage】处查看对应topic的消息消息时
* 发现出现异常【UNKOWN】

```text
org.apache.rocketmq.client.exception.MQClientException: CODE: 17 DESC: No topic route info in name server for the topic: %RETRY%dev_xxxxx_group See http://rocketmq.apache.org/docs/faq/ for further details.
```

## 二、问题解决

其实就是这种多节点的mq，其消费者的group需要上网站手动加，在加的过程中指定消费节点。

* 这也是为什么单节点的mq可以自动注册消费者的原因，因为不需要选择。
* 前往【Consumer】-【ADD/UPDATE】新增消费者的group即可