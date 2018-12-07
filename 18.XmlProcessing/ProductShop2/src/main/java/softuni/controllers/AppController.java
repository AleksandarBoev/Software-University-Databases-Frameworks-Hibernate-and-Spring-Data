package softuni.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.services.interfaces.CategoryService;
import softuni.services.interfaces.ProductService;
import softuni.services.interfaces.UserService;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class AppController implements CommandLineRunner {
    private static final String FULL_FILE_PATH_IMPORT_FOLDER = "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\\18.XmlProcessing\\ProductShop2\\src\\main\\resources\\import_files\\";
    private static final String RELATIVE_FILE_PATH_IMPORT_FOLDER = "import_files/";
    private static final String FULL_FILE_PATH_EXPORT_FOLDER = "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\\18.XmlProcessing\\ProductShop2\\src\\main\\resources\\export_files\\";

    private UserService userService;
    private CategoryService categoryService;
    private ProductService productService;

    private BufferedReader consoleReader;

    @Autowired
    public AppController(UserService userService, CategoryService categoryService, ProductService productService, BufferedReader consoleReader) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.consoleReader = consoleReader;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedData();
//        this.query01ProductsInRange();
//        this.query02SuccessfullySoldProducts();
//        this.query03CategoriesByProductsCount();
        this.query04UsersAndProducts();
    }


    private void query01ProductsInRange() throws JAXBException {
        BigDecimal price1 = new BigDecimal("500");
        BigDecimal price2 = new BigDecimal("1000");

        String exportFullFilePath =FULL_FILE_PATH_EXPORT_FOLDER + "query01.xml";
        this.productService.exportProductsWithPriceBetweenWithNoBuyerOrderedByPriceAsc(exportFullFilePath, price1, price2);
    }

    private void query02SuccessfullySoldProducts() throws JAXBException {
        String exportFullFilePath = FULL_FILE_PATH_EXPORT_FOLDER + "query02.xml";
        this.userService.exportUsersWhoHaveSoldAProduct(exportFullFilePath);
    }

    private void query03CategoriesByProductsCount() throws JAXBException {
        String exportFullFulePath = FULL_FILE_PATH_EXPORT_FOLDER + "query03.xml";
        this.categoryService.exportCategoriesNamesProductsCountAveragePriceAndTotalRevenue(exportFullFulePath);
    }

    private void query04UsersAndProducts() throws JAXBException {
        String fullFilePath = FULL_FILE_PATH_EXPORT_FOLDER + "query04.xml";
        this.userService.exportUsersAndProductsOrderedByProductsCountDescLastNameAsc(fullFilePath);
    }

    private void seedUsers() throws IOException, JAXBException {
        String relativeFilePath = RELATIVE_FILE_PATH_IMPORT_FOLDER + "users.xml";
        System.out.println(this.userService.seedDataFromXmlFile(relativeFilePath));
    }

    private void seedCategories() throws IOException, JAXBException {
        String relativePath = RELATIVE_FILE_PATH_IMPORT_FOLDER + "categories.xml";
        System.out.println(this.categoryService.seedDataFromXmlFile(relativePath));
    }

    private void seedProducts() throws IOException, JAXBException {
        String relativeFilePath = RELATIVE_FILE_PATH_IMPORT_FOLDER + "products.xml";
        System.out.println(this.productService.seedDataFromXmlFile(relativeFilePath));
    }

    private void seedData() throws IOException, JAXBException {
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();
    }
}
