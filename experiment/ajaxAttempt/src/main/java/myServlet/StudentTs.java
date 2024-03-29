package myServlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import myUtils.JsonUtil;
import pojo.StudentsMessage;
import sqlDemo.StudentsDemo;

import java.util.List;

public class StudentTs {
    public static void main(String[] args) throws JsonProcessingException {
        StudentsDemo studentsDemo = new StudentsDemo();
        List<StudentsMessage> studentsMessages = studentsDemo.allStudent();
        // 对象换成JSON
        // 下面那个是fastJson的转化方式，这个不能转化record类
        // String jsonString = JSON.toJSONString(studentsMessages.get(1));
        // 使用jackson转化，这个可以转化record
        String jsonString = JsonUtil.toStr(studentsMessages.get(1));
        System.out.println(jsonString);
        // JSON换成对象
        // StudentsMessage studentsMessage = JSON.parseObject(jsonString, StudentsMessage.class);
        StudentsMessage studentsMessage = JsonUtil.toObj(jsonString, StudentsMessage.class);
        System.out.println(studentsMessage);
    }
}
