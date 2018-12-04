package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //TODO for some reason the returned users all have null values for firstName. Everything else is ok.
    @Query(value = "" +
            "SELECT u FROM softuni.domain.entities.Product p " +
            "INNER JOIN p.seller u " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u " +
            "ORDER BY u.firstName ASC, u.lastName ASC"
    )
    List<User> findUsersByAtLeastOneSoldItemOrderByLastNameThenByFirstName();


    @Query(value = "" +
            "SELECT u FROM softuni.domain.entities.User u " +
            "INNER JOIN u.productsSold p " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u " +
            "ORDER BY COUNT(p) DESC, u.lastName ASC "
    )
    List<User> findUsersByProductSoldOrderByProductsSoldDescLastNameAsc();
}
