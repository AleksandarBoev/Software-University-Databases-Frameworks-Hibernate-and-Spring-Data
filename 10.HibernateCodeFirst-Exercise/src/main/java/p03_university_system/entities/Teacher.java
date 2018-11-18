package p03_university_system.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Table(name = "teachers")
public class Teacher extends BasePerson {
    private String email;
    private BigDecimal salaryPerHour;
    private List<Course> courses;

    public Teacher() {
        this.courses = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName, String phoneNumber, String email, BigDecimal salaryPerHour) {
        super(firstName, lastName, phoneNumber);
        this.courses = new ArrayList<>();
        this.setEmail(email);
        this.setSalaryPerHour(salaryPerHour);
    }

    @Column
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour", columnDefinition = "DECIMAL(9, 2)")
    public BigDecimal getSalaryPerHour() {
        return this.salaryPerHour;
    }

    public void setSalaryPerHour(BigDecimal salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @OneToMany(mappedBy = "teacher")
    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
