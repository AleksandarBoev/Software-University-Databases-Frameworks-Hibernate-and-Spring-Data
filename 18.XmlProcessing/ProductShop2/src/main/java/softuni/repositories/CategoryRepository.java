package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "" +
            "SELECT " +
            "   c.name," +
            "   COUNT(p)," +
            "   AVG(p.price), " +
            "   SUM(p.price) " +
            "FROM softuni.domain.entities.Category c " +
            "INNER JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY COUNT(p) DESC "
    )
    List<Object[]> getCategoriesNameProductsCountAveragePriceTotalRevenue();
}
