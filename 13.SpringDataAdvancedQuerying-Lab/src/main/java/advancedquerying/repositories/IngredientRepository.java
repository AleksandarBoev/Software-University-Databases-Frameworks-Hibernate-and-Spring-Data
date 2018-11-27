package advancedquerying.repositories;

import advancedquerying.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByNameStartsWith(String beginningOfName);

    List<Ingredient> findIngredientsByNameInOrderByPriceAsc(List<String> ingredientNames);

    Integer countIngredientsByPriceBefore(BigDecimal price);

    Ingredient findIngredientByName(String name);

    @Modifying
    @Transactional
    @Query(value = "" +
            "DELETE FROM advancedquerying.domain.entities.Ingredient i " +
            "WHERE i.name = :ingredientName"
    )
    void deleteIngredientsByName(@Param(value = "ingredientName") String name);

    @Modifying
    @Transactional
    @Query(value = "" +
            "UPDATE advancedquerying.domain.entities.Ingredient i " +
            "SET i.price = i.price + i.price * :percentage " +
            "WHERE i.id > 0 "
    )
    void updateIngredientsPrice(@Param(value = "percentage") BigDecimal percentage);

    @Modifying
    @Transactional
    @Query(value = "" +
            "UPDATE advancedquerying.domain.entities.Ingredient i " +
            "SET i.price = i.price + i.price * :percentage " +
            "WHERE i.name IN :ingredients"
    )
    void updatePricesOfIngredientsWithNames(@Param(value = "ingredients") List<String> ingredientNames, @Param(value = "percentage") BigDecimal percentage);
}
