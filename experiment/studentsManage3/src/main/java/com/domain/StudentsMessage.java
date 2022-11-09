package com.domain;

public class StudentsMessage {
    int id;
    String studentName;
    int sex;
    Integer englishGrade;
    Integer mathGrade;
    String societyName;
    double height;
    String birthday;
    Integer money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSex() {
        return sex == 1 ? "男" : "女";
    }

    public void setSex(int sex) {
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

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "StudentsMessage{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", sex=" + sex +
                ", englishGrade=" + englishGrade +
                ", mathGrade=" + mathGrade +
                ", societyName='" + societyName + '\'' +
                ", height=" + height +
                ", birthday='" + birthday + '\'' +
                ", money=" + money +
                '}';
    }
}
