# Redis锁超时测试

> 2024-6-27 21:05:14

## 一、锁代码样例

* 锁代码如下

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
public class XXXServiceImpl {
    @Autowired
    RedisLockRegistry redisLockRegistry;

    void sss() {
        Lock lock = redisLockRegistry.obtain("iAmLock");
        boolean sucLock = false;
        try {
            // 在60s内不断尝试获取锁,如成功获取则返回true,并向下走
            sucLock = lock.tryLock(60, TimeUnit.SECONDS);
            // tryLock返回false意为获取锁超时,即在60s中都没有获取到锁
            if (sucLock) {
                log.info("拿到锁");
            } else {
                log.error("锁超时");
            }
            // ...乱七八糟的业务代码
        } catch (InterruptedException e) {
            log.error("获取锁失败{}", e.toString());
        } finally {
            if (sucLock) {
                // 解锁前需要判断锁是否存在，如果解锁不存在的锁会有异常抛出
                // 这里通过解锁是否成功来判断纯属无奈之举
                lock.unlock();
                log.info("释放锁");
            }
        }
    }
}
```

## 二、解锁时的报错

> 在解锁时，出现了当前锁不存在，无法解锁的报错

* 比如同一份代码。dev环境服务器部署一个，本地启动一个
* 锁代码如上文的代码样例

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