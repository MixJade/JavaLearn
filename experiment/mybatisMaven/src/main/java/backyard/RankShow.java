package backyard;

public class RankShow {
    int id;
    String studentName;
    Integer englishGrade;
    Integer mathGrade;

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEnglishGrade(Integer englishGrade) {
        this.englishGrade = englishGrade;
    }

    public void setMathGrade(Integer mathGrade) {
        this.mathGrade = mathGrade;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "id：" + id +
                ", 姓名：'" + studentName + '\'' +
                ", 英语：" + englishGrade +
                ", 数学：" + mathGrade +
                "}\n";
    }
}
