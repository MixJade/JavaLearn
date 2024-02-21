package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 狗
 *
 * @author MixJade
 * @since 2024-02-21
 */
@Data
public class Dog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 狗的主键
     */
    @TableId(value = "dog_id", type = IdType.AUTO)
    private Integer dogId;

    /**
     * 狗的名字
     */
    private String dogName;

    /**
     * 性别，1公0母
     */
    private Boolean sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 品种
     */
    private String breed;

    /**
     * 特质列表
     */
    @TableField(exist = false) // 忽略它 他不是表字段
    private List<DogSpeciality> dogSpecialities;
}
