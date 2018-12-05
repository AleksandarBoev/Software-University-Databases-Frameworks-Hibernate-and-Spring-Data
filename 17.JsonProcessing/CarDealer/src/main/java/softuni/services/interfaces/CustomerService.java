package softuni.services.interfaces;

import java.io.IOException;

public interface CustomerService {
    String seedCustomers(String customersJson);

    void exportOrderedCustomers(String fullFilePath) throws IOException;

    void exportTotalSalesByCustomer(String fullFilePath) throws IOException;
}
