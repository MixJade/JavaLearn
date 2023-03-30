import org.junit.Test;

import java.util.Objects;

/**
 * @ClassName  : ObjectsTest
 * @Description : ObjectsTest
 * @Author : MixJade
 * @Date: 2023-03-30 15:20
 */
public class ObjectsTest {
    @Test
    public void testCheckFromIndexSize(){
        // 简单理解就是：[fromIndex， fromIndex + size )是否被包含于[0，length)。
        int i = Objects.checkFromIndexSize(1, 23, 40);
        System.out.println("一、索引的数据为："+i);
        // 分页条，从第8条数据开始，一页大小为8，总共有20条数据
        int i1 = Objects.checkFromIndexSize(8, 8, 20);
        System.out.println("二、索引的数据为："+i1);
        // 如果子范围超出范围，生成异常IndexOutOfBoundsException
        try {
            int i2 = Objects.checkFromIndexSize(8, 8, 12);
            System.out.println("三、索引的数据为："+i2);
        }catch (IndexOutOfBoundsException e){
            System.out.println("这是抛出的异常");
        }
    }
    @Test
    public void testCheckFromToIndex(){
        // 就是上面的加上了size的变种
        // 简单理解就是：[fromIndex，toIndex)是否被包含于[0，length)。
        int i = Objects.checkFromToIndex(23, 24, 40);
        System.out.println("一、索引的数据为："+i);
        // 分页条，从第8条数据开始，到第16条数据，总共有20条数据
        int i1 = Objects.checkFromToIndex(8, 16, 20);
        System.out.println("二、索引的数据为："+i1);
        // 如果子范围超出范围，生成异常IndexOutOfBoundsException
        try {
            int i2 = Objects.checkFromToIndex(8, 13, 12);
            System.out.println("三、索引的数据为："+i2);
        }catch (IndexOutOfBoundsException e){
            System.out.println("这是抛出的异常");
        }
    }

    @Test
    public void testCheckIndex() {
        // 简单理解就是：index是否被包含于[0，length)。
        int b = Objects.checkIndex(6,7);
        System.out.println("6是否小于7："+b);
        try {
            Objects.checkIndex(9,7);
        }catch (IndexOutOfBoundsException e){
            System.out.println("9并不小于7");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeepEquals() {
        // 对于基本数据类型来说，若是值相等则返回true。
        //对于引用数据类型来说，若是地址相等则返回true，
        // 否则继续判断值是否相等，相等则返回true，否则返回false。
        String a=null,b="哈哈哈";
        Boolean deepOne=Objects.deepEquals(a,b);
        // 或许可以替代equal
        System.out.println(deepOne);
    }
}
