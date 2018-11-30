package automappingobjects.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthDate;
    private Address address;
    private Boolean isOnHoliday;
    private Employee manager;
    private List<Employee> inChargeOf;

    public Employee() {
        this.inChargeOf = new ArrayList<>();
    }

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthDate, Address address) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthDate = birthDate;
        this.address = address;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Column(name = "birth_date")
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "on_holiday", columnDefinition = "BIT DEFAULT FALSE")
    public Boolean getOnHoliday() {
        return this.isOnHoliday;
    }

    public void setOnHoliday(Boolean onHoliday) {
        isOnHoliday = onHoliday;
    }

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    public Employee getManager() {
        return this.manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager", cascade = CascadeType.ALL)
    public List<Employee> getInChargeOf() {
        return this.inChargeOf;
    }

    public void setInChargeOf(List<Employee> inChargeOf) {
        this.inChargeOf = inChargeOf;
    }

    @Override
    public String toString() {
        StringBuilder inChargeOf = new StringBuilder();
        this.inChargeOf.forEach(e -> inChargeOf.append("\t").append(String.format("%s %s%n",
                e.firstName, e.lastName)));

        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", inChargeOf:\n" + "\t" + inChargeOf.toString().trim() + //trim removes the first \t
                '}';
    }
}
