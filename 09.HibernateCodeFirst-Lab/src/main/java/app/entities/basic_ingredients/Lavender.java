package app.entities.basic_ingredients;

import app.root_entities.BasicIngredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("lavender")
public class Lavender extends BasicIngredient {
    private static final String NAME = "Lavender";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2);

    public Lavender() {
        super(NAME, PRICE);
    }
}
