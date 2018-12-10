package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String categoryName);

    //Categories by count of items
    //Export all categories by count of items:
    //•	Extract from the database, categories’ names and items in the
    // categories with their name and price.
    //•	Order them descending by count of items in each category, and if
    // two or more categories have same number of items, order them
    // descending by sum of the items’ prices in each category.
    //•	The format is described below:

    @Query(value = "" +
            "SELECT c FROM alararestaurant.domain.entities.Category c " +
            "LEFT JOIN c.items i " + //export ALL categories
            "GROUP BY c " +
            "ORDER BY COUNT(i) DESC, SUM(i.price) DESC "
    )
    List<Category> getExportCategories();
}
