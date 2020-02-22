package ahmet.com.pwl.Model;

public class Courses {

    private String course, teacher, startCourse, endCourse;

    public Courses() {
    }

    public Courses(String course, String teacher, String startCourse, String endCourse) {
        this.course = course;
        this.teacher = teacher;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStartCourse() {
        return startCourse;
    }

    public void setStartCourse(String startCourse) {
        this.startCourse = startCourse;
    }

    public String getEndCourse() {
        return endCourse;
    }

    public void setEndCourse(String endCourse) {
        this.endCourse = endCourse;
    }
}
