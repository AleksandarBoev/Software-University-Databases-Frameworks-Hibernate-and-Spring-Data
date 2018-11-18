package p03_university_system.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Entity
@Table(name = "courses")
public class Course extends BaseId {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer credits;
    private Teacher teacher;
    private List<Student> studentsList;

    public Course() {
        this.studentsList = new ArrayList<>();
    }

    public Course(String name, String description, Date startDate, Date endDate, Integer credits, Teacher teacher) {
        this();
        this.setName(name);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setCredits(credits);
        this.setTeacher(teacher);
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "start_date")
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column
    public Integer getCredits() {
        return this.credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @ManyToMany
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    public List<Student> getStudentsList() {
        return this.studentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public String toString() {
        String students = String.join(", ", this.studentsList
                .stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.toList()));

        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", credits=" + credits +
                ", teacher=" + teacher +
                ", studentsList=" + students +
                '}';
    }
}
