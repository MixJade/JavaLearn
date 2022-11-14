package com.demo.domain;

public class Students {
    String studentName, birthday;
    Integer id, sex, englishGrade, mathGrade, societyId, money;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setMessage(String studentName, String sex, String englishGrade, String mathGrade, String societyId, String height, String birthday, String money) {
        this.studentName = studentName;
        this.sex = Integer.parseInt(sex);
        this.englishGrade = Integer.parseInt(englishGrade);
        this.mathGrade = Integer.parseInt(mathGrade);
        this.societyId = Integer.parseInt(societyId);
        this.height = Double.parseDouble(height);
        this.birthday = birthday;
        this.money = Integer.valueOf(money);
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentName='" + studentName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", id=" + id +
                ", sex=" + sex +
                ", englishGrade=" + englishGrade +
                ", mathGrade=" + mathGrade +
                ", societyId=" + societyId +
                ", money=" + money +
                ", height=" + height +
                '}';
    }
}
