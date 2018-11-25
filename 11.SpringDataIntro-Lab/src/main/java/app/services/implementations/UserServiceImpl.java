package app.services.implementations;

import app.models.User;
import app.repositories.UserRepository;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Primary
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(User user) {
        if (user == null || this.userRepository.findById(user.getId()).orElse(null) != null ||
                this.userRepository.getByUserName(user.getUserName()) != null) {
            return false;
        }

        this.userRepository.save(user);
        return true;
    }
}
