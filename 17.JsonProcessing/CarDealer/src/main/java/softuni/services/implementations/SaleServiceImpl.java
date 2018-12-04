package softuni.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.entities.Car;
import softuni.domain.entities.Customer;
import softuni.domain.entities.Sale;
import softuni.repositories.CarRepository;
import softuni.repositories.CustomerRepository;
import softuni.repositories.SaleRepository;
import softuni.services.interfaces.SaleService;

import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private CustomerRepository customerRepository;
    private CarRepository carRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
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
}
