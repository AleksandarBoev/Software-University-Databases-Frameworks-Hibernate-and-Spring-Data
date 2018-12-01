package softuni.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.app.domain.dtos.UserLoginDto;
import softuni.app.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    @Query
    void logInUser(UserLoginDto userLoginDto);
}
