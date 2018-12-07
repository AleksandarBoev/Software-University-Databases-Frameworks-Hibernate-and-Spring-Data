package softuni.services.implementations;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.create_dtos.UserCreateDto;
import softuni.domain.dtos.create_dtos.UserRootCreateDto;
import softuni.domain.dtos.export_dtos.*;
import softuni.domain.entities.Product;
import softuni.domain.entities.User;
import softuni.repositories.UserRepository;
import softuni.services.interfaces.UserService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository, UserCreateDto> implements UserService {
    @Autowired
    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper, FileReaderUtil fileReaderUtil, FileWriterUtil fileWriterUtil, ValidatorUtil validatorUtil) {
        super(repository, modelMapper, fileReaderUtil, fileWriterUtil, validatorUtil);
    }

    @Override
    public String seedDataFromXmlFile(String xmlRelativeFilePath) throws IOException, JAXBException {
        String content = super.getFileReaderUtil().readFile(xmlRelativeFilePath);

        JAXBContext jaxbContext = JAXBContext.newInstance(UserRootCreateDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        UserRootCreateDto userRootCreateDto = (UserRootCreateDto) unmarshaller.unmarshal(new StringReader(content));

        UserCreateDto[] userCreateDtos = userRootCreateDto.getUsers();

        TypeMap<UserCreateDto, User> typeMap =
                super.getModelMapper().createTypeMap(UserCreateDto.class, User.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(User::setProductsSold);
            mapper.skip(User::setFriends);
            mapper.skip(User::setProductsBought);
            mapper.skip(User::setId);
        });
        typeMap.validate();

        StringBuilder resultFromSeed = new StringBuilder();
        List<User> validUsers = new ArrayList<>();

        for (UserCreateDto userCreateDto : userCreateDtos) {

            if (!super.getValidatorUtil().isValid(userCreateDto)) {
                resultFromSeed.append(userCreateDto.toString())
                        .append(" | Not added to db. Reasons: ")
                        .append(super.getValidatorUtil().getErrorMessages(userCreateDto))
                        .append(System.lineSeparator());
            } else {
                resultFromSeed.append(userCreateDto.toString())
                        .append(" | Added to db!")
                        .append(System.lineSeparator());
                User user = typeMap.map(userCreateDto);
                validUsers.add(user);
            }
        }

        super.getRepository().saveAll(validUsers);
        return resultFromSeed.toString().trim();
    }

    @Override
    public void exportUsersWhoHaveSoldAProduct(String fullFilePath) throws JAXBException {
        List<User> users = super.getRepository().findUsersWhoHaveSoldAProduct();

        //For every user remove the products, which they are selling, which don't have a buyer.
        for (User user : users) {
            List<Product> productsWithBuyers = user.getProductsSold()
                    .stream()
                    .filter(p -> p.getBuyer() != null)
                    .collect(Collectors.toList());

            user.setProductsSold(productsWithBuyers);
        }

        Converter<User, String> userToFirstNameConverter = context ->
                context.getSource() == null ? "null" : context.getSource().getFirstName();

        Converter<User, String> userToLastNameConverter = context ->
                context.getSource() == null ? "null" : context.getSource().getLastName();

        TypeMap<Product, Query2ProductDto> productToDtoMap =
                super.getModelMapper().createTypeMap(Product.class, Query2ProductDto.class);
        productToDtoMap.addMappings(mapper -> {
            mapper.using(userToFirstNameConverter).map(Product::getBuyer, Query2ProductDto::setBuyerFirstName);
            mapper.using(userToLastNameConverter).map(Product::getBuyer, Query2ProductDto::setBuyerLastName);
        });
        productToDtoMap.validate();

        Converter<List<Product>, Query2ProductRootDto> listProductsToRootDto = context -> {
            Query2ProductDto[] query2ProductDtos = context.getSource()
                    .stream()
                    .map(p -> productToDtoMap.map(p))
                    .toArray(n -> new Query2ProductDto[n]);

            Query2ProductRootDto result = new Query2ProductRootDto();
            result.setProductDtos(query2ProductDtos);
            return result;
        };

        TypeMap<User, Query2UserDto> userToDtoMap =
                super.getModelMapper().createTypeMap(User.class, Query2UserDto.class);
        userToDtoMap.addMappings(mapper -> {
            mapper.using(listProductsToRootDto).map(User::getProductsSold, Query2UserDto::setQuery2ProductRootDto);
        });
        userToDtoMap.validate();

        Query2UserRootDto query2UserRootDto = new Query2UserRootDto();

        Query2UserDto[] userDtos = users.stream().map(u -> userToDtoMap.map(u)).toArray(n -> new Query2UserDto[n]);
        query2UserRootDto.setQuery2UserDto(userDtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(Query2UserRootDto.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(query2UserRootDto, new File(fullFilePath));
    }

    @Override
    public void exportUsersAndProductsOrderedByProductsCountDescLastNameAsc(String fullFilePath) throws JAXBException {
        List<User> users = super.getRepository().findUsersWhoHaveSoldAProductOrderedByProductsSoldDescLastNameAsc();

        TypeMap<Product, Query4ProductDto> productToDtoMap =
                super.getModelMapper().createTypeMap(Product.class, Query4ProductDto.class);
        productToDtoMap.validate();

        Converter<List<Product>, Query4ProductRootDto> productListToProductRootConverter = context -> {
            Query4ProductDto[] query4ProductDtos = context.getSource()
                    .stream()
                    .map(p -> productToDtoMap.map(p))
                    .toArray(n -> new Query4ProductDto[n]);

            Integer countOfProducts = query4ProductDtos.length;

            Query4ProductRootDto query4ProductRootDto = new Query4ProductRootDto(countOfProducts, query4ProductDtos);
            return query4ProductRootDto;
        };

        TypeMap<User, Query4UserDto> userToDtoMap =
                super.getModelMapper().createTypeMap(User.class, Query4UserDto.class);
        userToDtoMap.addMappings(mapper -> {
           mapper.using(productListToProductRootConverter).map(User::getProductsSold, Query4UserDto::setQuery4ProductRootDto);
        });
        userToDtoMap.validate();

        Query4UserDto[] query4UserDto = users
                .stream()
                .map(u -> userToDtoMap.map(u))
                .toArray(n -> new Query4UserDto[n]);

        Query4UserRootDto query4UserRootDto = new Query4UserRootDto(query4UserDto.length, query4UserDto);

        JAXBContext jaxbContext = JAXBContext.newInstance(Query4UserRootDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(query4UserRootDto, new File(fullFilePath));
    }
}
