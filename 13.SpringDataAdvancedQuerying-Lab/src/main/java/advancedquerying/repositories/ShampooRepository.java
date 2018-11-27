package advancedquerying.repositories;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.domain.entities.Shampoo;
import advancedquerying.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findShampoosBySizeOrderByIdAsc(Size size);

    List<Shampoo> findShampoosBySizeOrLabelIdOrderByPriceAsc(Size size, Long labelId);

    List<Shampoo> findShampoosByPriceAfterOrderByPriceDesc(BigDecimal price);

    @Query(value = "" +
            "SELECT s FROM advancedquerying.domain.entities.Shampoo s " +
            "INNER JOIN s.ingredients i " +
            "WHERE i.name IN :ingredients"
    )
    List<Shampoo> getShampoosByIncludingIngredients(@Param(value = "ingredients") List<String> ingredients);

    @Query(value = "" +
            "SELECT s FROM advancedquerying.domain.entities.Shampoo s " +
            "INNER JOIN s.ingredients i " +
            "GROUP BY s " +
            "HAVING COUNT(i) < :ingredientsCount"
    )
    List<Shampoo> getShampoosByHavingIngredientsCountLessThan(@Param(value = "ingredientsCount") Long ingredientCount);

    @Query(value = "" +
            "SELECT " +
            "   s.brand, " +
            "   SUM(i.price) " +
            "FROM advancedquerying.domain.entities.Shampoo s " +
            "INNER JOIN s.ingredients i " +
            "WHERE s.brand = :shampooBrand"
    )
    List<Object[]> getShampooBrandAndIngredientsTotalPrice(@Param(value = "shampooBrand") String shampooBrand);
}
