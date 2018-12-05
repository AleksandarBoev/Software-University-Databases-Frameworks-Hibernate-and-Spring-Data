package softuni.services.interfaces;

import java.io.IOException;

public interface CarService {
    String seedCars(String carsJson);

    void exportCarsFromMakeOrderedByModelNameAscTravelledDistanceDesc(String fullFilePath, String make) throws IOException;

    void exportCarsAndTheirParts(String fullFilePath) throws IOException;
}
