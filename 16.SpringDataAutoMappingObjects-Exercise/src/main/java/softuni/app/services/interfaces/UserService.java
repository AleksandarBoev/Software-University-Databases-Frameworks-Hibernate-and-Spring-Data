package softuni.app.services.interfaces;

import softuni.app.domain.dtos.*;
import softuni.app.domain.entities.User;
import softuni.app.repositories.UserRepository;

public interface UserService {
    String registerUser(String userRegisterDto);

    String loginUser(String userLoginInfo);

    String logoutUser(UserLogoutDto userLogoutDto);

    String addGame(GameRegisterDto gameRegisterDto);

    String editGame(GameEditDto gameEditDto);
}
