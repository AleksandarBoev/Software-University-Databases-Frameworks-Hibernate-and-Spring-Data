package app.services.interfaces;

import app.models.User;

import java.sql.Timestamp;

public interface UserService extends BaseService<User> {
    Iterable<User> getUsersByEmailProvider(String emailProvider);

    Iterable<User> getUsersMarkedForDeletion();

    void markUsersForDeletionBeforeDate(Timestamp dateTime);

    int deleteUsersMarkedForDeletion();
}
