package automappingobjects.domain.dtos;

import java.math.BigDecimal;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String addressCity;

    public EmployeeDto() {
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

    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                ", salary = " + this.salary +
                ", address city = '" + this.addressCity +
                "'}";
    }

    public String taskToString() {
        return String.format("%s %s %.2f", this.firstName, this.lastName, this.salary);
    }
}
