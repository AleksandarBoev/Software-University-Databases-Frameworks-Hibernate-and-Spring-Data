package softuni.services.interfaces;

import softuni.domain.entities.Category;
import softuni.repositories.CategoryRepository;

import javax.xml.bind.JAXBException;

public interface CategoryService extends BaseService<Category, CategoryRepository> {
    void exportCategoriesNamesProductsCountAveragePriceAndTotalRevenue(String fullFilePath) throws JAXBException;
}
