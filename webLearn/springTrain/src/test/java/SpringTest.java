import baiduCheck.ResourcesService;
import myConfig.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.StudentsTable;
import service.AccountService;
import service.StudentService;
import testAOP.AOPTest;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SpringTest {
    @Autowired
    private StudentService service;

    @Test
    public void testMybatis() {
        List<StudentsTable> studentsTables = service.selectAll();
        for (StudentsTable studentsTable : studentsTables) {
            System.out.println(studentsTable);
        }
    }

    @Autowired
    private AOPTest aopTest;

    @Test
    public void testAOP() {
        aopTest.firstMethod();
    }

    @Test
    public void testAOPParam() {
        aopTest.secondMethod(2000);
    }

    @Test
    public void testAOPReturn() {
        String s = aopTest.thirdMethod();
        System.out.println(s);
    }

    @Test
    public void testAOPThrow() {
        String s = aopTest.fourthMethod();
        System.out.println(s);
    }

    @Test
    public void baiduCheck(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        ResourcesService resourcesService = ctx.getBean(ResourcesService.class);
        boolean flag = resourcesService.openURL("https://pan.baidu.com/haha", "root ");
        System.out.println(flag);
    }

    @Autowired
    private AccountService accountService;

    @Test
    public void testTransfer(){
        accountService.transfer("黄金之风","轻音部",100D);
    }
}
