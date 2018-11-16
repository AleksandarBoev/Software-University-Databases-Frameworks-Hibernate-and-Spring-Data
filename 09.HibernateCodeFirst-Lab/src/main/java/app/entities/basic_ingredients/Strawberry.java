package app.entities.basic_ingredients;

import app.root_entities.BasicIngredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "strawberry")
public class Strawberry extends BasicIngredient {
    private static final String NAME = "Strawberry";
    private static final BigDecimal PRICE = BigDecimal.valueOf(4.85);

    public Strawberry() {
        super(NAME, PRICE);
    }
}
