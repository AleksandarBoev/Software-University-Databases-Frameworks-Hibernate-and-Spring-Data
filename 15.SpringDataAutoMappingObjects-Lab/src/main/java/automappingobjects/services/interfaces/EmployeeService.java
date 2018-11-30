package automappingobjects.services.interfaces;

import automappingobjects.domain.dtos.EmployeeDto;
import automappingobjects.domain.dtos.EmployeeDtoTask3;
import automappingobjects.domain.entities.Employee;
import automappingobjects.repositories.EmployeeRepository;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, EmployeeRepository> {
    EmployeeDto getEmployeeBasicInfoById(Long id);

    List<EmployeeDto> getEmployeesBasicInfo();

    List<EmployeeDtoTask3> getEmployeesBasicInfoByBirthYearBefore(Integer year);
}
