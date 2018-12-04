package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.seed_dtos.PartSeedDto;
import softuni.domain.entities.Part;
import softuni.domain.entities.Supplier;
import softuni.repositories.PartRepository;
import softuni.repositories.SupplierRepository;
import softuni.services.interfaces.PartService;
import softuni.utils.interfaces.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil<PartSeedDto> validatorUtil;
    private Gson gson;
    private SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidatorUtil<PartSeedDto> validatorUtil, Gson gson, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public String seedParts(String partsJson) {
        PartSeedDto[] partSeedDtos = this.gson.fromJson(partsJson, PartSeedDto[].class);

        TypeMap<PartSeedDto, Part> typeMap = this.modelMapper.createTypeMap(PartSeedDto.class, Part.class);
        typeMap.addMappings(mapper -> {
            mapper.skip(Part::setSupplier);
            mapper.skip(Part::setId);
        });
        typeMap.validate();

        List<Part> validParts = new ArrayList<>();
        StringBuilder resultFromSeed = new StringBuilder();

        for (PartSeedDto partSeedDto : partSeedDtos) {
            if (!this.validatorUtil.isValid(partSeedDto)) {
                List<String> errors = this.validatorUtil.getErrorMessages(partSeedDto);

                resultFromSeed.append(partSeedDto.toString()).append(" | Not added to repository. Reasons: ")
                        .append(String.join(" | ", errors))
                        .append(System.lineSeparator());
            } else {
                validParts.add(typeMap.map(partSeedDto));

                resultFromSeed.append(partSeedDto.toString()).append(" | Added to repository!")
                        .append(System.lineSeparator());
            }
        }

        this.partRepository.saveAll(validParts);

        List<Supplier> allSuppliers = this.supplierRepository.findAll();
        int numberOfSuppliers = allSuppliers.size();

        Random random = new Random();

        for (Part seededPart : validParts) {
            Supplier randomSupplier = allSuppliers.get(random.nextInt(numberOfSuppliers));
            seededPart.setSupplier(randomSupplier);
            this.partRepository.save(seededPart);
        }

        return resultFromSeed.toString().trim();
    }
}
