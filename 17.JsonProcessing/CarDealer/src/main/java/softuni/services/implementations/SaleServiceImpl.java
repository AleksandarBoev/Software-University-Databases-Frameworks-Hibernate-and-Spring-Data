package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.export_dtos.Query4CarDto;
import softuni.domain.dtos.export_dtos.Query6CarSaleDto;
import softuni.domain.entities.Car;
import softuni.domain.entities.Customer;
import softuni.domain.entities.Part;
import softuni.domain.entities.Sale;
import softuni.repositories.CarRepository;
import softuni.repositories.CustomerRepository;
import softuni.repositories.SaleRepository;
import softuni.services.interfaces.SaleService;
import softuni.utils.interfaces.FileWriterUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private CustomerRepository customerRepository;
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private FileWriterUtil fileWriterUtil;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository, CarRepository carRepository, ModelMapper modelMapper, Gson gson, FileWriterUtil fileWriterUtil) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileWriterUtil = fileWriterUtil;
    }

    @Override
    public String seedRandomSales() {
        List<Car> allCars = this.carRepository.findAll();
        List<Customer> allCustomers = this.customerRepository.findAll();
        Double[] discounts = new Double[]{0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};

        int numberOfCars = allCars.size();
        int numberOfCustomers = allCustomers.size();

        Random random = new Random();

        StringBuilder resultFromSeed = new StringBuilder();

        for (int i = 0; i < 300; i++) {
            Car randomCar = allCars.get(random.nextInt(numberOfCars));
            Customer randomCustomer = allCustomers.get(random.nextInt(numberOfCustomers));
            Double randomDiscount = discounts[random.nextInt(discounts.length)];

            Sale sale = new Sale();
            sale.setCar(randomCar);
            sale.setCustomer(randomCustomer);
            sale.setDiscount(randomDiscount);

            this.saleRepository.save(sale);
            resultFromSeed.append("Sale added: ").append(sale.toString()).append(System.lineSeparator());
        }

        return resultFromSeed.toString().trim();
    }

    @Override
    public void exportAllSales(String fullFilePath) throws IOException {

        TypeMap<Car, Query4CarDto> carTypeMap =
                this.modelMapper.createTypeMap(Car.class, Query4CarDto.class);
        carTypeMap.validate();

        Converter<Car, BigDecimal> carPriceConverter = context -> {
            BigDecimal totalMoney = BigDecimal.ZERO;

            for (Part part : context.getSource().getParts()) {
                totalMoney = totalMoney.add(part.getPrice());
            }

            return totalMoney;
        };

        Converter<Car, Query4CarDto> carConverter = context -> carTypeMap.map(context.getSource());
        Converter<Customer, String> customerNameConverter = mappingContext -> mappingContext.getSource().getName();

        TypeMap<Sale, Query6CarSaleDto> saleToDtoTypeMap =
                this.modelMapper.createTypeMap(Sale.class, Query6CarSaleDto.class);
        saleToDtoTypeMap.addMappings(mapper -> {
            mapper.using(carConverter).map(Sale::getCar, Query6CarSaleDto::setCar);
            mapper.using(customerNameConverter).map(Sale::getCustomer, Query6CarSaleDto::setCustomerName);
            mapper.using(carPriceConverter).map(Sale::getCar, Query6CarSaleDto::setPrice);
            //setPriceWithDiscount has been modified. Probably bad practise
            mapper.using(carPriceConverter).map(Sale::getCar, Query6CarSaleDto::setPriceWithDiscount);
        });
        saleToDtoTypeMap.validate();

        List<Sale> allSales = this.saleRepository.findAll();
        Query6CarSaleDto[] query6CarSaleDtos = allSales
                .stream()
                .map(s -> saleToDtoTypeMap.map(s))
                .toArray(n -> new Query6CarSaleDto[n]);

        String jsonResult = this.gson.toJson(query6CarSaleDtos);
        this.fileWriterUtil.writeToFile(fullFilePath, jsonResult);
    }
}
