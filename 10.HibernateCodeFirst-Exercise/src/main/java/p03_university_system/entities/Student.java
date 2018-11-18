package p03_university_system.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Table(name = "students")
public class Student extends BasePerson {
    private Double averageGrade;
    private Double attendancePercentage;
    private List<Course> courseList;

    public Student() {
        this.courseList = new ArrayList<>();
    }

    public Student(String firstName, String lastName, String phoneNumber, Double averageGrade, Double attendancePercentage) {
        super(firstName, lastName, phoneNumber);
        this.courseList = new ArrayList<>();
        this.setAverageGrade(averageGrade);
        this.setAttendancePercentage(attendancePercentage);
    }

    @Column(name = "average_grade")
    public Double getAverageGrade() {
        return this.averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Column(name = "attendance_percentage")
    public Double getAttendancePercentage() {
        return this.attendancePercentage;
    }

    public void setAttendancePercentage(Double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    @ManyToMany(mappedBy = "studentsList")
    public List<Course> getCourseList() {
        return this.courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
