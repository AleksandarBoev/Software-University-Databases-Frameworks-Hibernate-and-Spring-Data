package mostwanted.service;

import com.google.gson.Gson;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.import_dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TownServiceImpl extends BaseServiceImpl<Town, TownRepository> implements TownService {
    private Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository mainRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, Gson gson) {
        super(mainRepository, modelMapper, validationUtil, fileUtil);
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return super.getMainRepository().count() != 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return super.getFileUtil().readFile(IMPORT_FILE_RELATIVE_PATH + "towns.json");
    }

    @Override
    public String importTowns(String townsFileContent) { //depends on the above method.
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);

        StringBuilder resultFromImports = new StringBuilder();


        for (TownImportDto townImportDto : townImportDtos) {
            if (!super.getValidationUtil().isValid(townImportDto)) {
                resultFromImports.append(Constants.INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            if (super.getMainRepository().existsTownByName(townImportDto.getName())) {
                resultFromImports.append(Constants.DUPLICATE_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            Town validTown = super.getModelMapper().map(townImportDto, Town.class);
            super.getMainRepository().saveAndFlush(validTown); //validTown object automatically gets an id.

            resultFromImports.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    validTown.getClass().getSimpleName(), validTown.getName()))
                    .append(System.lineSeparator());
        }

        return resultFromImports.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        return "Hello";
    }
}
