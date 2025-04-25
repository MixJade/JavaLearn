package testJson;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * JackSon将String转为List_obj
 *
 * @author MixJade
 * @since 2024-5-8 17:33:45
 */
public class TesStringToListObj {
    public static void main(String[] args) {

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("张三", 13));
        personList.add(new Person("李四", 24));
        personList.add(new Person("王五", 15));

        // 将List_obj转为String
        String jsonString = JsonUtil.objToStr(personList);
        System.out.println("转为String的结果:" + jsonString);

        // 将String转为List_obj，注意这里的接收类型List<Person>是默认转化目标
        List<Person> listPerson = JsonUtil.strToObj(jsonString, new TypeReference<>() {
        });
        System.out.println("转成的结果：" + listPerson);
    }

    record Person(String name, int age) {
    }
}
