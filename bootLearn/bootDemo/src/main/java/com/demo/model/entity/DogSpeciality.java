package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 狗的特质
 *
 * @author MixJade
 * @since 2024-02-21
 */
@Data
@TableName("dog_speciality")
public class DogSpeciality implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 狗的特质主键
     */
    @TableId(value = "speciality_id", type = IdType.AUTO)
    private Integer specialityId;

    /**
     * 狗的特质
     */
    private String specialityName;

    /**
     * 狗的主键
     */
    private Integer dogId;
}
