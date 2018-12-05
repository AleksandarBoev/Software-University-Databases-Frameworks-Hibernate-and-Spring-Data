package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.export_dtos.Query1CustomerDto;
import softuni.domain.dtos.export_dtos.Query5CustomerDto;
import softuni.domain.dtos.seed_dtos.CustomerSeedDto;
import softuni.domain.entities.Customer;
import softuni.domain.entities.Part;
import softuni.domain.entities.Sale;
import softuni.repositories.CustomerRepository;
import softuni.services.interfaces.CustomerService;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidatorUtil<CustomerSeedDto> validatorUtil;
    private FileWriterUtil fileWriterUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil<CustomerSeedDto> validatorUtil, FileWriterUtil fileWriterUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.fileWriterUtil = fileWriterUtil;
    }

    @Override
    public String seedCustomers(String customersJson) {
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(customersJson, CustomerSeedDto[].class);

        TypeMap<CustomerSeedDto, Customer> typeMap =
                this.modelMapper.createTypeMap(CustomerSeedDto.class, Customer.class);

        Converter<String, LocalDateTime> dateTimeConverter = context -> {
            LocalDateTime localDateTime = LocalDateTime.parse(context.getSource());
            return localDateTime;
        };

        typeMap.addMappings(mapper -> {
            mapper.skip(Customer::setId);
            mapper.skip(Customer::setCarsBought);
            mapper.using(dateTimeConverter).map(CustomerSeedDto::getBirthDate, Customer::setBirthDate);
        });
        typeMap.validate();

        List<Customer> validCustomers = new ArrayList<>();

        StringBuilder resultFromSeed = new StringBuilder();

        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            resultFromSeed.append(customerSeedDto.toString());

            if (!this.validatorUtil.isValid(customerSeedDto)) {
                List<String> errors = this.validatorUtil.getErrorMessages(customerSeedDto);

                resultFromSeed.append(" | Not added to database. Reasons: ")
                        .append(String.join(" | ", errors))
                        .append(System.lineSeparator());
            } else {
                validCustomers.add(typeMap.map(customerSeedDto));

                resultFromSeed.append(" | Added to database!")
                        .append(System.lineSeparator());
            }
        }

        this.customerRepository.saveAll(validCustomers);

        return resultFromSeed.toString().trim();
    }

    @Override
    public void exportOrderedCustomers(String fullFilePath) throws IOException {
        List<Customer> allCustomersOrderedByBirthDateAsc = this.customerRepository.findAll();

        //another way of sorting could be: Query1CustomDto implements Comparable<Query1CustomDto> and using the newly made compareTo method. But comparing dates would be problematic, since they are now strings
        allCustomersOrderedByBirthDateAsc = allCustomersOrderedByBirthDateAsc.stream().sorted((c1, c2) -> {
            int comparisonResult = c1.getBirthDate().compareTo(c2.getBirthDate());

            if (comparisonResult == 0)
                comparisonResult = c2.getYoungDriver().compareTo(c1.getYoungDriver());

            return comparisonResult;
        }).collect(Collectors.toList());

        TypeMap<Customer, Query1CustomerDto> typeMap =
                this.modelMapper.createTypeMap(Customer.class, Query1CustomerDto.class);

        Converter<List<Sale>, List<String>> salesConverter = context -> context.getSource().stream()
                .map(s -> {
                    String car = s.getCar().getMake() + " " + s.getCar().getModel();
                    return String.format("Car: %s, Discount: %s", car, s.getDiscount());
                }).collect(Collectors.toList());

        String dateFormat = "yyyy-MM-dd'T'hh:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        Converter<LocalDateTime, String> dateConverter = context -> dateTimeFormatter.format(context.getSource());

        typeMap.addMappings(mapper -> {
            mapper.using(salesConverter).map(Customer::getCarsBought, Query1CustomerDto::setSales);
            mapper.using(dateConverter).map(Customer::getBirthDate, Query1CustomerDto::setBirthDate);
        });
        typeMap.validate();

        Query1CustomerDto[] query1CustomerDtos = allCustomersOrderedByBirthDateAsc
                .stream()
                .map(c -> typeMap.map(c))
                .toArray(n -> new Query1CustomerDto[n]);

        String exportJson = this.gson.toJson(query1CustomerDtos);

        this.fileWriterUtil.writeToFile(fullFilePath, exportJson);
    }

    @Override
    public void exportTotalSalesByCustomer(String fullFilePath) throws IOException {
        TypeMap<Customer, Query5CustomerDto> typeMap =
                this.modelMapper.createTypeMap(Customer.class, Query5CustomerDto.class);

        Converter<List<Sale>, BigDecimal> listOfSaleToTotalMoney = context -> {
            BigDecimal totalMoney = BigDecimal.ZERO;

            for (Sale sale : context.getSource()) {
                for (Part part : sale.getCar().getParts()) {
                    totalMoney = totalMoney.add(part.getPrice());
                }
            }

            return totalMoney;
        };

        typeMap.addMappings(mapper -> {
            mapper.map(Customer::getName, Query5CustomerDto::setFullName);
            mapper.map(src -> src.getCarsBought().size(), (dest, value) -> dest.setBoughtCars((Integer) value));
            mapper.using(listOfSaleToTotalMoney).map(Customer::getCarsBought, Query5CustomerDto::setSpentMoney);
        });
        typeMap.validate();

        List<Customer> customersWithAtLeastOneBoughtCar =
                this.customerRepository.getCustomersByHavingBoughtCars();

        Query5CustomerDto[] query5CustomerDtos = customersWithAtLeastOneBoughtCar
                .stream()
                .map(cust -> typeMap.map(cust))
                .toArray(n -> new Query5CustomerDto[n]);

        String jsonResult = this.gson.toJson(query5CustomerDtos);
        this.fileWriterUtil.writeToFile(fullFilePath, jsonResult);
    }
}
