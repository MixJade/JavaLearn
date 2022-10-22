# Spring学习开始

> * spring framework是一个bean容器、优秀的开源框架，
> * 是 spring mvc、spring boot等多种“spring应用”的基础

## 控制反转IoC (Inversion of Control)，

* 使用对象是，由主动new对象，转为外部提供对象，通过容器来实现对象组件的装配和管理。
* 所谓的“控制反转”概念就是对组件对象控制权的转移，从程序代码本身转移到了外部容器。
* spring提供了一个容器，叫IoC容器，作为IoC思想的外部

## 依赖注入DI（Dependency Injection）

* 在容器间建立bean与bean之间的联系
* 依赖注入的基本原则是：应用组件不应该负责查找资源或者其他依赖的协作对象。
* 配置对象的工作应该由IoC容器负责，“查找资源”的逻辑应该从应用组件的代码中抽取出来，交给IoC容器负责。

## IoC上手

> * 管理什么?"Service和Dao"
> * 如何将被管理对象告知IoC容器?"配置文件"
> * 如何获取IoC容器?"接口"
> * 如何从IoC容器获取bean?"接口方法"
> * 使用Spring需要导入的坐标?"pom.xml"

1. 引入依赖包
    ```        
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.2.10.RELEASE</version>
    </dependency>
    ```

2. 创建spring管理的类(接口)
    * 接口
   ```java
   public interface BookDao {
       void store();
   }
   ```   
   ```java
   public interface BookService {
       void deposit();
   }
   ```
    * 实现类
   ```java
   public class BookDaoImpl implements BookDao{
       @Override
       public void store() {
           System.out.println("The book is stored on the shelf");
       }
   }
   ```
   ```java
   public class BookServiceImpl implements BookService {
       private BookDao bookDao = new BookDaoImpl();
       @Override
       public void deposit() {
           System.out.println("You are saving a book");
           bookDao.store();
       }
   }
   ```

3. 建立spring配置文件`applicationContext.xml`
    * 配置对应类作为spring管理的bean(要这种能new对象的实现类)
    * id可以自定义,class是实现类的路径
    * 注意id不能在xml文件里重复
   ```
   <bean id="bookDaoReality" class="dao.BookDaoImpl"/>
   <bean id="bookServiceReality" class="service.BookServiceImpl"/>
   ```
4. 初始化IoC容器，通过容器获取bean
   ```
    public void testIoC01(){
        //加载xml文件，获取容器对象
        ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取资源
        BookService bookService= (BookService) acx.getBean("bookServiceReality");
        bookService.deposit();
    }
   ```

## DI上手

> * 基于IoC管理bean
> * Service不再使用new形式创建Dao对象
> * Service使用的对象通过Dao提供
> * Service与Dao之间的关系通过配置文件描述

1. 删除业务层中使用new的形式创建对象，并通过set方法来写入对象
   ```java
   public class BookServiceImpl implements BookService {
       private BookDao bookDao;
       @Override
       public void deposit() {
           System.out.println("You are saving a book");
           bookDao.store();
       }
   
       public void setBookDao(BookDao bookDao) {
           this.bookDao = bookDao;
       }
   }
   ```
2. 在spring配置文件声明两者的关系
    * property表示配置当前bean的属性
    * name表示具体需要的属性
    * ref是配置文件中对应属性的id或者别名
   ```
    <bean id="bookServiceReality" class="service.BookServiceImpl">
        <property name="bookDao" ref="bookDaoReality"/>
    </bean>
   ```
   从此业务层便见不到持久层的接口实现了

## Spring配置文件

> * 给bean起别名
> * 设置作用域(单例与非单例(又名原型))

1. 给bean起别名
    * 可以通过ref调用
    * 可以起多个别名，通过空格、逗号、分号分割
   ```
   <bean id="bookDaoReality" name="firstDao secondDao" class="dao.BookDaoImpl"/>
   <bean id="bookServiceReality" name="service" class="service.BookServiceImpl">
     <property name="bookDao" ref="firstDao"/>
   </bean>
   ```
    * 甚至可以通过name进行引用
   ```
   BookService bookService= (BookService) acx.getBean("service");
   bookService.deposit();
   ```
2. 设置作用域
    * 默认为单例对象，即为一个bean创建两个不同的对象，它们的地址是一样的
   ```
   ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
   BookDao bookDao1= (BookDao) acx.getBean("secondDao");
   BookDao bookDao2= (BookDao) acx.getBean("secondDao");
   System.out.println(bookDao1);
   System.out.println(bookDao2);
   ```
    * 输出地址，可以看到是一样的
   ```
   dao.BookDaoImpl@34a3d150
   dao.BookDaoImpl@34a3d150
   ```
    * 在xml文件中通过`scope="prototype"`设置为原型对象
    * ```<bean id="bookDaoReality" name="firstDao secondDao" class="dao.BookDaoImpl" scope="prototype"/>```
    * scope有两个值,`singleton`和`prototype`分别对应单例(默认)和非单例

