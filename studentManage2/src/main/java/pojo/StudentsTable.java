package pojo;

public class StudentsTable {
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
