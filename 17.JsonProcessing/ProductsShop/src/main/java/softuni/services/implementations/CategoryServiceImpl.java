package softuni.services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.query_dtos.Query3CategoryDto;
import softuni.domain.dtos.seed_dtos.CategorySeedDto;
import softuni.domain.entities.Category;
import softuni.repositories.CategoryRepository;
import softuni.services.interfaces.CategoryService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public String seedCategories(String json) {
        StringBuilder result = new StringBuilder();

        Gson gson = new Gson();
        CategorySeedDto[] categorySeedDtos = gson.fromJson(json, CategorySeedDto[].class);

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<CategorySeedDto, Category> typeMap = modelMapper.createTypeMap(CategorySeedDto.class, Category.class);
        typeMap.addMappings(mapper -> mapper.skip(Category::setProducts));

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            Set<ConstraintViolation<CategorySeedDto>> errorSet = validator.validate(categorySeedDto);

            if (errorSet.isEmpty()) {
                Category category = typeMap.map(categorySeedDto);
                this.categoryRepository.save(category);
                result.append(categorySeedDto.toString())
                        .append(" | Successfully added to repository!")
                        .append(System.lineSeparator());
            } else {
                result.append(categorySeedDto.toString())
                        .append(" | Not added to the database. Reasons: ")
                        .append(String.join(" | ", errorSet.stream().map(e -> e.getMessage()).collect(Collectors.toList())))
                        .append(System.lineSeparator());
            }
        }

        return result.toString().trim();
    }

    @Override
    public String getJsonCategoriesNameProductsCountAveragePriceTotalRevenue() {
        List<Object[]> result =
                this.categoryRepository.getCategoriesProductCountAvPriceTotalRevenueOrderedByProductsCount();

        Query3CategoryDto[] query3CategoryDtos = result.stream()
                .map(o -> {
                    Query3CategoryDto query3CategoryDto = new Query3CategoryDto();

                    query3CategoryDto.setName((String)o[0]);
                    query3CategoryDto.setProductsCount((Long)o[1]);
                    query3CategoryDto.setAveragePrice(new BigDecimal("" + o[2]));
                    query3CategoryDto.setTotalRevenue(new BigDecimal("" + o[3]));

                    return query3CategoryDto;
                }).toArray(n -> new Query3CategoryDto[n]);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(query3CategoryDtos);
    }
}
