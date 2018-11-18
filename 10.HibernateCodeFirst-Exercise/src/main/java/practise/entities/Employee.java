package practise.entities;

import javax.persistence.*;

//@Entity
@Table
public class Employee extends BaseId {
    private String name;
    private Department department;
    private Address address;

    public Employee() {
    }

    public Employee(String name, Department department, Address address) {
        this.name = name;
        this.department = department;
        this.address = address;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
