package softuni.services.interfaces;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    String seedProducts(String json);

    void saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscRelativePath(String price1, String price2, String relativePath) throws IOException;

    void saveToJsonProductsWithinPriceRangeWithoutBuyerOrderedByPriceAscFullPath(String price1, String price2, String fullPath) throws IOException;
}
