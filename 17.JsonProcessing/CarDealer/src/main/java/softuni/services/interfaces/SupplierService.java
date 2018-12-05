package softuni.services.interfaces;

import java.io.IOException;

public interface SupplierService {
    String seedSuppliers(String suppliersJson);

    void exportLocalSuppliers(String fullFilePath) throws IOException;
}
