package app.repositories;

import app.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> getUsersByEmailEndsWith(String emailProvider);

    Iterable<User> getUsersByIsDeletedTrue();

    @Transactional
    void deleteUsersByIsDeletedTrue();

    Integer countUserByIsDeletedTrue();
}
