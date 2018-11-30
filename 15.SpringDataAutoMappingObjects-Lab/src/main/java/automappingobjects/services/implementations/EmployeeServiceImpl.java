package automappingobjects.services.implementations;

import automappingobjects.domain.dtos.EmployeeDto;
import automappingobjects.domain.entities.Employee;
import automappingobjects.repositories.EmployeeRepository;
import automappingobjects.services.interfaces.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
