package softuni.services.implementations;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.create_dtos.ProductCreateDto;
import softuni.domain.dtos.create_dtos.ProductRootCreateDto;
import softuni.domain.dtos.export_dtos.Query1ProductDto;
import softuni.domain.dtos.export_dtos.Query1ProductRootDto;
import softuni.domain.entities.Category;
import softuni.domain.entities.Product;
import softuni.domain.entities.User;
import softuni.repositories.CategoryRepository;
import softuni.repositories.ProductRepository;
import softuni.repositories.UserRepository;
import softuni.services.interfaces.ProductService;
import softuni.utils.interfaces.FileReaderUtil;
import softuni.utils.interfaces.FileWriterUtil;
import softuni.utils.interfaces.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, ProductRepository, ProductCreateDto> implements ProductService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ModelMapper modelMapper,
                              FileReaderUtil fileReaderUtil, FileWriterUtil fileWriterUtil,
                              ValidatorUtil validatorUtil, CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        super(repository, modelMapper, fileReaderUtil, fileWriterUtil, validatorUtil);
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String seedDataFromXmlFile(String xmlRelativeFilePath) throws IOException, JAXBException {
        String content = super.getFileReaderUtil().readFile(xmlRelativeFilePath);

        JAXBContext jaxbContext = JAXBContext.newInstance(ProductRootCreateDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ProductRootCreateDto productRootCreateDto =
                (ProductRootCreateDto) unmarshaller.unmarshal(new StringReader(content));

        ProductCreateDto[] productCreateDtos = productRootCreateDto.getProductCreateDtos();

        TypeMap<ProductCreateDto, Product> typeMap =
                super.getModelMapper().createTypeMap(ProductCreateDto.class, Product.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(Product::setId);
        });
        typeMap.validate();
        List<Product> validProducts = new ArrayList<>();

        List<User> allUsers = this.userRepository.findAll();
        int usersCount = allUsers.size();

        List<Category> allCategories = this.categoryRepository.findAll();
        int categoriesCount = allCategories.size();

        Random random = new Random();

        int counter = 0;
        for (ProductCreateDto productCreateDto : productCreateDtos) {
            counter++;
            if (!super.getValidatorUtil().isValid(productCreateDto))
                continue;

            User buyer = null;
            User seller = null;

            //if random buyer is also the seller, do the cycle again. If there is only 1 user in the db,
            // then a stack overflow would occur
            do {
                buyer = allUsers.get(random.nextInt(usersCount));
                seller = allUsers.get(random.nextInt(usersCount));
            } while (buyer.getId().equals(seller.getId()));

            Category category1 = null;
            Category category2 = null;
            do {
                category1 = allCategories.get(random.nextInt(categoriesCount));
                category2 = allCategories.get(random.nextInt(categoriesCount));
            } while (category1.getId().equals(category2.getId()));

            if (counter % 10 != 0) //every tenth product will not have a buyer. Required for tasks
                productCreateDto.setBuyer(buyer);

            productCreateDto.setSeller(seller);
            productCreateDto.getCategories().add(category1);
            productCreateDto.getCategories().add(category2);

            validProducts.add(typeMap.map(productCreateDto));
        }

        super.getRepository().saveAll(validProducts);
        return null;
    }

    @Override
    public void exportProductsWithPriceBetweenWithNoBuyerOrderedByPriceAsc(
            String fullFilePath, BigDecimal price1, BigDecimal price2) throws JAXBException {
        List<Product> productsToBeExported =
                super.getRepository().findProductsByPriceBetweenAndBuyerIsNullOrderByPriceAsc(price1, price2);

        Converter<User, String> userToNamesConverter = context ->
                context.getSource().getFirstName() + " " + context.getSource().getLastName();

        TypeMap<Product, Query1ProductDto> typeMap =
                super.getModelMapper().createTypeMap(Product.class, Query1ProductDto.class);
        typeMap.addMappings(mapper -> {
            mapper.using(userToNamesConverter).map(Product::getSeller, Query1ProductDto::setSeller);
        });
        typeMap.validate();

        Query1ProductRootDto query1ProductRootDto = new Query1ProductRootDto();
        query1ProductRootDto.setProductDtos(productsToBeExported
                .stream()
                .map(p -> typeMap.map(p))
                .toArray(n -> new Query1ProductDto[n]));

        JAXBContext jaxbContext = JAXBContext.newInstance(Query1ProductRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //makes things prettier. Remove for better performance

        marshaller.marshal(query1ProductRootDto, new File(fullFilePath));
    }
}
