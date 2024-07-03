package listStudy;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static listStudy.TestSortCompare.getManList;

/**
 * 测试将一个List转为Map
 *
 * @since 2024-7-3 16:27:24
 */
public class TestListToMap {
    List<Man> manList = getManList();

    /**
     * 将一个List(Obj)转为Map(key, List(Obj))
     * <p>最基础的分组</p>
     */
    @Test
    public void testGroupBy() {
        System.out.println("=====一、最基础的GroupBy操作=====");
        // 将manList转为键为age的map
        Map<Integer, List<Man>> manGroupMap = manList.stream()
                .filter(i -> i.age() != null) // 过滤防止key为null的数据混入
                .collect(Collectors.groupingBy(Man::age));
        // 逐行输出整个map的值
        Set<Integer> manGroupKeySet = manGroupMap.keySet();
        manGroupKeySet.forEach(i -> System.out.println(i + ":" + manGroupMap.get(i)));
    }

    /**
     * 将一个List(Obj)转为Map(key, List(Obj)),但Map的List(Obj)长度至少为2
     * <p>在group操作后添加过滤操作，只包含value列表长度至少为2的元素</p>
     */
    @Test
    public void testGroupButLeastTwo() {
        System.out.println("=====二、二次过滤操作=====");
        // 将manList转为键为age的map
        // 在group操作后添加过滤操作，只包含value列表长度至少为2的元素
        Map<Integer, List<Man>> manGroupMap = manList.stream()
                .filter(i -> i.age() != null) // 过滤防止key为null的数据混入
                .collect(Collectors.groupingBy(Man::age))
                // 开始过滤(先用entrySet生成一个set列表,对这个列表过滤)
                .entrySet().stream() // 重新生成一个stream流
                .filter(entry -> entry.getValue().size() >= 2) // 加上过滤
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 逐行输出整个map的值(这次使用entrySet输出玩玩)
        manGroupMap.entrySet().forEach(System.out::println);
    }

    /**
     * 将一个List(Obj)转为Map(key, Obj)
     * <p>元素的覆盖需要遵循某个逻辑，或者是单纯的先来后到</p>
     */
    @Test
    public void testGroupButOneMap() {
        System.out.println("=====三、单元素map转化=====");
        // 将manList转为键为age的map
        System.out.println("=====3.1、单元素map转化(单纯的先来后到)=====");
        Map<Integer, Man> manGroupMap = manList.stream()
                .filter(i -> i.age() != null) // 过滤防止key为null的数据混入
                .collect(Collectors.toMap(Man::age, man -> man, (oldVal, newVal) -> newVal)); // 冲突时使用新值
        // 逐行输出整个map的值
        manGroupMap.entrySet().forEach(System.out::println);
        // 将manList转为键为age的map(名字长的优先)
        System.out.println("=====3.1、单元素map转化(名字长的优先)=====");
        Map<Integer, Man> manGroupMap2 = manList.stream()
                .filter(i -> i.age() != null) // 过滤防止key为null的数据混入
                // 名字长的人优先
                .collect(Collectors.toMap(Man::age, man -> man, (oldVal, newVal) -> {
                    if (oldVal.name().length() > newVal.name().length())
                        return oldVal;
                    else return newVal;
                }));
        // 逐行输出整个map的值
        manGroupMap2.entrySet().forEach(System.out::println);
    }
}
