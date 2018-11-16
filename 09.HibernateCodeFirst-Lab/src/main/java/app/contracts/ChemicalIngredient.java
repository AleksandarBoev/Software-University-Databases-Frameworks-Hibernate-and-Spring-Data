package app.contracts;

public interface ChemicalIngredient extends Ingredient {
    String getChemicalFormula();

    void setChemicalFormula(String chemicalFormula);
}
