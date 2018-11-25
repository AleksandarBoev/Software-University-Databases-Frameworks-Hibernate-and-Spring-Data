package app.services.implementations;

import app.models.User;
import app.repositories.UserRepository;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Primary
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {
    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<User> getUsersByEmailProvider(String emailProvider) {
        return super.getRepository().getUsersByEmailEndsWith(emailProvider);
    }

    @Override
    public Iterable<User> getUsersMarkedForDeletion() {
        Iterable<User> users = new ArrayList<>();


        return super.getRepository().getUsersByIsDeletedTrue();
    }

    @Override
    public void markUsersForDeletionBeforeDate(Timestamp dateTime) {
        for (User user : super.getRepository().findAll()) {
            if (user.getLastTimeLoggedIn().compareTo(dateTime) < 0)
                user.setIsDeleted(true);
            super.getRepository().save(user);
        }
    }

    @Override
    public int deleteUsersMarkedForDeletion() {
        int usersDeletedCount = super.getRepository().countUserByIsDeletedTrue();
        super.getRepository().deleteUsersByIsDeletedTrue();
        return usersDeletedCount;
    }
}
