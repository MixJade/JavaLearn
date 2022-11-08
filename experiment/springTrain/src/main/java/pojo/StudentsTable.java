package pojo;


public class StudentsTable{
    int id;
    String studentName;
    int sex, englishGrade, mathGrade, societyId;
    double height;
    String birthday;
    Integer money;

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getSex() {
        return sex;
    }

    public int getEnglishGrade() {
        return englishGrade;
    }

    public int getMathGrade() {
        return mathGrade;
    }

    public int getSocietyId() {
        return societyId;
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

    public void setId(int id) {
        this.id = id;
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
        return "StudentsTable{" + id +
                "name='" + studentName + '\'' +
                ", sex=" + sex +
                ", englishGrade=" + englishGrade +
                ", mathGrade=" + mathGrade +
                ", societyId=" + societyId +
                ", height=" + height +
                ", birthday='" + birthday + '\'' +
                ", money=" + money +
                '}';
    }
}