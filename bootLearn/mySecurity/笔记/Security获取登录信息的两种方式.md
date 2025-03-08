# SpringSecurity获取登录信息的两种方式

## 1 在控制器参数获取Principal

```java
@GetMapping("/no")
public String areYouOk(Principal principal) {
    System.out.println(principal.getName());
    return "areYouOk";
}
```

在参数中直接注入Principal对象，使用getName就能获取到登录时的MyUser类的信息

但是，这样只能获取到MyUser的toString返回值。

## 2 在控制器参数获取Authentication

```java
@GetMapping("/no")
public String areYouOk(Authentication authentication) {
    if (authentication.getPrincipal() instanceof MyUser myUser) {
        System.out.println("登录的用户信息7是：" + myUser);
    }
    return "areYouOk";
}
```

这样就能获取到Object了

## 在方法内直接获取

```java
SecurityContextHolder.getContext().getAuthentication().getPrincipal();
```

这样就能直接获取Object，缺点是语法语法比较长，可以考虑封装成一个类

(整成下面这样是为了实验密封类的用法，不必在意，实际上直接整成一个工具类就行)

* 密封的接口

```java
/**
 * 这是密封类的语法，
 * 使用sealed修饰，permits之后跟着实现类
 * 实现类必须是final，或者也是一个密封类
 */
public sealed interface INowUser permits NowUserImpl {
    Object getUser();
}
```

* 实现类

```java
@Service
public final class NowUserImpl implements INowUser {
    @Override
    public Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
```

* 在控制器中，如此调用

```java
@RestController
@RequestMapping("/demo")
public class DemoController {
    INowUser nowUser;

    @Autowired
    public DemoController(INowUser nowUser) {
        this.nowUser = nowUser;
    }

    @GetMapping("/role")
    @Secured({RoleConst.ADMIN, RoleConst.ADMIN2})
    public String role() {
        // 可以如此获取当前登录的信息
        if (nowUser.getUser() instanceof MyUser myUser) {
            System.out.println("登录的用户信息是：" + myUser);
        }
        return "YES_ADMIN";
    }
}
```