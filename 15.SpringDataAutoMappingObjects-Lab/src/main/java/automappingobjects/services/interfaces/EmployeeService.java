package automappingobjects.services.interfaces;

import automappingobjects.domain.dtos.EmployeeDto;
import automappingobjects.domain.entities.Employee;
import automappingobjects.repositories.EmployeeRepository;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, EmployeeRepository> {
    EmployeeDto getEmployeeBasicInfoById(Long id);

    List<EmployeeDto> getEmployeesBasicInfo();
}
