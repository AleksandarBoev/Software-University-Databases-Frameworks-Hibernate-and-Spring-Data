package automappingobjects.domain.dtos;

import java.math.BigDecimal;

public class EmployeeDtoTask3 {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String managersLastName;

    public EmployeeDtoTask3() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagersLastName() {
        return this.managersLastName;
    }

    public void setManagersLastName(String managersLastName) {
        this.managersLastName = managersLastName;
    }

    @Override
    public String toString() {
        return "EmployeeDtoTask3{" +
                "firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                ", salary = " + this.salary +
                ", managersLastName = '" + this.managersLastName + '\'' +
                '}';
    }
}
