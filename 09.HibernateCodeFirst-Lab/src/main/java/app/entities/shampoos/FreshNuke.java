package app.entities.shampoos;

import app.enums.Size;
import app.root_entities.BasicLabel;
import app.root_entities.BasicShampoo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "fresh nuke")
public class FreshNuke extends BasicShampoo {
    private static final String BRAND = "Fresh Nuke";
    private static final BigDecimal PRICE = BigDecimal.valueOf(9.33);
    private static final Size SIZE = Size.LARGE;

    public FreshNuke() {
    }

    public FreshNuke(BasicLabel label) {
        super(BRAND, PRICE, SIZE, label);
    }
}
