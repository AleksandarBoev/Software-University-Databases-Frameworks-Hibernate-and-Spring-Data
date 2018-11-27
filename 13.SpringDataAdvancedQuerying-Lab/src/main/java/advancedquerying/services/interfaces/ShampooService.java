package advancedquerying.services.interfaces;

import advancedquerying.repositories.ShampooRepository;

import java.util.List;

public interface ShampooService {
    List<String> getDataOnShampoosByGivenSizeOrderedByIdAsc(String size);

    List<String> getShampoosBySizeOrLabelId(String size, Long labelId);

    List<String> getShampoosMoreExpensiveThan(String price);

    List<String> getShampoosWhichContainTheseIngredients(List<String> ingredients);

    List<String> getShampoosByHavingIngredientsCountLessThan(Long ingredientsCount);

    List<String> getShampooBrandAndIngredientsTotalPrice(String shampooBrand);
}
