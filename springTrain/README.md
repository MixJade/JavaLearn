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

* javaBean(来自javaSE,好像不是这里的bean):
    * 也可称为实体类，其对象可用于封装数据
    * 为了规范，成员变量需使用private修饰
    * 提供成员变量对应的set、get方法
    * 必须有一个无参构造器，有参构造器可以写

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
    public BookDao getObject() {
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

## bean的生命周期

> * 重点

### 生命周期阶段简述

> * 一、初始化容器

1. 创建对象（即内存分配）
2. 执行构造方法  
   这就是为什么前面会出现一加载配置文件会自动执行构造方法~~
3. 执行属性注入(set操作)
4. 执行bean初始化方法

> * 二、使用bean

* 执行业务操作

> * 三、关闭/销毁容器

* 执行bean的销毁方法

## 操作，关于生命周期

> * 自己提供生命周期方法，并去容器里面声明
> * 通过实现特定接口来设置生命周期方法

### 关于通过在容器中声明来执行初始化和销毁方法

> * 了解即可，不常用，因为要在容器中声明

* 在实现类中定义初始化和销毁方法，
* 注意这个方法名字是自定义的，这样只是为了规范
* BookDaoImpl.java

```java
public class BookDaoImpl implements BookDao {
    public BookDaoImpl() {
        System.out.println("This is a constructor");
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
    }

    public void init() {
        System.out.println("Impl init==");
    }

    public void destroy() {
        System.out.println("Impl destroy==");
    }
}
```

* 在配置文件中定义初始化和销毁方法

```
<bean id="bookDaoReality" name="firstDao secondDao" class="dao.BookDaoImpl" init-method="init" destroy-method="destroy"/>
```

* 输出结果:

```
This is a constructor
Impl init==
```

* 可以看到销毁方法没有被执行，因为java虚拟机在执行销毁方法前就自动关闭了

> * 关于通过在容器中声明来执行销毁方法，却没有执行，
> * 只能在引用的方法中手动关闭，以执行销毁方法

* 因为`ApplicationContext`中没有close方法，只能将它换成`ClassPathXmlApplicationContext`
* 方式如下:

```
public void testIoC() {
    //加载xml文件，获取容器对象
    ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
    // 获取资源
    BookService bookService = (BookService) acx.getBean("service");
    bookService.deposit();
    //关闭容器以执行销毁方法
    acx.close();
}
```

* 可以发现销毁方法被执行了
* 但是直接close会很暴力，因为关闭的是整个容器，
* 如果后面还有操作，会导致无法执行

> * 使用`registerShutdownHook()`来注册销毁钩子
> * 向容器声明，在虚拟机关闭前执行销毁方法
> * `acx.registerShutdownHook()`可以放在方法的任何地方，不会导致后面的操作无法进行

```
public void testIoC() {
    //加载xml文件，获取容器对象
    ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
    // 获取资源
    BookService bookService = (BookService) acx.getBean("service");
    bookService.deposit();
    //关闭容器以执行销毁方法
    acx.registerShutdownHook();
}
```

### 通过实现接口来执行初始化与销毁

> * 通过实现InitializingBean与DisposableBean接口
> * 来自动执行初始化与销毁
> * 这样就不用在容器里面定义

* 实现类
* 小细节:初始化方法叫afterPropertiesSet
* 意为在实例化->生成对象->属性填充之后执行

```java
public class BookDaoImpl implements BookDao, InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean==接口");
    }

    @Override
    public void destroy() {
        System.out.println("DisposableBean==接口");
    }
}
```

* 配置文件(很正常的方式)

```
<bean id="bookDaoReality" name="firstDao secondDao" class="dao.BookDaoImpl"/>
```

* 可以看到执行效果并没有什么差别，
* 但是不用去容器里面配置

## 依赖注入

> 通过set方法、构造函数、容器自动装配的方式向类中传递参数

### 前言

> 只关于set方法、构造函数

* 向一个类中传递参数有两种方法:
    * setter方法
    * 构造函数
* 依赖注入描述了在容器中建立bean与bean之间的关系的过程
* 依赖注入的两种类型：
    * 引用类型(其他的bean)
    * 简单类型(基本数据类型与String)

### 实操

> 分为setter方法、构造器方法

#### setter方法

> * 通过name进行调用相应的setter方法
> * 注意这个name是setter方法的形参名,如果形参名不一样会找不到
> * 通过value进行赋值(只限简单类型)
> * 或者通过ref进行赋值(引用类型)
> * ref的引用类型---就是一开始的依赖注入

* 先设置setter方法

```java
public class BookDaoImpl implements BookDao {
    private String bookName;
    private int bookPrice;

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
        System.out.println(bookName + ":" + bookPrice);
    }
}
```

* 再在容器中配置

```
<!--通过setter注入依赖-->
<bean id="firstDao" class="dao.BookDaoImpl">
    <property name="bookName" value="MyBook"/>
    <property name="bookPrice" value="3"/>
</bean>
```

#### 构造器依赖注入方法

> * 比setter严谨，因为不能注入空值
> * 官方推荐，但平时还是用setter
> * 因为setter灵活
> * 一样的，简单类型用value，引用类型用ref

```java
public class BookDaoImpl implements BookDao {
    private final String bookName;
    private final int bookPrice;


    public BookDaoImpl(String bookName, int bookPrice) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
    }

    @Override
    public void store() {
        System.out.println("The book is stored on the shelf");
        System.out.println(bookName + ":" + bookPrice);
    }
}
```

```
<bean id="firstDao" class="dao.BookDaoImpl">
    <constructor-arg name="bookName" value="A Book"/>
    <constructor-arg name="bookPrice" value="900"/>
</bean>
<!--调用bean-->
<bean id="bookServiceReality" name="service" class="service.BookServiceImpl">
    <property name="bookDao" ref="firstDao"/>
</bean>
```

> * 除了通过name来声明是哪个参数，
> * 还可以通过type声明类型、index声明位置
> * 如果不进行声明，或者通过type声明但相应的类型不唯一，就会按容器的配置顺序

通过type声明类型

```
<bean id="firstDao" class="dao.BookDaoImpl">
    <constructor-arg type="java.lang.String" value="A Book"/>
    <constructor-arg type="int" value="900"/>
</bean>
```

index声明位置

```
<bean id="firstDao" class="dao.BookDaoImpl">
    <constructor-arg index="0" value="A Book"/>
    <constructor-arg index="1" value="900"/>
</bean>
```

不进行声明

```
<bean id="firstDao" class="dao.BookDaoImpl">
    <constructor-arg value="A Book"/>
    <constructor-arg value="900"/>
</bean>
```

#### 容器自动装配

> * 加入参数autowire
> * 当然还是要相应的setter方法
> * 有byName，通过相应的setter方法参数的ID或名字来在容器中查找相符合的bean
> * 有byType，通过相应setter方法在容器中查找相符合的类型
> * 有constructor，通过构造器，原理差不多

byName

```
<bean id="bookDao" name="firstDao" class="dao.BookDaoImpl"/>
<!--调用bean-->
<bean id="bookServiceReality" name="service" class="service.BookServiceImpl" autowire="byName"/>
```

byType,实现了对应接口的类也算

```
<bean id="book00Dao" name="bookDa0o" class="dao.BookDaoImpl"/>
<!--调用bean-->
<bean id="bookServiceReality" name="service" class="service.BookServiceImpl" autowire="byType"/>
```

附:对应的setter方法

```
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

## 集合注入
> * 在bean里面设置集合及setter,在容器注入集合元素
> * value可以变成`bean ref=`，但是很麻烦，只是了解

* BookGather.java
```java
public class BookGather implements BookDao {
    private int[] array;
    private List<String> list;
    private Set<String> set;
    private Map<String, String> map;
    private Properties properties;

    public void setArray(int[] array) {
        this.array = array;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void store() {
        System.out.println("book dao save ...");
        System.out.println("遍历数组:" + Arrays.toString(array));
        System.out.println("遍历List" + list);
        System.out.println("遍历Set" + set);
        System.out.println("遍历Map" + map);
        System.out.println("遍历Properties" + properties);
    }
}
```

* 配置文件写法：
```
<bean id="bookDao" class="testGather.BookGather">
    <!--数组注入-->
    <property name="array">
        <array>
            <value>100</value>
            <value>200</value>
            <value>300</value>
        </array>
    </property>
    <!--list集合注入-->
    <property name="list">
        <list>
            <value>first</value>
            <value>second</value>
            <value>third</value>
            <value>fourth</value>
        </list>
    </property>
    <!--set集合注入-->
    <property name="set">
        <set>
            <value>first</value>
            <value>second</value>
            <value>third</value>
            <value>fourth</value>
        </set>
    </property>
    <!--map集合注入-->
    <property name="map">
        <map>
            <entry key="country" value="china"/>
            <entry key="province" value="henan"/>
            <entry key="city" value="kaifeng"/>
        </map>
    </property>
    <!--Properties注入-->
    <property name="properties">
        <props>
            <prop key="country">china</prop>
            <prop key="province">henan</prop>
            <prop key="city">kaifeng</prop>
        </props>
    </property>
</bean>
```
* 最后输出:
```
@Test
public void testGather(){
    ClassPathXmlApplicationContext acx=new ClassPathXmlApplicationContext("applicationContext.xml");
    BookDao bookDao= (BookDao) acx.getBean("bookDao");
    bookDao.store();
}
```