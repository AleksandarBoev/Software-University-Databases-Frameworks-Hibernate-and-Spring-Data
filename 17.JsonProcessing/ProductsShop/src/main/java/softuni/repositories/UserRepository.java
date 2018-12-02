package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
