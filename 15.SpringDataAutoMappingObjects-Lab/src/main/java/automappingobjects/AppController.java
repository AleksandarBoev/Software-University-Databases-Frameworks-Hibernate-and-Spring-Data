package automappingobjects;

import automappingobjects.domain.dtos.EmployeeDto;
import automappingobjects.domain.dtos.ManagerDto;
import automappingobjects.domain.entities.Address;
import automappingobjects.domain.entities.Employee;
import automappingobjects.services.interfaces.AddressService;
import automappingobjects.services.interfaces.EmployeeService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private BufferedReader reader;
    private Scanner scanner;
    private EmployeeService employeeService;
    private AddressService addressService;

    @Autowired
    public AppController(BufferedReader reader, Scanner scanner, EmployeeService employeeService, AddressService addressService) {
        this.reader = reader;
        this.scanner = scanner;
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.problem01SimpleMapping();
        this.problem02AdvancedMapping();
    }

    private void problem01SimpleMapping() {
        Employee employee = this.employeeService.getById(1L);
        ModelMapper modelMapper = new ModelMapper();

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        System.out.println(employeeDto);
    }

    private void problem02AdvancedMapping() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Employee, EmployeeDto> employeeDtoTypeMap = modelMapper.createTypeMap(Employee.class, EmployeeDto.class);

        employeeDtoTypeMap.addMappings(mapper -> {
            mapper.skip(EmployeeDto::setAddressCity);
        });

        //Convert a List<Employee> to a List<EmployeeDto>, using a mapper from Employee to EmployeeDto on every employee.
        Converter<List<Employee>, List<EmployeeDto>> employeesDtoConverter =
                context -> {
                    List<EmployeeDto> result = new ArrayList<>();

                    for (Employee employee : context.getSource())
                        result.add(employeeDtoTypeMap.map(employee));

                    return result;
        };

        Converter<List<Employee>, Integer> employeesCountDtoConverter = context -> context.getSource().size();

        TypeMap<Employee, ManagerDto> managerDtoTypeMap = modelMapper.createTypeMap(Employee.class, ManagerDto.class);
        managerDtoTypeMap.addMappings(mapper -> {
            mapper.using(employeesDtoConverter).map(Employee::getInChargeOf, ManagerDto::setEmployees);
            mapper.using(employeesCountDtoConverter).map(Employee::getInChargeOf, ManagerDto::setCountOfEmployees);
            //firstName and lastName are the same as in Employee, so the mapper can handle them on it's own
        });

        managerDtoTypeMap.validate();

        Employee managerWithEmployees = this.employeeService.getById(1L);
        ManagerDto managerDto = managerDtoTypeMap.map(managerWithEmployees);

        System.out.println(managerDto);
    }

    private void seedData() {
        Address address1 = new Address("Sofia");
        Address address2 = new Address("Berlin");
        this.addressService.save(address1);
        this.addressService.save(address2);

        Employee employee1 = new Employee("Ivana", "Lazonova", BigDecimal.valueOf(2500.0), LocalDate.parse("1995-09-05"), address1);
        Employee employee2 = new Employee("Gergon", "Gergaev", BigDecimal.valueOf(1800.0), LocalDate.parse("1995-08-25"), address1);
        Employee employee3 = new Employee("Gergon2", "Gergaev2", BigDecimal.valueOf(1800.0), LocalDate.parse("1995-08-25"), address2);
        Employee employee4 = new Employee("Person 40", "What2", BigDecimal.valueOf(500.0), LocalDate.parse("1995-09-05"),  address2);

        this.employeeService.save(employee1);
        this.employeeService.save(employee2);
        this.employeeService.save(employee3);
        this.employeeService.save(employee4);

        employee2.setManager(employee1);
        employee3.setManager(employee1);
        employee4.setManager(employee1);

        this.employeeService.save(employee2);
        this.employeeService.save(employee3);
        this.employeeService.save(employee4);
//        this.employeeService.save(employee1); //this doesn't help

//        System.out.println(employee1); //weird how the manager has 0 employees now
//        Employee employee = this.employeeService.getById(1L); //and when he is extracted from the db
//        System.out.println(employee); //he has 3 employees
    }

    private void printBasicInfo() {
        this.employeeService.getEmployeesBasicInfo().forEach(System.out::println);
    }

    /*
        Manually doing things. Note: EVERY field in the Dto class is filled (unless "skip" is used). Even if I didn't write
    "m.map(src -> src.getFirstName(), EmployeeDto::setFirstName);", the typeMap would try to fill it in automatically.
        If there was an additional field in EmployeeDto, which I did not explicitly map and which the typeMap couldn't
    map on it's own, an error like this would pop up: "Unmapped destination properties found in TypeMap[Employee -> EmployeeDto]:
    "automappingobjects.domain.dtos.EmployeeDto.setAdditionalProperty()", where "additionalProperty" is the name
    of the additional field in the dto. BUT for the error to pop up, typeMap.validate() should be present after
    the mapping. Otherwise the program wouldn't throw an exception and the additional field would be null.
     */
    private void practiseMapping1() {
        Employee employee = this.employeeService.getById(1L);

        ModelMapper modelMapper = new ModelMapper(); //this has .validate() also
        //Map from Employee.class (source) to EmployeeDto.class (destination)
        TypeMap<Employee, EmployeeDto> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDto.class);

        typeMap.addMappings(m -> {
            m.map(Employee::getFirstName, EmployeeDto::setFirstName); //first way of doing it
            m.map(src -> src.getLastName(), (destination, value) -> destination.setFirstName("" + value)); //2nd way of doing it
            m.skip(EmployeeDto::setSalary); //this field of EmployeeDto will NOT be mapped
            m.map(src -> src.getAddress().getCity(), EmployeeDto::setAddressCity);
        });
        typeMap.validate();

        EmployeeDto employeeDto = typeMap.map(employee);
        System.out.println(employeeDto);
    }

    //Converting properties
    private void practiseModeling2() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> toUppercase = ctx -> ctx.getSource() == null ? null : ctx.getSource().toUpperCase();
        Converter<Address, String> addressToString = context -> "City: " + context.getSource().getCity(); //checking for null is good practise

        TypeMap<Employee, EmployeeDto> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDto.class)
                .addMappings(mapper -> {
                    mapper.using(toUppercase).map(src -> src.getFirstName(), (dest, value) -> dest.setFirstName("" + value));
                    mapper.using(addressToString).map(src -> src.getAddress(), (destination, value) -> destination.setAddressCity((String)value));
                });
        typeMap.validate();
        Employee employee = this.employeeService.getById(1L);

        EmployeeDto employeeDto = typeMap.map(employee);
        System.out.println(employeeDto);
    }
}
