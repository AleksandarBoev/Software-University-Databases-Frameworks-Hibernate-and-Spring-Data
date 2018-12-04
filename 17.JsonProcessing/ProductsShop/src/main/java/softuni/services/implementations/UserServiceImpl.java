package softuni.services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.dtos.query_dtos.*;
import softuni.domain.dtos.seed_dtos.UserSeedDto;
import softuni.domain.entities.Product;
import softuni.domain.entities.User;
import softuni.repositories.UserRepository;
import softuni.services.interfaces.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getJsonUsersWithAtLeastOneSoldItem() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Product, Query2ProductDto> typeMapProduct = modelMapper.createTypeMap(Product.class, Query2ProductDto.class);

        //There has to be a smarter way of doing this
        Converter<List<Product>, List<Query2ProductDto>> productConverter = context -> {
            List<Query2ProductDto> query2ProductDtos = new ArrayList<>();
            for (Product product : context.getSource()) {
                query2ProductDtos.add(typeMapProduct.map(product));
            }

            return query2ProductDtos;
        };


        TypeMap<User, Query2UserDto> typeMapUser = modelMapper.createTypeMap(User.class, Query2UserDto.class);
        typeMapUser.addMappings(mapper -> {
            mapper.using(productConverter).map(User::getProductsSold, Query2UserDto::setSoldProducts);
        });
        typeMapUser.validate();

        //All users retrieved from the db have null for firstName for some odd reason
        List<User> users = this.userRepository.findUsersByAtLeastOneSoldItemOrderByLastNameThenByFirstName();
        Query2UserDto[] query2UserDtos = users
                .stream()
                .map(u -> typeMapUser.map(u))
                .toArray(n -> new Query2UserDto[n]);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(query2UserDtos);
    }

    @Override
    public String getJsonProductSoldOrderByProductsSoldDescLastNameAsc() {
        List<User> users = this.userRepository.findUsersByProductSoldOrderByProductsSoldDescLastNameAsc();

        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Product, Query4ProductDto> productToQuery4ProductDto =
                modelMapper.createTypeMap(Product.class, Query4ProductDto.class);

        Converter<List<Product>, Query4ProductsSoldDto> productsSoldConverter = context -> {
            Query4ProductsSoldDto result = new Query4ProductsSoldDto();

            result.setCount(context.getSource().size());

            List<Query4ProductDto> query4ProductDtos = context.getSource().stream()
                    .map(p -> productToQuery4ProductDto.map(p))
                    .collect(Collectors.toList());
            result.setProducts(query4ProductDtos);

            return result;
        };

        TypeMap<User, Query4UserDto> userToQuery4UserDto =
                modelMapper.createTypeMap(User.class, Query4UserDto.class);
        userToQuery4UserDto.addMappings(mapper -> {
           mapper.using(productsSoldConverter).map(User::getProductsSold, Query4UserDto::setSoldProducts);
        });
        userToQuery4UserDto.validate();

        int usersCount = users.size();
        List<Query4UserDto> query4UserDtoList = users
                .stream()
                .map(u -> userToQuery4UserDto.map(u))
                .collect(Collectors.toList());

        Query4AllUsersDto query4AllUsersDto = new Query4AllUsersDto();
        query4AllUsersDto.setUsersCount(usersCount);
        query4AllUsersDto.setUsers(query4UserDtoList);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(query4AllUsersDto);
    }

    //Not using theValidatorUtilImpl, so that I can get used to the new material
    @Override
    public String seedUsers(String json) {
        StringBuilder result = new StringBuilder();
        Gson gson = new Gson();
        //Step one: Get the objects from the json in a seedDto form.
        UserSeedDto[] userSeedDtos = gson.fromJson(json, UserSeedDto[].class);

        //Step two: prepare mapping from a seedDto to an entity
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<UserSeedDto, User> typeMap = modelMapper.createTypeMap(UserSeedDto.class, User.class);
        typeMap.addMappings(mapper -> {
            mapper.skip(User::setProductsBought);
            mapper.skip(User::setFriends);
            mapper.skip(User::setProductsSold);
            mapper.skip(User::setId);
        });
        typeMap.validate();

        //Step three: prepare validator
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (int i = 0; i < userSeedDtos.length; i++) {
            Set<ConstraintViolation<UserSeedDto>> errorSet =
                    validator.validate(userSeedDtos[i]);

            //Step four: check if info is valid. If yes - save to repository. If not, display errors.
            if (errorSet.isEmpty()) {
                User validUser = typeMap.map(userSeedDtos[i]);
                this.userRepository.save(validUser);
                result.append(userSeedDtos[i].toString())
                        .append(" | Successfully added to repository!")
                        .append(System.lineSeparator());
            } else {
                result.append(userSeedDtos[i])
                        .append(" | Not added to repository. Reasons: ")
                        .append(String.join(" | ", errorSet.stream().map(e -> e.getMessage()).collect(Collectors.toList())))
                        .append(System.lineSeparator());
            }
        }

        List<User> allUsers = this.userRepository.findAll();
        Random random = new Random();
        int numberOfUsers = allUsers.size();

        for (User currentUser : allUsers) {
            User friend1 = null;
            User friend2 = null;
            do { //make sure that:
                friend1 = allUsers.get(random.nextInt(numberOfUsers));
                friend2 = allUsers.get(random.nextInt(numberOfUsers));
            } while (friend1.getId().compareTo(friend2.getId()) == 0 || //friend1 and friend2 are different
                    friend1.getId().compareTo(currentUser.getId()) == 0 || //friend1 and current user are different
                    friend2.getId().compareTo(currentUser.getId()) == 0); //friend2 and current user are different

            List<User> friends = new ArrayList<>();
            friends.add(friend1);
            friends.add(friend2);
            currentUser.setFriends(friends);

            this.userRepository.save(currentUser); //update user
        }

        return result.toString().trim();
    }
}
