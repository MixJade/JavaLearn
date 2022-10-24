package service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service()
@Scope("singleton")
public class BookServiceImpl implements BookService {
    public BookServiceImpl() {
        System.out.println("hhhhhhhhhhh");
    }
    @Override
    public void deposit() {
        System.out.println("You are saving a book");
    }

    @PostConstruct
    public void init(){
        System.out.println("这是在构造方法之后");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("只有单例作用域才会执行销毁方法");
        System.out.println("这是在调用关闭钩子后，在销毁之前");
    }
}
