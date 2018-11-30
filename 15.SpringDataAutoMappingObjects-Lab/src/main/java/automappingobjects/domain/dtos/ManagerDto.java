package automappingobjects.domain.dtos;

import java.util.List;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;
    private int countOfEmployees;

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

    public List<EmployeeDto> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public int getCountOfEmployees() {
        return this.countOfEmployees;
    }

    public void setCountOfEmployees(int countOfEmployees) {
        this.countOfEmployees = countOfEmployees;
    }

    //Steve Jobbsen | Employees: 2
//    - Stephen Bjorn 4300.00
//    - Kirilyc Lefi 4400.00


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.firstName).append(" ").append(this.lastName)
                .append(" | Employees: ").append(this.countOfEmployees)
                .append(System.lineSeparator());

        for (EmployeeDto employee : this.employees)
            result.append("\t- ").append(employee.taskToString()).append(System.lineSeparator());

        return result.toString().trim();
    }
}
