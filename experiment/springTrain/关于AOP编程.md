# 关于AOP编程

> * SpringAOP本质是代理模式
> * AOP核心概念有两个:目标对象和代理

## AOP概念

### 什么是AOP?

* AOP(Aspect Oriented Programming)面向切面编程，一种编程范式，指导开发者如何组织程序结构。
    * OOP(Object Oriented Programming)面向对象编程

我们都知道OOP是一种编程思想，那么AOP也是一种编程思想，编程思想主要的内容就是指导程序员该如何编写程序，所以它们两个是不同的`编程范式`。

### AOP作用

- 作用:在不惊动原始设计的基础上为其进行功能增强

### AOP核心概念

* 连接点(JoinPoint)：程序执行过程中的任意位置，粒度为执行方法、抛出异常、设置变量等
    * 在SpringAOP中，理解为方法的执行
* 切入点(Pointcut):匹配连接点的式子
    * 在SpringAOP中，一个切入点可以描述一个具体方法，也可也匹配多个方法
        * 一个具体的方法:如dao包下的BookDao接口中的无形参无返回值的save方法
        * 匹配多个方法:所有的save方法，所有的get开头的方法，所有以Dao结尾的接口中的任意方法，所有带有一个参数的方法
    * 连接点范围要比切入点范围大，是切入点的方法也一定是连接点，但是是连接点的方法就不一定要被增强，所以可能不是切入点。
* 通知(Advice):在切入点处执行的操作，也就是共性功能
    * 在SpringAOP中，功能最终以方法的形式呈现
* 通知类：定义通知的类
* 切面(Aspect):描述通知与切入点的对应关系。

### AOP工作流程

1. 流程1:Spring容器启动
2. 流程2:读取所有切面配置中的切入点
3. 流程3:初始化bean，
    * 匹配失败，创建原始对象
    * 匹配成功，创建原始对象(目标对象)的**代理对象**
4. 流程4:获取bean执行方法
    * 获取的bean是原始对象时，调用方法并执行，完成操作
    * 获取的bean是代理对象时，根据代理对象的运行模式运行原始方法与增强的内容，完成操作

## 上手

* 先在pom.xml导入坐标

```html

<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.9.1</version>
</dependency>
```

* 再定义一个接口和它的实现类
* 这个两个类很正常，毕竟AOP主打的就是无侵入式
* AOPTest.java

```java
public interface AOPTest {
    void firstMethod();

    void secondMethod();

    void thirdMethod();
}
```

* AOPTestImpl.java

```java

@Component
public class AOPTestImpl implements AOPTest {
    @Override
    public void firstMethod() {
        System.out.println("这是第一个方法");
    }

    @Override
    public void secondMethod() {
        System.out.println("This is a second method");
    }

    @Override
    public void thirdMethod() {
        System.out.println("第三个方法");
    }
}
```

* 再定义一个通知类，用于实现AOP
* Component声明bean,Aspect声明这是一个通知类
* Pointcut写所实现方法的全路径，下面跟一个private的空方法
* Before表示在切入方法之前执行,也可以用其他注解，比如Around表示把切入方法放在通知方法里面
* MyAdvice.java

```java

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void testAOP.AOPTest.firstMethod())")
    private void needMethod() {
    }

    @Before("needMethod()")
    public void adviceMethod() {
        System.out.println("这是切片");
        System.out.println(System.currentTimeMillis());
    }
}
```

* 接着在配置类中进行导入
* 注意:新增了注解`@EnableAspectJAutoProxy`
* 作用是向spring声明所扫描的bean中存在通知类
* 其他的都是上一个实验的

```java

@Configuration
@ComponentScan({"service", "dao", "testAOP"})
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class, MybatisConfig.class})
public class SpringConfig {
}
```

# 关于around的注解

* 把配置类这样改就行，完全就是大改
* 因为这位注解有严格的格式
* 这个格式给其他的注解通用

