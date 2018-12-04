package softuni.services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.seed_dtos.CustomerSeedDto;
import softuni.domain.entities.Customer;
import softuni.repositories.CustomerRepository;
import softuni.services.interfaces.CustomerService;
import softuni.utils.interfaces.ValidatorUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidatorUtil<CustomerSeedDto> validatorUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil<CustomerSeedDto> validatorUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
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
}
