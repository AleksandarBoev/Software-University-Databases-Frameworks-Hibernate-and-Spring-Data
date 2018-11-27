package advancedquerying.services.implementations;

import advancedquerying.domain.entities.Shampoo;
import advancedquerying.domain.entities.Size;
import advancedquerying.repositories.IngredientRepository;
import advancedquerying.repositories.ShampooRepository;
import advancedquerying.services.interfaces.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl extends BaseServiceImpl<Shampoo, ShampooRepository> implements ShampooService {
    private IngredientRepository ingredientRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository repository, IngredientRepository ingredientRepository) {
        super(repository);
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public List<String> getDataOnShampoosByGivenSizeOrderedByIdAsc(String size) {
        Size shampooSize = Size.valueOf(size.toUpperCase());

        List<Shampoo> shampoos = super.getRepository().findShampoosBySizeOrderByIdAsc(shampooSize);

        return this.getShampoosInfo(shampoos);
    }

    @Override
    public List<String> getShampoosBySizeOrLabelId(String size, Long labelId) {
        Size shampooSize = Size.valueOf(size.toUpperCase());

        List<Shampoo> shampoos = super.getRepository().findShampoosBySizeOrLabelIdOrderByPriceAsc(shampooSize, labelId);

        return this.getShampoosInfo(shampoos);
    }

    @Override
    public List<String> getShampoosMoreExpensiveThan(String price) {
        BigDecimal shampooPrice = new BigDecimal(price);

        List<Shampoo> shampoos = super.getRepository().findShampoosByPriceAfterOrderByPriceDesc(shampooPrice);

        return this.getShampoosInfo(shampoos);
    }

    @Override
    public List<String> getShampoosWhichContainTheseIngredients(List<String> ingredients) {
        return super.getRepository().getShampoosByIncludingIngredients(ingredients)
                .stream()
                .map(s -> s.getBrand())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getShampoosByHavingIngredientsCountLessThan(Long ingredientsCount) {
        return super.getRepository().getShampoosByHavingIngredientsCountLessThan(ingredientsCount)
                .stream()
                .map(s -> s.getBrand())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getShampooBrandAndIngredientsTotalPrice(String shampooBrand) {
        return Arrays.stream(super.getRepository().getShampooBrandAndIngredientsTotalPrice(shampooBrand).get(0))
                .map(o -> "" + o)
                .collect(Collectors.toList());

    }

    private List<String> getShampoosInfo(List<Shampoo> shampoos) {
        return shampoos.stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(), s.getSize().name(), s.getPrice()))
                .collect(Collectors.toList());
    }
}
