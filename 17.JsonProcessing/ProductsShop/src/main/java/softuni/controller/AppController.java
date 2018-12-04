package softuni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.services.interfaces.CategoryService;
import softuni.services.interfaces.ProductService;
import softuni.services.interfaces.UserService;
import softuni.utils.interfaces.FileReadUtil;
import softuni.utils.interfaces.FileWriteUtil;

import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {
    private static final String RELATIVE_SEED_PATH = "json_seed_files/"; //this works
    private static final String RELATIVE_WRITE_PATH = "json_query_tasks/"; //but this does not for some reason
    private static final String FULL_WRITE_PATH = "C:\\AleksandarUser\\Programming\\GitHubRepositories\\Software-University-Databases-Frameworks-Hibernate-and-Spring-Data\\17.JsonProcessing\\ProductsShop\\src\\main\\resources\\json_query_tasks\\";

    private static final String FILE_NAME_CATEGORIES = "categories.json";
    private static final String FILE_NAME_PRODUCTS = "products.json";
    private static final String FILE_NAME_USERS= "users.json";

    //services, utils
    private FileReadUtil fileReadUtil;
    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;
    private BufferedReader reader;
    private FileWriteUtil fileWriteUtil;

    @Autowired
    public AppController(FileReadUtil fileReadUtil, CategoryService categoryService, ProductService productService, UserService userService, BufferedReader reader, FileWriteUtil fileWriteUtil) {
        this.fileReadUtil = fileReadUtil;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.reader = reader;
        this.fileWriteUtil = fileWriteUtil;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedData();
//        this.query01ProductsInRange();
//        this.query02SuccessfullySoldProducts();
//        this.query03CategoriesByProductsCount();
        this.query04UsersAndProducts();
    }

    private void query01ProductsInRange() throws IOException {
        String price1 = "500";
        String price2 = "1000";

        String fullPath = FULL_WRITE_PATH + "query01.json";
        String relativePath = RELATIVE_WRITE_PATH + "query01.json";

        this.productService.saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscFullPath(price1, price2, fullPath);
        //TODO the relative path way of doing the task doesn't work. It finds the file, but does nothing.
//        this.productService.saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscRelativePath(price1, price2, relativePath);
    }

    private void query02SuccessfullySoldProducts() throws IOException {
        String json = this.userService.getJsonUsersWithAtLeastOneSoldItem();
        String fullFilePath = FULL_WRITE_PATH + "query02.json";
        this.fileWriteUtil.writeToFile(fullFilePath, json);
    }

    private void query03CategoriesByProductsCount() throws IOException {
        String json = this.categoryService.getJsonCategoriesNameProductsCountAveragePriceTotalRevenue();
        String fullFilePath = FULL_WRITE_PATH + "query03.json";
        this.fileWriteUtil.writeToFile(fullFilePath, json);
    }

    private void query04UsersAndProducts() throws IOException {
        String json = this.userService.getJsonProductSoldOrderByProductsSoldDescLastNameAsc();
        String fullFilePath = FULL_WRITE_PATH + "query04.json";
        this.fileWriteUtil.writeToFile(fullFilePath, json);
    }

    private void seedData() {
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();
    }

    private void seedUsers() {
        String usersJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_SEED_PATH + FILE_NAME_USERS);
        System.out.println(this.userService.seedUsers(usersJson));
    }

    private void seedProducts() {
        String productsJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_SEED_PATH + FILE_NAME_PRODUCTS);
        System.out.println(this.productService.seedProducts(productsJson));
    }

    private void seedCategories() {
        String categoriesJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_SEED_PATH + FILE_NAME_CATEGORIES);
        System.out.println(this.categoryService.seedCategories(categoriesJson));
    }
}