### 关于单例与原型的区别

* **单例**
  ：一个bean被声明为单例时，处理多次请求时spring容器里只实例化一个bean，后续的请求公用这个对象，这个对象存储在一个map中，当有请求时，先在缓存中（map）查找是否存在，存在则使用，不存在才实例化一个对象
* **原型**：每当有请求来就实例化一个新的bean，没有缓存以及从缓存中查

> 代码逻辑

* 生成bean时先判断单例的还是原型
* 如果是单例的则先尝试从缓存里获取，没有在新创建

**总结**

* 单例的bean只有第一次创建新的bean 后面都会复用该bean，所以不会频繁创建对象。
* 原型的bean每次都会新创建

### 为什么bean默认设计成单例？

* **减少了新生成实例的消耗**
* 新生成实例消耗包括两方面，第一，spring会通过反射或者cglib来生成bean实例这都是耗性能的操作，其次给对象分配内存也会涉及复杂算法。

* **减少jvm垃圾回收**
* 由于不会给每个请求都新生成bean实例，所以自然回收的对象少了。

* **可以快速获取到bean**
* 因为单例的获取bean操作除了第一次生成之外其余的都是从缓存里获取的所以很快。
* **总结**
  > * 单例的优势
    * 为了提高性能
    * 少创建实例
    * 方便垃圾回收
    * 缓存快速获取
  > * 单例的劣势
    * 如果是有状态的话在并发环境下线程不安全。
    * 有状态对象:有实例变量的对象，有存储数据能力
    * 无状态对象:无实例变量的对象，无存储数据能力
  > * 适合交给容器管理的bean
    * 表现层对象
    * 业务层对象
    * 数据层对象
    * 工具对象
  > * 不适合的bean
    * 封装实体的域或对象

## 生成bean的三种方式

> * 通过无参构造方法生成bean
> * 通过静态工厂实例化bean
> * 通过实例工厂实例化bean
> * 使用FactoryBean实例化bean

1. **通过无参构造**  
   默认调用无参构造方法;  
   如果只存在一个有参构造的方法，会报错`NoSuchMethodException`  
   在加载配置文件(``ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml")``)
   的时候，会执行一次构造方法  
   每加载一次，就执行一次```This is a constructor```
2. **通过静态工厂实例化bean**
    * 在工厂类中定义一个静态方法返回相应的bean对象
    * 这个工厂类里面可以做一些操作，不然会显得多余
    * BookDaoFactory.java
    ```java
    public class BookDaoFactory {
        public static BookDao getBookDao(){
            System.out.println("I am a factory");
            return new BookDaoImpl();
        }
    }
    ```
    * 在xml文件中进行配置,
    * 与实例化相比多了`factory-method="getBookDao"`来定义工厂方法
    * ```<bean id="bookDaoFactory" class="dao.BookDaoFactory" factory-method="getBookDao"/>```
    * 在加载配置文件时会先执行一次(工厂方法-实现类构造方法)
    ```
    I am a factory
    This is a constructor
    ```
   只要加载一次配置文件就会重复上面一次
3. **通过实例工厂实例化bean**
    * 先建一个实例工厂
    ```java
    public class BookDaoFactory {
        public BookDao getBookDao(){
            System.out.println("I am a factory");
            return new BookDaoImpl();
        }
    }
    ```
    * 通过配置文件进行实例化
    ```
    <bean id="bookDaoFactory" class="dao.BookDaoFactory"/>
    <bean id="bookDaoUser" factory-method="getBookDao" factory-bean="bookDaoFactory"/>
    ```
    * 一样的，在每次加载配置文件时都会执行一遍

### 使用FactoryBean实例化bean
> * 通过实例工厂实例化bean有缺点
> * 一是两条xml语句配合使用没有意义
> * 二是方法名不固定，每次都需要重新配置
> * 后来spring整了一个接口，叫FactoryBean

* 实现FactoryBean接口,重写部分方法
```java
public class BookDaoFactoryBean implements FactoryBean<BookDao> {

    @Override
    public BookDao getObject() throws Exception {
        System.out.println("得到对象");
        return new BookDaoImpl();
    }

    @Override
    public Class<?> getObjectType() {
        System.out.println("返回类型");
        return BookDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

```
* 注意`isSingleton`方法是设置实例化的bean是单例还是实例
* 默认为true,即单例；可以设置成false，即原型
* 配置文件：
```
<bean id="bookDaoFactoryBean" class="dao.BookDaoFactoryBean"/>
```
* 加载配置文件会这样输出
```
得到对象
This is a constructor
返回类型
```