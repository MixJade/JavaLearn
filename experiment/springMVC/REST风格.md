# REST风格
> * REST风格，即不使用具体的资源路径来访问具体的方法
> * 而是通过访问的动作来区分

* 按照REST风格访问资源时使用==行为动作==区分对资源进行了何种操作
    * `http://localhost/users`	查询全部用户信息 GET（查询）
    * `http://localhost/users/1`  查询指定用户信息 GET（查询）
    * `http://localhost/users`    添加用户信息    POST（新增/保存）
    * `http://localhost/users`    修改用户信息    PUT（修改/更新）
    * `http://localhost/users/1`  删除用户信息    DELETE（删除）

* 最初最原始的REST风格
* 原始的在注释里
```java
//@Controller
//@ResponseBody配置在类上可以简化配置，表示设置当前每个方法的返回值都作为响应体
//@ResponseBody
@RestController     //使用@RestController注解替换@Controller与@ResponseBody注解，简化书写
@RequestMapping("/books")
public class BookController {

    //    @RequestMapping( method = RequestMethod.POST)
    @PostMapping        //使用@PostMapping简化Post请求方法对应的映射配置
    public String save(@RequestBody Book book){
        System.out.println("book save..." + book);
        return "{'module':'book save'}";
    }

    //    @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")     //使用@DeleteMapping简化DELETE请求方法对应的映射配置
    public String delete(@PathVariable Integer id){
        System.out.println("book delete..." + id);
        return "{'module':'book delete'}";
    }

    //    @RequestMapping(method = RequestMethod.PUT)
    @PutMapping         //使用@PutMapping简化Put请求方法对应的映射配置
    public String update(@RequestBody Book book){
        System.out.println("book update..."+book);
        return "{'module':'book update'}";
    }

    //    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
    @GetMapping("/{id}")    //使用@GetMapping简化GET请求方法对应的映射配置
    public String getById(@PathVariable Integer id){
        System.out.println("book getById..."+id);
        return "{'module':'book getById'}";
    }

    //    @RequestMapping(method = RequestMethod.GET)
    @GetMapping             //使用@GetMapping简化GET请求方法对应的映射配置
    public String getAll(){
        System.out.println("book getAll...");
        return "{'module':'book getAll'}";
    }
}
```

* 经过简化的REST风格
```java
//标准REST风格控制器开发
@RestController
@RequestMapping("/books")
public class BookController2 {

    @PostMapping
    public String save(@RequestBody Book book){
        System.out.println("book save..." + book);
        return "{'module':'book save'}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        System.out.println("book delete..." + id);
        return "{'module':'book delete'}";
    }

    @PutMapping
    public String update(@RequestBody Book book){
        System.out.println("book update..."+book);
        return "{'module':'book update'}";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id){
        System.out.println("book getById..."+id);
        return "{'module':'book getById'}";
    }

    @GetMapping
    public String getAll(){
        System.out.println("book getAll...");
        return "{'module':'book getAll'}";
    }
}
```