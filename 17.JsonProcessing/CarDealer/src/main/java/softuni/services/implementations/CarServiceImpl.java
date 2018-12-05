package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.export_dtos.Query2CarDto;
import softuni.domain.dtos.export_dtos.Query4CarDto;
import softuni.domain.dtos.export_dtos.Query4CarPartsDto;
import softuni.domain.dtos.export_dtos.Query4PartDto;
import softuni.domain.dtos.seed_dtos.CarSeedDto;
import softuni.domain.entities.Car;
import softuni.domain.entities.Part;
import softuni.repositories.CarRepository;
import softuni.repositories.PartRepository;
import softuni.services.interfaces.CarService;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil<CarSeedDto> validatorUtil;
    private Gson gson;
    private PartRepository partRepository;
    private FileWriterUtil fileWriterUtil;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidatorUtil<CarSeedDto> validatorUtil, Gson gson, PartRepository partRepository, FileWriterUtil fileWriterUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.partRepository = partRepository;
        this.fileWriterUtil = fileWriterUtil;
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

        int[] numberOfParts = new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
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

    @Override
    public void exportCarsFromMakeOrderedByModelNameAscTravelledDistanceDesc(String fullFilePath, String make) throws IOException {
        TypeMap<Car, Query2CarDto> typeMap = this.modelMapper.createTypeMap(Car.class, Query2CarDto.class);
        typeMap.validate();

        Query2CarDto[] query2CarDtos = this.carRepository.findCarsByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(c -> typeMap.map(c))
                .toArray(n -> new Query2CarDto[n]);

        String jsonResult = this.gson.toJson(query2CarDtos);
        this.fileWriterUtil.writeToFile(fullFilePath, jsonResult);
    }

    @Override
    public void exportCarsAndTheirParts(String fullFilePath) throws IOException {
        List<Car> allCars = this.carRepository.findAll();

        TypeMap<Part, Query4PartDto> partToPartDtoMap =
                this.modelMapper.createTypeMap(Part.class, Query4PartDto.class);
        partToPartDtoMap.validate();

        List<Query4CarPartsDto> result = allCars.stream()
                .map(c -> {
                    String carMake = c.getMake();
                    String carModel = c.getModel();
                    BigInteger travelledDistance = c.getTravelledDistance();
                    Query4CarDto car = new Query4CarDto(carMake, carModel, travelledDistance);

                    List<Query4PartDto> partsList = c.getParts().stream().map(p -> partToPartDtoMap.map(p)).collect(Collectors.toList());

                    return new Query4CarPartsDto(car, partsList);
                }).collect(Collectors.toList());

        String resultJson = this.gson.toJson(result);
        this.fileWriterUtil.writeToFile(fullFilePath, resultJson);


    /* This solution does not work. Gives error: Failed to get value from softuni.domain.entities.Car.getModel()
        Converter<List<Part>, List<Query4PartDto>> partsListConverter =
                context -> context.getSource().stream().map(p -> partToPartDtoMap.map(p)).collect(Collectors.toList());

        Converter<List<String>, Query4CarDto> testing = context -> {
            String make = context.getSource().get(0);
            String model = context.getSource().get(1);
            BigInteger travelledDistance = new BigInteger(context.getSource().get(2));
            return new Query4CarDto(make, model, travelledDistance);
        };

        TypeMap<Car, Query4CarPartsDto> carToCarPartsDtoMap =
                this.modelMapper.createTypeMap(Car.class, Query4CarPartsDto.class);

        carToCarPartsDtoMap.addMappings(mapper -> {
            mapper.using(testing).map(src -> {
                List<String> params = new ArrayList<>();
                params.add(src.getMake());
                params.add(src.getModel());
                params.add("" + src.getTravelledDistance());
                return params;
            }, Query4CarPartsDto::setCar);
            mapper.using(partsListConverter).map(Car::getParts, Query4CarPartsDto::setParts);
        });
        carToCarPartsDtoMap.validate();

        Query4CarPartsDto[] query4CarPartsDtos = allCars
                .stream()
                .map(c -> carToCarPartsDtoMap.map(c))
                .toArray(n -> new Query4CarPartsDto[n]);

        String jsonResult = this.gson.toJson(query4CarPartsDtos);
        this.fileWriterUtil.writeToFile(fullFilePath, jsonResult);
    */
    }
}
