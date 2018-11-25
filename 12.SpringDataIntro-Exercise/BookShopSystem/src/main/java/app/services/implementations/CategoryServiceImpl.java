package app.services.implementations;

import app.models.Category;
import app.repositories.CategoryRepository;
import app.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Find category by id. If nothing is found, returns null.
     *
     * @return null, Category
     */
    @Override
    public Category getById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    /**
     * Finds category by categoryName. If nothing is found, returns null
     *
     * @return null, Category
     */
    @Override
    public Category getByName(String categoryName) {
        return this.categoryRepository.getByName(categoryName);
    }

    /**
     * Validates and registers category in the database. Validations include:
     * <ul>
     * <li> category parameter is not null
     * <li> category parameter has id == null. If it is not null, then it has been
     * already registered in the database.
     * </ul>
     *
     * @return true if registration into the database is successful and false if not
     */
    @Override
    public boolean register(Category category) {
        if (category == null || category.getId() != null)
            return false;

        this.categoryRepository.save(category);
        return true;
    }

    @Override
    public List<Category> getAll() {
        List<Category> allCategories = new ArrayList<>();

        for (Category category : this.categoryRepository.findAll())
            allCategories.add(category);

        return allCategories;
    }

    @Override
    public boolean update(Category category) {
        if (category == null || !this.categoryRepository.existsById(category.getId()))
            return false;

        this.categoryRepository.save(category);
        return true;
    }
}
