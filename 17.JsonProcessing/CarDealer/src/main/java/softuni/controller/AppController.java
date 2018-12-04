package softuni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.services.interfaces.*;
import softuni.utils.interfaces.FileReaderUtil;

import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {
    private static final String RELATIVE_SEED_PATH = "seed_json/";

    private FileReaderUtil fileReaderUtil;

    private SupplierService supplierService;
    private PartService partService;
    private CarService carService;
    private CustomerService customerService;
    private SaleService saleService;

    @Autowired
    public AppController(FileReaderUtil fileReaderUtil, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.fileReaderUtil = fileReaderUtil;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();
    }

    //TODO think of a way of making a BaseServiceImpl, which does part of the work for seeding data.
    private void seedSuppliers() throws IOException {
        String relativePath = RELATIVE_SEED_PATH + "suppliers.json";
        String suppliersSeedInfo = this.fileReaderUtil.readFile(relativePath);

        System.out.println(this.supplierService.seedSuppliers(suppliersSeedInfo));
    }

    private void seedParts() throws IOException {
        String relativePath = RELATIVE_SEED_PATH + "parts.json";
        String partsSeedInfo = this.fileReaderUtil.readFile(relativePath);

        System.out.println(this.partService.seedParts(partsSeedInfo));
    }

    private void seedCars() throws IOException {
        String relativePath = RELATIVE_SEED_PATH + "cars.json";
        String carsSeedInfo = this.fileReaderUtil.readFile(relativePath);

        System.out.println(this.carService.seedCars(carsSeedInfo));
    }

    private void seedCustomers() throws IOException {
        String relativePath = RELATIVE_SEED_PATH + "customers.json";
        String customersSeedInfo = this.fileReaderUtil.readFile(relativePath);

        System.out.println(this.customerService.seedCustomers(customersSeedInfo));
    }

    private void seedSales() {
        System.out.println(this.saleService.seedRandomSales());
    }

    private void seedData() throws IOException {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
    }

}
