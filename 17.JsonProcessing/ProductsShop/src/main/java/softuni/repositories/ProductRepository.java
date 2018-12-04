package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "" +
            "SELECT " +
            "   p.name, " +
            "   p.price, " +
            "   CONCAT(p.seller.firstName, ' ', p.seller.lastName) AS fullName " +
            "FROM softuni.domain.entities.Product p " +
            "WHERE p.price BETWEEN :price1 AND :price2 AND p.buyer IS NULL " +
            "ORDER BY p.price ASC "
    )
    List<Object[]> findProductsByPriceBetweenWithoutBuyerOrderedByPriceAsc(@Param(value = "price1") BigDecimal price1, @Param(value = "price2") BigDecimal price2);
}
