package pojo;

public record StudentsMessage(int id,
                              String studentName,
                              int sex,
                              Integer englishGrade,
                              Integer mathGrade,
                              String societyName,
                              double height,
                              String birthday,
                              Integer money) {
}