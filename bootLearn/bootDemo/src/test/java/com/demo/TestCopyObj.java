package com.demo;

import com.demo.TestObj.ObjA;
import com.demo.TestObj.ObjB;
import org.springframework.beans.BeanUtils;

/**
 * 测试Spring的对象属性复制
 *
 * @author MixJade
 * @since 2024-02-22
 */
public class TestCopyObj {
    public static void main(String[] args) {
        ObjA objA = new ObjA(123, "wc原", 12);
        ObjB objB = new ObjB();

        // 注意：这个工具类是springframework的
        // 只有A中与B相同的字段才会赋予B
        BeanUtils.copyProperties(objA, objB);

        // 此时B已经获取了A的值,甚至包装类也能自动赋值
        // 但类型不同就不会赋,比如objB中String类型的age,就获取不到objA中int类型的age
        System.out.println(objB);
    }
}