```java

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void testAOP.AOPTest.*Method())")//芝士匹配所有以Method的方法
    private void needMethod() {
    }

    @Around("needMethod()")
    public Object adviceMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("这是切片");
        long beginTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        Thread.sleep(200);
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - beginTime;
        System.out.println("所耗费的时间为" + spendTime);
        return obj;
    }
}
```

* 环绕通知注意事项
    1. 环绕通知必须依赖形参ProceedingJoinPoint才能实现对原始方法的调用，进而实现原始方法调用前后同时添加通知
    2. 通知中如果未使用ProceedingJoinPoint对原始方法进行调用将跳过原始方法的执行
    3. 对原始方法的调用可以不接收返回值，通知方法设置成void即可，如果接收返回值，最好设定为Object类型
    4. 原始方法的返回值如果是void类型，通知方法的返回值类型可以设置成void,也可以设置成Object
    5. 由于无法预知原始方法运行后是否会抛出异常，因此环绕通知方法必须要处理Throwable异常

* 其实AOP的通知一共五种类型
    - 前置通知
    - 后置通知
    - **环绕通知(重点)**
    - 返回后通知(了解)
    - 抛出异常后通知(了解)

# 切入点表达式书写技巧及规范

### 切入点表达式格式

* 切入点表达式标准格式：动作关键字(访问修饰符 返回值 包名.类/接口名.方法名(参数) 异常名）

对于这个格式，我们不需要硬记，通过一个例子，理解它:

```
execution(public User com.itheima.service.UserService.findById(int))
```

* execution：动作关键字，描述切入点的行为动作，例如execution表示执行到指定切入点
* public:访问修饰符,还可以是public，private等，可以省略
* User：返回值，写返回值类型
* com.service：包名，多级包使用点连接
* UserService:类/接口名称
* findById：方法名
* int:参数，直接写参数的类型，多个类型用逗号隔开
* 异常名：方法定义中抛出指定异常，可以省略

### 使用通配符描述表达式

* `*`:单个独立的任意符号，可以独立出现，也可以作为前缀或者后缀的匹配符出现

  ```
  execution（public * com.*.UserService.find*(*))
  ```

  匹配com包下的任意包中的UserService类或接口中所有find开头的带有一个参数的方法

* `..`：多个连续的任意符号，可以独立出现，常用于简化包名与参数的书写

  ```
  execution（public User com..UserService.findById(..))
  ```

  匹配com包下的任意包中的UserService类或接口中所有名称为findById的方法

* `+`：专用于匹配子类类型

  ```
  execution(* *..*Service+.*(..))
  ```

  使用率较低，描述子类的，继承机会就一次，使用都很慎重，所以很少用它。*Service+，表示所有以Service结尾的接口的子类。

# 关于切入点的信息

> * 通过pjp.getSignature获取切入点的签名对象
> * 通过切入点的签名对象来获取相应的信息

## 获取切入点的类路径、类名、方法名

```
Signature signature = pjp.getSignature();//获取切入点签名
System.out.println("所引用类型为:" + signature.getDeclaringType());
System.out.println("所引用的类为:" + signature.getDeclaringTypeName());
System.out.println("切入点名为:" + signature.getName());
```

* 输出结果如下

```
所引用类型为:interface testAOP.AOPTest
所引用的类为:testAOP.AOPTest
切入点名为:firstMethod
```

## 获取切入点的方法参数

> 当然也可以修改

* 首先修改通知类让它可以匹配任意参数值的方法(就是在方法参数描述那里加两个点)
* 然后通过切入点签名来获取参数(是一个数组)

```java

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void testAOP.AOPTest.*Method(..))")//芝士匹配所有以Method的方法
    private void needMethod() {
    }

    @Around("needMethod()")
    public Object adviceMethod(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();//获取切入点签名
        System.out.println("切入点名为:" + signature.getDeclaringTypeName() + "." + signature.getName());
        long beginTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();// 获取方法参数
        args[0] = 890;// 改变参数
        Object obj = pjp.proceed();// 执行方法
        System.out.println("该方法的参数是" + Arrays.toString(args));//输出参数
        Thread.sleep(200);
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - beginTime;
        System.out.println("所耗费的时间为" + spendTime);
        return obj;
    }
}
```

* 接着配置相应的方法让它可以有参数

```
@Override
public void secondMethod(int number) {
    System.out.println("This is a second method,and the param is "+number);
}
```

* 还有测试方法

```
@Test
public void testAOPParam(){
    aopTest.secondMethod(2000);
}
```

* 当然获取参数并非什么都不做，还可以改变它的参数
* 可以在以后通过这种方法来改变错误的传参，或者设置默认的参数
* 可以像这样改变输入的参数`args[0] = 890;// 改变参数`

* 最后输出

```
切入点名为:testAOP.AOPTest.secondMethod
This is a second method,and the param is 2000
该方法的参数是[890]
所耗费的时间为208
```

## 获取切入点的返回值

* 首先改变关于切入点的描述
* 让它可以匹配任意的返回值，注意这里不能省略Public了，或者打两个星
* 然后用AfterReturning注解，注意要设置value的代理方法和returning的参数名
* 通知的形参要与returning的名字一样

```java

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(public * testAOP.AOPTest.*Method(..))")//芝士匹配所有以Method的方法
    private void needMethod() {
    }

    @AfterReturning(value = "needMethod()", returning = "obj")
    public void adviceMethod(Object obj) {
        System.out.println("这是返回之后的通知:" + obj);
    }
}
```

* 然后是相应方法

```
@Override
public String thirdMethod() {
    System.out.println("第三个方法");
    return "我要发动一次第三方法";
}
```

* 与测试方法

```
@Test
public void testAOPReturn(){
    String s = aopTest.thirdMethod();
    System.out.println(s);
}
```

* 最后输出

```
第三个方法
这是返回之后的通知:我要发动一次第三方法
我要发动一次第三方法
```

## 关于切入点抛出的异常

* 我们先定义一个一定会抛出异常的方法
* 注意：因为它有返回值，所以要在抛出方法前面加一个永远为真的判断
* 不然编译器会报错，因为return语句永远到达不了

```
@Override
public String fourthMethod() {
    System.out.println("这是第四个方法");
    if (true) throw new NullPointerException();
    return "第四个方法";
    }
```

* 然后通过注解AfterThrowing定义一个抛出异常后的通知方法
* 这个不用注释前面的，事实上前面那个AfterReturning也不用注释之前的

```java

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(public * testAOP.AOPTest.*Method(..))")//芝士匹配所有以Method的方法
    private void needMethod() {
    }

    @AfterReturning(value = "needMethod()", returning = "obj")
    public void adviceMethod(Object obj) {
        System.out.println("这是返回之后的通知:" + obj);
    }

    @AfterThrowing(value = "needMethod()", throwing = "e")
    public void adviceMethod03(Throwable e) {
        System.out.println("这是它抛出的异常" + e);
    }
}
```

* 这是输出
* 后面还跟着与异常相应的一大堆报错

```
这是第四个方法
这是它抛出的异常java.lang.NullPointerException
```

## 还有其它的方式

### AOP三种获取

* 获取切入点方法的参数，所有的通知类型都可以获取参数
    * JoinPoint：适用于前置、后置、返回后、抛出异常后通知
    * ProceedingJoinPoint：适用于环绕通知
* 获取切入点方法返回值，前置和抛出异常后通知是没有返回值，后置通知可有可无，所以不做研究
    * 返回后通知
    * 环绕通知
* 获取切入点方法运行异常信息，前置和返回后通知是不会有，后置通知可有可无，所以不做研究
    * 抛出异常后通知
    * 环绕通知
# 案例:验证密码

> * 当然不会真的验证
> * 百度网盘的密钥是跟URL绑定的，两个一起验证
> * 这个逻辑是展示通过spring验证密钥是否正确

* 两个接口不言而喻
* Dao接口的实现类

```java

@Repository
public class ResourcesDaoImpl implements ResourcesDao {
    @Override
    public boolean readResources(String url, String password) {
        System.out.println(password.length());
        //模拟校验
        return password.equals("root");
    }
}
```

* Service接口实现类

```java

@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private ResourcesDao resourcesDao;

    @Override
    public boolean openURL(String url, String password) {
        return resourcesDao.readResources(url, password);
    }
}
```

* 通知类
* 注意里面的判断参数是不是字符串的代码

```java

@Component
@Aspect
public class DataAdvice {
    @Pointcut("execution(boolean baiduCheck.*Service.*(*,*))")
    private void servicePt() {
    }

    @Around("DataAdvice.servicePt()")
    public Object trimStr(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            //判断参数是不是字符串
            if (args[i].getClass().equals(String.class)) {
                args[i] = args[i].toString().trim();
            }
        }
        return pjp.proceed(args);
    }
}
```

* 测试方法

```
@Test
public void baiduCheck(){
    ApplicationContext ctx = new AnnotationConfigApplicationContext(myConfig.SpringConfig.class);
    ResourcesService resourcesService = ctx.getBean(ResourcesService.class);
    boolean flag = resourcesService.openURL("https://pan.baidu.com/haha", "root ");
    System.out.println(flag);
}
```

# AOP总结

### AOP的核心概念

* 概念：AOP(Aspect Oriented Programming)面向切面编程，一种编程范式
* 作用：在不惊动原始设计的基础上为方法进行功能==增强==
* 核心概念
    * 代理（Proxy）：SpringAOP的核心本质是采用代理模式实现的
    * 连接点（JoinPoint）：在SpringAOP中，理解为任意方法的执行
    * 切入点（Pointcut）：匹配连接点的式子，也是具有共性功能的方法描述
    * 通知（Advice）：若干个方法的共性功能，在切入点处执行，最终体现为一个方法
    * 切面（Aspect）：描述通知与切入点的对应关系
    * 目标对象（Target）：被代理的原始对象成为目标对象

### 切入点表达式

* 切入点表达式标准格式：动作关键字(访问修饰符 返回值 包名.类/接口名.方法名（参数）异常名)

  ```
  execution(* com.itheima.service.*Service.*(..))
  ```

* 切入点表达式描述通配符：

    * 作用：用于快速描述，范围描述
    * `*`：匹配任意符号（常用）
    * `..` ：匹配多个连续的任意符号（常用）
    * `+`：匹配子类类型

* 切入点表达式书写技巧

  1.按==标准规范==开发
  2.查询操作的返回值建议使用\*匹配
  3.减少使用..的形式描述包
  4.==对接口进行描述==，使用\*表示模块名，例如UserService的匹配描述为*Service
  5.方法名书写保留动词，例如get，使用\*表示名词，例如getById匹配描述为getBy\*
  6.参数根据实际情况灵活调整

### 五种通知类型

- 前置通知
- 后置通知
- 环绕通知（重点）
    - 环绕通知依赖形参ProceedingJoinPoint才能实现对原始方法的调用
    - 环绕通知可以隔离原始方法的调用执行
    - 环绕通知返回值设置为Object类型
    - 环绕通知中可以对原始方法调用过程中出现的异常进行处理
- 返回后通知
- 抛出异常后通知

### 通知中获取参数

- 获取切入点方法的参数，所有的通知类型都可以获取参数
    - JoinPoint：适用于前置、后置、返回后、抛出异常后通知
    - ProceedingJoinPoint：适用于环绕通知
- 获取切入点方法返回值，前置和抛出异常后通知是没有返回值，后置通知可有可无，所以不做研究
    - 返回后通知
    - 环绕通知
- 获取切入点方法运行异常信息，前置和返回后通知是不会有，后置通知可有可无，所以不做研究
    - 抛出异常后通知
    - 环绕通知