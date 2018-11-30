package automappingobjects.services.implementations;

import automappingobjects.domain.dtos.EmployeeDto;
import automappingobjects.domain.dtos.EmployeeDtoTask3;
import automappingobjects.domain.entities.Employee;
import automappingobjects.repositories.EmployeeRepository;
import automappingobjects.services.interfaces.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, EmployeeRepository> implements EmployeeService {
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        super(employeeRepository);
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto getEmployeeBasicInfoById(Long id) {
        return this.modelMapper.map(super.getById(id), EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getEmployeesBasicInfo() {
        List<EmployeeDto> result = super.getAll()
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());

        this.modelMapper.validate();

        return result;
    }

    @Override
    public List<EmployeeDtoTask3> getEmployeesBasicInfoByBirthYearBefore(Integer year) {
        List<Employee> employeesBornBeforeGivenYear = super.getRepository().findEmployeesByBirthDateBefore(year);

        TypeMap<Employee, EmployeeDtoTask3> employeeDtoTask3TypeMap =
                this.modelMapper.createTypeMap(Employee.class, EmployeeDtoTask3.class);

        employeeDtoTask3TypeMap.addMappings(mapper -> {
            mapper.map(src -> src.getManager().getLastName(), EmployeeDtoTask3::setManagersLastName);
        });
        employeeDtoTask3TypeMap.validate();

        List<EmployeeDtoTask3> result = new ArrayList<>();
        for (Employee employee : employeesBornBeforeGivenYear)
            result.add(employeeDtoTask3TypeMap.map(employee));

        return result;
    }
}
