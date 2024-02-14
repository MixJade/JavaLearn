package pojo;

public class PageBirth {
    String studentName;//学生姓名
    Integer societyId;//社团id
    int currentPage1;//当前页码
    int pageItem;//一页最大条目数

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Integer societyId) {
        this.societyId = societyId;
    }

    public int getCurrentPage1() {
        return currentPage1;
    }

    public void setCurrentPage1(int currentPage1) {
        this.currentPage1 = currentPage1;
    }

    public int getPageItem() {
        return pageItem;
    }

    public void setPageItem(int pageItem) {
        this.pageItem = pageItem;
    }
}
