package softuni.services.implementations;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.seed_dtos.SupplierSeedDto;
import softuni.domain.entities.Supplier;
import softuni.repositories.SupplierRepository;
import softuni.services.interfaces.SupplierService;
import softuni.utils.interfaces.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidatorUtil<SupplierSeedDto> validatorUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil<SupplierSeedDto> validatorUtil) {
        this.supplierRepository = supplierRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String seedSuppliers(String suppliersJson) {
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(suppliersJson, SupplierSeedDto[].class);

        TypeMap<SupplierSeedDto, Supplier> typeMap =
                this.modelMapper.createTypeMap(SupplierSeedDto.class, Supplier.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(Supplier::setParts);
            mapper.skip(Supplier::setId);
        });
        typeMap.validate();

        List<Supplier> validSuppliers = new ArrayList<>();
        StringBuilder resultFromJson = new StringBuilder();

        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!this.validatorUtil.isValid(supplierSeedDto)) {
                resultFromJson.append(supplierSeedDto.toString()).append(" | Not added to repository. Reasons: ")
                        .append(String.join(" | ", this.validatorUtil.getErrorMessages(supplierSeedDto)))
                        .append(System.lineSeparator());
            } else {
                validSuppliers.add(typeMap.map(supplierSeedDto));
                resultFromJson.append(supplierSeedDto.toString()).append(" | Added to repository!")
                        .append(System.lineSeparator());
            }
        }

        this.supplierRepository.saveAll(validSuppliers);
        return resultFromJson.toString().trim();
    }
}
