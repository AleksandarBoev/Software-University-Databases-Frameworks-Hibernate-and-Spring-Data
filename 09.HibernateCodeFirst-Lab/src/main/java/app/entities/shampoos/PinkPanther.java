package app.entities.shampoos;

import app.enums.Size;
import app.root_entities.BasicLabel;
import app.root_entities.BasicShampoo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "pink panther")
public class PinkPanther extends BasicShampoo {
    private static final String BRAND = "Pink Panther";
    private static final BigDecimal PRICE = BigDecimal.valueOf(8.50);
    private static final Size SIZE = Size.MEDIUM;

    public PinkPanther() {
    }

    public PinkPanther(BasicLabel label) {
        super(BRAND, PRICE, SIZE, label);
    }
}
