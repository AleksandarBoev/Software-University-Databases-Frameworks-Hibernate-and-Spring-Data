package softuni.services.interfaces;

import softuni.domain.entities.Product;
import softuni.repositories.ProductRepository;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;

public interface ProductService extends BaseService<Product, ProductRepository> {
    void exportProductsWithPriceBetweenWithNoBuyerOrderedByPriceAsc(String fullFilePath, BigDecimal price1, BigDecimal price2) throws JAXBException;
}
