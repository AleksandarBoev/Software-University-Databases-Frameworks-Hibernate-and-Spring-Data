package alararestaurant.service;

import alararestaurant.constants.ImportConstants;
import alararestaurant.domain.dtos.import_dtos.employees.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private FileUtil fileUtil;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.positionRepository = positionRepository;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        String relativePath = "files/employees.json";
        return fileUtil.readFile(relativePath);
    }

    @Override
    public String importEmployees(String employees) {
        //•	If any validation errors occur (such as if their name or
        // position are too long/short or their age is out of range) proceed
        // as described above
        //•	If a position doesn’t exist yet (and the position and rest of
        // employee data is valid), create it.
        //•	If an employee is invalid, do not import their position.
        String employeeJson = employees;
        EmployeeImportDto[] employeeImportDtos =
                this.gson.fromJson(employeeJson, EmployeeImportDto[].class);

        StringBuilder resultFromImport = new StringBuilder();

        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {
            if (!this.validationUtil.isValid(employeeImportDto)) {
                resultFromImport.append(ImportConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }
            Position position =
                    this.positionRepository.findPositionByName(employeeImportDto.getPosition());

            if (position == null) {
                position = new Position();
                position.setName(employeeImportDto.getPosition());
                this.positionRepository.saveAndFlush(position); //automatically gives "position" object an id
            }

            Employee employee = new Employee();
            employee.setAge(employeeImportDto.getAge());
            employee.setName(employeeImportDto.getName());
            employee.setPosition(position);
            this.employeeRepository.saveAndFlush(employee);

            resultFromImport.append(
                    String.format(ImportConstants.SUCCESSFUL_IMPORT, employee.getName()))
                    .append(System.lineSeparator());
        }

        return resultFromImport.toString().trim();
    }
}
