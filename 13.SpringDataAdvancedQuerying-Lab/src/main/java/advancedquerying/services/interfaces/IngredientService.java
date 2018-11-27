package advancedquerying.services.interfaces;

import java.util.List;

public interface IngredientService {
    List<String> getIngredientsWithNamesStartingWith(String beginningOfName);

    //Should services work ONLY with strings?(return type and params) Maybe instead of a List<String>
    //it should be a single string, containing '\n' and the service implementation will
    //take care of the rest.
    List<String> getIngredientsOrderedByPriceAsc(List<String> ingredientNames);

    String getCountOfIngredientsCostingLowerThan(String price);

    void deleteIngredientsByName(String name);

    void updateIngredientsPricesByPercentage(String percentage);

    void updateIngredientsPricesByPercentage(List<String> ingredientNames, String percentage);
}
