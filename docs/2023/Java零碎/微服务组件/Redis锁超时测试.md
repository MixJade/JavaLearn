# Redis锁超时测试

> 2024-6-27 21:05:14

* 比如同一份代码。dev环境服务器部署一个，本地启动一个

* 锁代码如下

```java
import org.springframework.integration.redis.util.RedisLockRegistry;

public class XXXServiceImpl {
    @Autowired
    RedisLockRegistry redisLockRegistry;

    void sss() {
        Lock lock = redisLockRegistry.obtain("iAmLock");
        try {
            lock.tryLock(60, TimeUnit.SECONDS);
            log.info("拿到锁");

        } catch (InterruptedException e) {
            log.error(e.toString());
        } finally {
            lock.unlock();
            log.info("释放锁");
        }
    }
}
```

然后做如下操作

* 先请求本地接口：拿到锁以后sleep(100000)。(修改本地代码)
* 在本地请求以后，马上请求服务器的接口。

等60s以后，就会发现，服务器的接口调通。

等本地的sleep(100000)完成以后，本地就报错：

```text
IllegalStateException: You do not own lock at redis_integration
```

因为本地要释放锁的时候，没有锁了。

锁在redis中的默认过期时间是60s，本地占用了超过60秒后，锁自动销毁。

这时服务器获取到锁了，所以服务器在最后几秒钟完成了请求。

本地的代码sleep完了以后，就要去释放锁了，结果锁没有了，就抛异常了。