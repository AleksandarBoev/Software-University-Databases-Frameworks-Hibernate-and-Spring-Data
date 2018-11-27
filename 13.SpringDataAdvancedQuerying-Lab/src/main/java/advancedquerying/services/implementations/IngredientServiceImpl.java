package advancedquerying.services.implementations;

import advancedquerying.domain.entities.Ingredient;
import advancedquerying.repositories.IngredientRepository;
import advancedquerying.services.interfaces.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl extends BaseServiceImpl<Ingredient, IngredientRepository> implements IngredientService {
    @Autowired
    public IngredientServiceImpl(IngredientRepository repository) {
        super(repository);
    }

    @Override
    public List<String> getIngredientsWithNamesStartingWith(String beginningOfName) {
        return this.getIngredientsInfo(super.getRepository().findIngredientsByNameStartsWith(beginningOfName));
    }

    @Override
    public List<String> getIngredientsOrderedByPriceAsc(List<String> ingredientNames) {
        return this.getIngredientsInfo(super.getRepository()
                        .findIngredientsByNameInOrderByPriceAsc(ingredientNames));
    }

    @Override
    public String getCountOfIngredientsCostingLowerThan(String price) {
        BigDecimal ingredientPrice = new BigDecimal(price);

        return "" + super.getRepository().countIngredientsByPriceBefore(ingredientPrice);
    }

    @Override
    public void deleteIngredientsByName(String name) {
        super.getRepository().deleteIngredientsByName(name);
    }

    @Override
    public void updateIngredientsPricesByPercentage(String percentage) {
        BigDecimal percentageUpdate = new BigDecimal(percentage).multiply(BigDecimal.valueOf(0.01));
        super.getRepository().updateIngredientsPrice(percentageUpdate);
    }

    @Override
    public void updateIngredientsPricesByPercentage(List<String> ingredientNames, String percentage) {
        BigDecimal percentageUpdate = new BigDecimal(percentage).multiply(BigDecimal.valueOf(0.01));
        super.getRepository().updatePricesOfIngredientsWithNames(ingredientNames, percentageUpdate);
    }

    private List<String> getIngredientsInfo(List<Ingredient> ingredients) {
        return ingredients.stream().map(i -> i.getName()).collect(Collectors.toList());
    }
}
