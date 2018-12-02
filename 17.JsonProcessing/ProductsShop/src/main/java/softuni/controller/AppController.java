package softuni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.services.interfaces.CategoryService;
import softuni.services.interfaces.ProductService;
import softuni.services.interfaces.UserService;
import softuni.utils.interfaces.FileReadUtil;

@Controller
public class AppController implements CommandLineRunner {
    private static final String RELATIVE_PATH = "json_seed_files/";

    private static final String FILE_NAME_CARS = "cars.json";
    private static final String FILE_NAME_CATEGORIES = "categories.json";
    private static final String FILE_NAME_CUSTOMERS = "customers.json";
    private static final String FILE_NAME_PARTS = "parts.json";
    private static final String FILE_NAME_PRODUCTS = "products.json";
    private static final String FILE_NAME_SUPPLIERS = "suppliers.json";
    private static final String FILE_NAME_USERS= "users.json";

    //services, utils
    private FileReadUtil fileReadUtil;
    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public AppController(FileReadUtil fileReadUtil, CategoryService categoryService, ProductService productService, UserService userService) {
        this.fileReadUtil = fileReadUtil;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_PATH + FILE_NAME_USERS));
    }

    private void seedData() {
        String categoriesJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_PATH + FILE_NAME_CATEGORIES);
        String usersJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_PATH + FILE_NAME_USERS);
        String productsJson =
                this.fileReadUtil.readFileFromRelativeFilePath(RELATIVE_PATH + FILE_NAME_PRODUCTS);

        this.categoryService.seedCategories(categoriesJson);
        this.userService.seedUsers(usersJson);
        this.productService.seedProducts(productsJson);
    }
}
