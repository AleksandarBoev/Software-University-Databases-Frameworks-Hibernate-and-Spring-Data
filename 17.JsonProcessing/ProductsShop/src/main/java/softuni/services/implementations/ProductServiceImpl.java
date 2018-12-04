package softuni.services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.query_dtos.Query1ProductDto;
import softuni.domain.dtos.seed_dtos.ProductSeedDto;
import softuni.domain.entities.Category;
import softuni.domain.entities.Product;
import softuni.domain.entities.User;
import softuni.repositories.CategoryRepository;
import softuni.repositories.ProductRepository;
import softuni.repositories.UserRepository;
import softuni.services.interfaces.ProductService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String seedProducts(String json) {
        StringBuilder result = new StringBuilder();

        Gson gson = new Gson();
        ProductSeedDto[] productSeedDtos = gson.fromJson(json, ProductSeedDto[].class);

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ProductSeedDto, Product> typeMap =
                modelMapper.createTypeMap(ProductSeedDto.class, Product.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(Product::setId);
            mapper.skip(Product::setCategories);
        });

        List<Product> productsToBePersisted = new ArrayList<>();
        List<User> allUsers = this.userRepository.findAll();
        List<Category> allCategories = this.categoryRepository.findAll();

        Random random = new Random();
        int numberOfUsers = allUsers.size();
        int numberOfCategories = allCategories.size();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            User randomBuyer = null;
            User randomSeller = null;
            do { //make sure the buyer and seller are different people
                randomBuyer = allUsers.get(random.nextInt(numberOfUsers));
                randomSeller = allUsers.get(random.nextInt(numberOfUsers));
            } while (randomBuyer.getId().compareTo(randomSeller.getId()) == 0);

            productSeedDto.setBuyer(randomBuyer);
            productSeedDto.setSeller(randomSeller); //this is done before the validation, because a product NEEDS a seller

            Set<ConstraintViolation<ProductSeedDto>> errorSet = validator.validate(productSeedDto);

            if (errorSet.isEmpty()) {
                List<Category> categories = new ArrayList<>();
                categories.add(allCategories.get(random.nextInt(numberOfCategories)));


                result.append(productSeedDto.toString()).append(" | Successfully added to repository!")
                        .append(System.lineSeparator());

                Product product = typeMap.map(productSeedDto);
                product.setCategories(categories);

                productsToBePersisted.add(product);
            } else {
                result.append(productSeedDto.toString()).append(" | Not added to repository. Reasons: ")
                        .append(String.join(" | ", errorSet.stream().map(e -> e.getMessage()).collect(Collectors.toList())))
                        .append(System.lineSeparator());
            }
        }

        int numberOfProductsToBePersisted = productsToBePersisted.size();

        this.productRepository.saveAll(productsToBePersisted);

        //Remove the buyer from 20 random products.
        for (int i = 0; i < 20; i++) {
            Product product = productsToBePersisted.get(random.nextInt(numberOfProductsToBePersisted));
            product.setBuyer(null);
            this.productRepository.save(product);
        }
        return result.toString().trim();
    }

    @Override
    public void saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscRelativePath(String price1, String price2, String relativePath) throws IOException {
        String jsonResult = this.getQuery1Json(price1, price2);

        File file = new ClassPathResource(relativePath).getFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonResult);
        fileWriter.close();
    }

    @Override
    public void saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscFullPath(String price1, String price2, String fullPath) throws IOException {
        String jsonResult = this.getQuery1Json(price1, price2);

        FileWriter fileWriter = new FileWriter(fullPath);
        fileWriter.write(jsonResult);
        fileWriter.close();
    }

    private String getQuery1Json(String price1, String price2) {
        BigDecimal price1Value = new BigDecimal(price1);
        BigDecimal price2Value = new BigDecimal(price2);

        List<Object[]> products = this.productRepository
                .findProductsByPriceBetweenWithoutBuyerOrderedByPriceAsc(price1Value, price2Value);

        Query1ProductDto[] query1ProductDtos = products.stream().map(p -> {
            String productName = "" + p[0];
            BigDecimal producPrice = new BigDecimal("" + p[1]);
            String sellerFullName = "" + p[2];
            return new Query1ProductDto(productName, producPrice, sellerFullName);
        }).toArray(n -> new Query1ProductDto[n]);


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(query1ProductDtos);
    }
}
