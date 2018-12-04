package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.seed_dtos.CarSeedDto;
import softuni.domain.entities.Car;
import softuni.domain.entities.Part;
import softuni.repositories.CarRepository;
import softuni.repositories.PartRepository;
import softuni.services.interfaces.CarService;
import softuni.utils.interfaces.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil<CarSeedDto> validatorUtil;
    private Gson gson;
    private PartRepository partRepository;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidatorUtil<CarSeedDto> validatorUtil, Gson gson, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.partRepository = partRepository;
    }

    @Override
    public String seedCars(String carsJson) {
        CarSeedDto[] carSeedDtos = this.gson.fromJson(carsJson, CarSeedDto[].class);

        TypeMap<CarSeedDto, Car> typeMap = this.modelMapper.createTypeMap(CarSeedDto.class, Car.class);
        typeMap.addMappings(mapper -> {
            mapper.skip(Car::setId);
            mapper.skip(Car::setParts);
        });
        typeMap.validate();

        List<Car> validCars = new ArrayList<>();
        StringBuilder resultFromSeed = new StringBuilder();

        for (CarSeedDto carSeedDto : carSeedDtos) {
            if (!this.validatorUtil.isValid(carSeedDto)) {
                List<String> errors = this.validatorUtil.getErrorMessages(carSeedDto);

                resultFromSeed.append(carSeedDto.toString()).append(" | Not added to database. Reasons: ")
                        .append(String.join(" | ", errors))
                        .append(System.lineSeparator());
            } else {
                Car validCar = typeMap.map(carSeedDto);
                validCars.add(validCar);

                resultFromSeed.append(carSeedDto.toString()).append(" | Added to database!")
                        .append(System.lineSeparator());
            }
        }

        this.carRepository.saveAll(validCars);

        List<Part> allParts = this.partRepository.findAll();
        int numberOfAllParts = allParts.size();

        int[] numberOfParts = new int[] {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        Random random = new Random();

        for (Car seededCar : validCars) {
            int randomNumberBetween10And20 = numberOfParts[random.nextInt(numberOfParts.length)];

            for (int i = 0; i < randomNumberBetween10And20; i++) {
                int randomPartIndex = random.nextInt(numberOfAllParts);
                Part randomPart = allParts.get(randomPartIndex);

                seededCar.getParts().add(randomPart);
            }

            this.carRepository.save(seededCar);
        }

        return resultFromSeed.toString().trim();
    }
}
