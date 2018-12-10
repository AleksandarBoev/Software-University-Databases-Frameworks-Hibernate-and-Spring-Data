package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        List<Category> categories = this.categoryRepository.getExportCategories();

        StringBuilder exportResult = new StringBuilder();

        for (Category category : categories) {
            exportResult.append(this.formatCategory(category)).append(System.lineSeparator());
        }

        return exportResult.toString().trim();
    }

    private String formatCategory(Category category) {
        //Category: {category1Name}
        //--- Item Name: {item1Name}
        //--- Item Price: {item1Price}
        //
        //--- Item Name: {item2Name}
        //--- Item Price: {item2Price}
        //
        //--- Item Name: {item3Name}
        //--- Item Price: {item3Price}
        StringBuilder result = new StringBuilder();
        result.append(String.format("Category: %s%n", category.getName()));

        for (Item item : category.getItems()) {
            result.append("--- Item Name: ").append(item.getName()).append(System.lineSeparator());
            result.append(String.format("--- Item Price: %.2f%n", item.getPrice()));
            result.append(System.lineSeparator());
        }

        return result.toString();
    }
}
