package softuni.app.services.implementations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.app.domain.dtos.*;
import softuni.app.domain.entities.User;
import softuni.app.repositories.UserRepository;
import softuni.app.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final String ERROR_USER_WITH_EMAIL_ALREADY_EXISTS = "User with email '%s' already exists!";

    private static final String SPLITTER = "\\|";

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String userRegisterInfo) {
        //[fullName] was registered / Incorrect email. / Passwords must match. / A user with that email has already registered
        //RegisterUser|<email>|<password>|<confirmPassword>|<fullName>
        String[] userRegisterInfoTokens = userRegisterInfo.split(SPLITTER);
        String email = userRegisterInfoTokens[1];
        String password = userRegisterInfoTokens[2];
        String confirmPassword = userRegisterInfoTokens[3];
        String fullName = userRegisterInfoTokens[4];

        if (this.userRepository.existsUserByEmail(email))
            return String.format(ERROR_USER_WITH_EMAIL_ALREADY_EXISTS, email);

        try {
            UserRegisterDto userRegisterDto = new UserRegisterDto(email, password, confirmPassword, fullName);

            TypeMap<UserRegisterDto, User> userTypeMap = this.modelMapper.createTypeMap(UserRegisterDto.class, User.class);

            userTypeMap.addMappings(mapper -> {
               mapper.skip(User::setRole);
               mapper.skip(User::setGames);
               mapper.skip(User::setOrders);
            });
            userTypeMap.validate();

            User newUser = userTypeMap.map(userRegisterDto);
            this.userRepository.save(newUser);

            return fullName + " was registered.";
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        }
    }

    @Override
    public String loginUser(String userLogInInfo) {
        //LoginUser|<email>|<password>
        String[] userLogInInfoTokens = userLogInInfo.split(SPLITTER);
        String email = userLogInInfoTokens[1];
        String password = userLogInInfoTokens[2];


        return null;
    }

    @Override
    public String logoutUser(UserLogoutDto userLogoutDto) {
        return null;
    }

    @Override
    public String addGame(GameRegisterDto gameRegisterDto) {
        return null;
    }

    @Override
    public String editGame(GameEditDto gameEditDto) {
        return null;
    }
}
