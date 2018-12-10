package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.import_dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl extends BaseServiceImpl<Car, CarRepository> implements CarService {
    private Gson gson;
    private RacerRepository racerRepository;

    @Autowired
    public CarServiceImpl(CarRepository mainRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, Gson gson, RacerRepository racerRepository) {
        super(mainRepository, modelMapper, validationUtil, fileUtil);
        this.gson = gson;
        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean carsAreImported() {
        return super.getMainRepository().count() != 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return super.getFileUtil().readFile(IMPORT_FILE_RELATIVE_PATH + "cars.json");
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder resultFromImports = new StringBuilder();

        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);

        for (CarImportDto carImportDto : carImportDtos) {
            Racer racer = this.racerRepository.findRacerByName(carImportDto.getRacerName());

            if (!super.getValidationUtil().isValid(carImportDto) || racer == null) {
                resultFromImports.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Car validCar = super.getModelMapper().map(carImportDto, Car.class);
            validCar.setRacer(racer);

            String validCarEntityMessage = String.format("%s %s @ %d",
                    validCar.getBrand(), validCar.getModel(), validCar.getYearOfProduction());

            super.getMainRepository().saveAndFlush(validCar);
            resultFromImports.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    Car.class.getSimpleName(), validCarEntityMessage))
                    .append(System.lineSeparator());
        }

        return resultFromImports.toString().trim();
    }
}
