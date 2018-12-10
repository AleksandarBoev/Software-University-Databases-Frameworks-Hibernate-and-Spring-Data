package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.import_dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl extends BaseServiceImpl<District, DistrictRepository> implements DistrictService {
    private Gson gson;
    private TownRepository townRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository mainRepository, ModelMapper modelMapper, ValidationUtil validationUtil,
                               FileUtil fileUtil, Gson gson, TownRepository townRepository) {
        super(mainRepository, modelMapper, validationUtil, fileUtil);
        this.gson = gson;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean districtsAreImported() {
        return super.getMainRepository().count() != 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return super.getFileUtil().readFile(IMPORT_FILE_RELATIVE_PATH + "districts.json");
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        StringBuilder resultFromImports = new StringBuilder();

        for (DistrictImportDto districtImportDto : districtImportDtos) {
            Town town = townRepository.findByName(districtImportDto.getTownName());

            if (!super.getValidationUtil().isValid(districtImportDto) || town == null) {
                resultFromImports.append(Constants.INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());

                continue;
            }

            if (super.getMainRepository().existsByName(districtImportDto.getName())) {
                resultFromImports.append(Constants.DUPLICATE_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            District district = super.getModelMapper().map(districtImportDto, District.class);
            district.setTown(town);
            super.getMainRepository().saveAndFlush(district);
            resultFromImports.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    District.class.getSimpleName(), district.getName()))
                    .append(System.lineSeparator());

//            Converter<String, Town> converter = context -> {
//                return this.townRepository.findTownByName(context.getSource());
//            };
        }

        return resultFromImports.toString().trim();
    }
}
