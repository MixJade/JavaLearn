package myServlet;

import com.alibaba.fastjson.JSON;
import sqlDemo.StudentsDemo;
import pojo.StudentsMessage;

import java.util.List;

public class StudentTs {
    public static void main(String[] args) {
        StudentsDemo studentsDemo = new StudentsDemo();
        List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
        // 对象换成JSON
        String jsonString = JSON.toJSONString(studentsMessages.get(1));
        System.out.println(jsonString);
        // JSON换成对象
        StudentsMessage studentsMessage = JSON.parseObject(jsonString, StudentsMessage.class);
        System.out.println(studentsMessage);
    }
}
