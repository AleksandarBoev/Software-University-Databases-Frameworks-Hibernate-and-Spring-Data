package app.entities.chemical_ingredients;

import app.root_entities.BasicChemicalIngredient;

import java.math.BigDecimal;

public class AmmoniumChloride extends BasicChemicalIngredient {
    private static final String NAME = "Ammonium Chloride";
    private static final BigDecimal PRICE = BigDecimal.valueOf(0.59);
    private static final String CHEMICAL_FORMULA = "NH4Cl";

    public AmmoniumChloride() {
        super(NAME, PRICE, CHEMICAL_FORMULA);
    }
}
