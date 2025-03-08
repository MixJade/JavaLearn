package com.demo.TestObj;

import lombok.Data;

/**
 * B对象
 *
 * @author MixJade
 * @since 2024-02-22
 */
@Data
public class ObjB {
    private Long bId;
    // ObjA中的aId是long(复制时会自动赋值)
    private Long aId;
    private String name;
    // ObjA中的age是int(复制时不会获取)
    private String age;
    private String notes;
}
