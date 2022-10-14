package backyard;

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

    public void setMessage(String studentName, int sex, Integer englishGrade, Integer mathGrade, double height, String birthday, Integer money) {
        this.studentName = studentName;
        this.sex = (sex == 1);
        this.englishGrade = englishGrade;
        this.mathGrade = mathGrade;
        this.height = height;
        this.birthday = birthday;
        this.money = money;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEnglishGrade(Integer englishGrade) {
        this.englishGrade = englishGrade;
    }

    public void setMathGrade(Integer mathGrade) {
        this.mathGrade = mathGrade;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id：" + id +
                ", 姓名：'" + studentName + '\'' +
                ", " + (sex ? "男" : "女") +
                ", 英语：" + englishGrade +
                ", 数学：" + mathGrade +
                ", 社团：" + societyName +
                ", 身高：" + height +
                ", 生日：'" + birthday + '\'' +
                ", 钱：" + money +
                "}\n";
    }
}
