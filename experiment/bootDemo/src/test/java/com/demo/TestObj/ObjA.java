package com.demo.TestObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A对象
 *
 * @author MixJade
 * @since 2024-02-22
 */
@Data
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
public class ObjA {
    private long aId;
    private String name;
    private int age;
}
