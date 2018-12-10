package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.import_dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl extends BaseServiceImpl<Racer, RacerRepository> implements RacerService {
    private Gson gson;
    private TownRepository townRepository;

    @Autowired
    public RacerServiceImpl(RacerRepository mainRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, Gson gson, TownRepository townRepository) {
        super(mainRepository, modelMapper, validationUtil, fileUtil);
        this.gson = gson;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean racersAreImported() {
        return super.getMainRepository().count() != 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return super.getFileUtil().readFile(IMPORT_FILE_RELATIVE_PATH + "racers.json");
    }

    @Override
    public String importRacers(String racersFileContent) {
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);

        StringBuilder importResults = new StringBuilder();

        for (RacerImportDto racerImportDto : racerImportDtos) {
            Town town = this.townRepository.findByName(racerImportDto.getHomeTown());

            if (!super.getValidationUtil().isValid(racerImportDto) || town == null) {
                importResults.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (super.getMainRepository().findRacerByName(racerImportDto.getName()) != null){
                importResults.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Racer racer = super.getModelMapper().map(racerImportDto, Racer.class);
            racer.setTown(town);
            super.getMainRepository().saveAndFlush(racer);
            importResults.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    Racer.class.getSimpleName(), racer.getName()))
                    .append(System.lineSeparator());
        }

        return importResults.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        return "Hello";
    }
}
