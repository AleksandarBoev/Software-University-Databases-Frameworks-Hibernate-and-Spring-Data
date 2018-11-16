package app.entities.basic_ingredients;

import app.root_entities.BasicIngredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "mint")
public class Mint extends BasicIngredient {
    private static final String NAME = "Mint";
    private static final BigDecimal PRICE = BigDecimal.valueOf(3.54);

    public Mint() {
        super(NAME, PRICE);
    }
}
