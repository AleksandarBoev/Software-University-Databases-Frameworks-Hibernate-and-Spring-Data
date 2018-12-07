package softuni.services.implementations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.create_dtos.CategoryCreateDto;
import softuni.domain.dtos.create_dtos.CategoryRootCreateDto;
import softuni.domain.dtos.export_dtos.Query3CategoryDto;
import softuni.domain.dtos.export_dtos.Query3CategoryRootDto;
import softuni.domain.entities.Category;
import softuni.repositories.CategoryRepository;
import softuni.services.interfaces.CategoryService;
import softuni.utils.interfaces.FileReaderUtil;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, CategoryRepository, CategoryCreateDto> implements CategoryService {
    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, ModelMapper modelMapper,
                               FileReaderUtil fileReaderUtil, FileWriterUtil fileWriterUtil, ValidatorUtil validatorUtil) {
        super(repository, modelMapper, fileReaderUtil, fileWriterUtil, validatorUtil);
    }

    @Override
    public String seedDataFromXmlFile(String xmlRelativeFilePath) throws IOException, JAXBException {
        String content = super.getFileReaderUtil().readFile(xmlRelativeFilePath);

        JAXBContext jaxbContext = JAXBContext.newInstance(CategoryRootCreateDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        //validations and building added ot not added messages

        CategoryRootCreateDto categoryRootCreateDto =
                (CategoryRootCreateDto) unmarshaller.unmarshal(new StringReader(content));

        CategoryCreateDto[] categoryCreateDtos = categoryRootCreateDto.getCategoryCreateDtos();

        TypeMap<CategoryCreateDto, Category> typeMap =
                super.getModelMapper().createTypeMap(CategoryCreateDto.class, Category.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(Category::setId);
            mapper.skip(Category::setProducts);
        });

        typeMap.validate();

        List<Category> categories = Arrays.stream(categoryCreateDtos)
                .map(c -> typeMap.map(c))
                .collect(Collectors.toList());

        super.getRepository().saveAll(categories);
        return null;
    }

    @Override
    public void exportCategoriesNamesProductsCountAveragePriceAndTotalRevenue(String fullFilePath) throws JAXBException {
        List<Object[]> categoryInfo =
                super.getRepository().getCategoriesNameProductsCountAveragePriceTotalRevenue();

        Query3CategoryDto[] query3CategoryDtos = categoryInfo
                .stream()
                .map(c -> {
                    String categoryName = (String) c[0];
                    Long productsCount = (Long) c[1];
                    Double averagePrice = (Double) c[2];
                    BigDecimal totalRevenue = (BigDecimal) c[3];

                    return new Query3CategoryDto(categoryName, productsCount, averagePrice, totalRevenue);
                }).toArray(n -> new Query3CategoryDto[n]);

        Query3CategoryRootDto query3CategoryRootDto = new Query3CategoryRootDto();
        query3CategoryRootDto.setQuery3CategoryDtos(query3CategoryDtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(Query3CategoryRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(query3CategoryRootDto, new File(fullFilePath));
    }
}
