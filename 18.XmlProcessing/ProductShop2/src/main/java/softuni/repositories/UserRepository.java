package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Get all users, who have at least 1 item sold with a buyer. Order them by last name,
    // then by first name. Select the person's first and last name. For each of the products
    // sold (products with buyers), select the product's name, price and the buyer's first and last name.

    @Query(value = "" +
            "SELECT u FROM softuni.domain.entities.User u " +
            "INNER JOIN u.productsSold p " +
            "WHERE p.buyer IS NOT NULL " +
            "ORDER BY u.lastName ASC, u.firstName ASC "
    )
    List<User> findUsersWhoHaveSoldAProduct();

    @Query(value = "" +
            "SELECT u FROM softuni.domain.entities.User u " +
            "INNER JOIN u.productsSold p " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u " +
            "ORDER BY COUNT(p) DESC, u.lastName ASC "
    )
    List<User> findUsersWhoHaveSoldAProductOrderedByProductsSoldDescLastNameAsc();
}
