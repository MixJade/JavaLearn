package pojo;

public class StudentsMessage {
    int id;
    String studentName;
    boolean sex;
    Integer englishGrade;
    Integer mathGrade;
    String societyName;
    double height;
    String birthday;
    Integer money;

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public boolean isSex() {
        return sex;
    }

    public Integer getEnglishGrade() {
        return englishGrade;
    }

    public Integer getMathGrade() {
        return mathGrade;
    }

    public String getSocietyName() {
        return societyName;
    }

    public double getHeight() {
        return height;
    }

    public String getBirthday() {
        return birthday;
    }

    public Integer getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "Student{" + "id：" + id + ", 姓名：'" + studentName + '\'' + ", " + (sex ? "男" : "女") + ", 英语：" + englishGrade + ", 数学：" + mathGrade + ", 社团：" + societyName + ", 身高：" + height + ", 生日：'" + birthday + '\'' + ", 钱：" + money + "}\n";
    }
}
