package com.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;


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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getEnglishGrade() {
        return englishGrade;
    }

    public void setEnglishGrade(Integer englishGrade) {
        this.englishGrade = englishGrade;
    }

    public Integer getMathGrade() {
        return mathGrade;
    }

    public void setMathGrade(Integer mathGrade) {
        this.mathGrade = mathGrade;
    }

    public Integer getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Integer societyId) {
        this.societyId = societyId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
