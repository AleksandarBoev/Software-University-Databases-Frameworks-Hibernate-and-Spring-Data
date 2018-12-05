package softuni.services.interfaces;

import java.io.IOException;

public interface SaleService {
    String seedRandomSales();

    void exportAllSales(String fullFilePath) throws IOException;
}
