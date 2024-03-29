package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class Students {
    @TableField("studentName")
    String studentName;
    String birthday;
    @TableId(type = IdType.AUTO)
    Long id;
    Integer sex;
    @TableField("englishGrade")
    Integer englishGrade;
    @TableField("mathGrade")
    Integer mathGrade;
    @TableField("societyId")
    Integer societyId;
    Integer money;
    double height;
}
