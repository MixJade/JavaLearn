# 自产自销的RocketMQ

> 2024年3月1日15:02:20

## 一、登录MQ网站新增topic

* 公司dev环境的MQ(已脱敏，仅做参考): **`http://12.3.45.67:8080/#/topic`**
* 登录的账号/密码(已脱敏，仅做参考)：admin/password
* 然后在**topic页面**新增一个**发送者的topic**，这里叫**`devMyTesMq_topic`**

## 二、配置文件新增配置

> 这里发送者、消费者都配置了

```yaml
spring:
  cloud:
    stream:
      function:
        definition: myTesCustomerMethod; # 与消费者方法同名
      rocketmq:
        binder:
          name-server: 12.3.45.67:10086
          vipChannelEnabled: true
          enabled: false
        bindings:
          # 生产者(测试发送，自产自销)
          myTesMq-out-0: # 生产者命名格式：xxx-out-0,与发送时指定的常量相同
            producer:
              group: devMyTesMq-group
              sync: false # 开启异步发送
          # 消费者(测试发送，自产自销)
          myTesCustomerMethod-in-0: # 消费者命名格式：消费方法-in-0
            consumer:
              messageModel: CLUSTERING
      bindings: # 上面配置了以后，这里要配置topic和消费者group
        #生产者
        myTesMq-out-0: # 生产者命名格式：xxx-out-0
          destination: devMyTesMq_topic # topic,与MQ网站的相同
        #消费者
        myTesCustomerMethod-in-0: # 消费者命名格式：xxx-in-0
          destination: devMyTesMq_topic # topic,与发送者相同
          content-type: application/json # 接收的数据格式为json
          group: devMyTesMq-group # 消费者group，不同group即为不同消费者
```

## 三、发送者代码

### 3.1 MQ常量类中，新增发送者

> 这里的发送者与配置文件中定义的必须一样

```java
public interface MqConstants {
    //全部mq开关
    boolean MQ_SWITCH = true;
    
    interface RocketMq {
        String MY_TES_MQ = "myTesMq-out-0"; //测试MQ的生产者,与配置文件的一致
    }
}
```

### 3.2 MQ接口增加下发方法

```java
public interface MpSendService {

    /**
     * 测试发送的自产自销MQ
     */
    boolean sendMyTesMessage(List<Dog> dogList);
}
```

### 3.3 MQ接口下发方法实现

```java
@Service
public class MpSendServiceImpl implements MpSendService{

	@Autowired
    private StreamBridge streamBridge;

	@Override
	public boolean sendMyTesMessage(List<Dog> dogList) {
		// 封装消息
		Message<List<Dog>> message = org.springframework.integration.support
				.MessageBuilder.withPayload(dogList).build();
		// 发送消息
		return streamBridge.send(MqConstants.RocketMq.MY_TES_MQ, message);
	}
}
```

## 四、消费者代码

### 4.1 接收消息代码

> * 这里的方法名要与配置文件中定义的一样
>
> * 且返回值必须是`Consumer<Message<String>>`

```java
@Slf4j
@Component
public class MyTesConsumerMqService {

    @Autowired
    DogService dogService;
    
    @Bean
    @Transactional(rollbackFor = Exception.class)
    public Consumer<Message<String>> myTesCustomerMethod() {
        return message -> {
            try {
                String myMqMessage = message.getPayload();
                log.info("MQ记录者接受到数据{}", myMqMessage);
                List<Dog> sendData = JacksonsUtil.readValue(myMqMessage, new TypeReference<List<Dog>>() {
                });
                dogService.saveBatch(sendData);
            } catch (Exception e) {
                log.error("消费异常，进行登记：\n\n {}", e.getMessage());
            }
        };
    }
}
```

## 五、方法调用

> 直接像service一样调用就行

```java
@Service
public class DogServiceImpl extends ServiceImpl<DogMapper, Dog> implements DogService {

    @Autowired
    MpSendService mpSendService;
    
    private void saveDogChange() {
        try {
            List<Dog> dogList = new ArrayList<>();
            // ...乱七八糟的代码
            // 下发MQ
            mpSendService.sendMyTesMessage(dogList);
        } catch (Exception e) {
            log.error("发送失败： \n\n {}", e);
        }
    }
}
```

