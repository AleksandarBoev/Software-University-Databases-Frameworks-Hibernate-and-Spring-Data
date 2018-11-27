package advancedquerying.controller;

import advancedquerying.services.interfaces.IngredientService;
import advancedquerying.services.interfaces.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShampooCompanyApplication implements CommandLineRunner {
    private BufferedReader reader;
    private ShampooService shampooService;
    private IngredientService ingredientService;

    @Autowired
    public ShampooCompanyApplication(ShampooService shampooService, IngredientService ingredientService) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.problem01SelectShampoosBySize();
//        this.problem02SelectShampoosBySizeOrLabel();
//        this.problem03SelectShampoosByPrice();
//        this.problem04SelectIngredientsByName();
//        this.problem05SelectIngredientsByNames();
//        this.problem06CountShampoosByPrice();
//        this.problem07SelectShampoosByIngredients();
//        this.problem08SelectShampoosByIngredientsCount();
//        this.problem09SelectIngredientNameAndShampooBrandByName();
//        Ingredient ingredient = new Ingredient("Sasho shampoo", BigDecimal.valueOf(9.99));
//        this.ingredientRepository.save(ingredient);
//        this.problem10DeleteIngredientsByName();
//        this.problem11UpdateIngredientsPrice();
//        this.problem12UpdateIngredientsByNames();
        this.reader.close();
    }

    /*
    Create a method that selects all shampoos with a given size, ordered by shampoo id.
     */
    private void problem01SelectShampoosBySize() throws IOException {
        System.out.print("Enter shampoo size: ");
        String size = this.reader.readLine();

        for (String currentShampooData : this.shampooService.getDataOnShampoosByGivenSizeOrderedByIdAsc(size)) {
            System.out.println(currentShampooData);
        }
    }

    /*
    Create a method that selects all shampoos with a given size or label id. Sort the result ascending by price.
     */
    private void problem02SelectShampoosBySizeOrLabel() throws IOException {
        System.out.print("Enter shampoo size: ");
        String size = this.reader.readLine();
        System.out.print("Enter label id: ");
        Long labelId = Long.parseLong(this.reader.readLine());

        System.out.println(String.join("\n", this.shampooService.getShampoosBySizeOrLabelId(size, labelId)));
    }

    /*
    Create a method that selects all shampoos, which price is higher than a given price. Sort the result descending by price.
     */
    private void problem03SelectShampoosByPrice() throws IOException {
        System.out.println("Enter shampoo price: ");
        String price = this.reader.readLine();

        this.shampooService.getShampoosMoreExpensiveThan(price)
                .forEach(System.out::println);
    }

    /*
    Create a method that selects all ingredients, which name starts with given letters.
     */
    private void problem04SelectIngredientsByName() throws IOException {
        System.out.print("Enter beginning of ingredient name: ");
        String beginning = this.reader.readLine();

        this.ingredientService.getIngredientsWithNamesStartingWith(beginning)
                .forEach(System.out::println);
    }

    /*
    Create a method that selects all ingredients, which are contained in a given list. Sort the result ascending by price.
     */
    private void problem05SelectIngredientsByNames() throws IOException {
        List<String> ingredientsInput = new ArrayList<>();

        System.out.println("Enter ingredients and 'end' when you are done: ");
        String input;
        while (!"end".equals(input = this.reader.readLine()))
            ingredientsInput.add(input);

        this.ingredientService.getIngredientsOrderedByPriceAsc(ingredientsInput)
                .forEach(System.out::println);
    }

    /*
    Create a method that counts all shampoos with price lower than a given price.
     */
    private void problem06CountShampoosByPrice() throws IOException {
        System.out.print("Enter price: ");
        String price = this.reader.readLine();

        System.out.println(this.ingredientService.getCountOfIngredientsCostingLowerThan(price));
    }

    //______________JPQL Querying_______________________________
    /*
    Create a method that selects all shampoos with ingredients included in a given list.
     */
    private void problem07SelectShampoosByIngredients() throws IOException {
        List<String> ingredients = new ArrayList<>();

        System.out.println("Enter ingredients and 'end' when you are done: ");
        String input;
        while (!"end".equals(input = this.reader.readLine()))
            ingredients.add(input);

        this.shampooService.getShampoosWhichContainTheseIngredients(ingredients)
                .forEach(System.out::println);
    }

    /*
    Create a method that selects all shampoos with ingredients less than a given number.
     */
    private void problem08SelectShampoosByIngredientsCount() throws IOException {
        System.out.print("Enter number of ingredients: ");
        Long ingredientsCount = Long.parseLong(this.reader.readLine());

        this.shampooService.getShampoosByHavingIngredientsCountLessThan(ingredientsCount)
                .forEach(System.out::println);
    }

    /*
    Create a method that selects all shampoo names and the sum of their ingredients prices. Use named query.
     */
    private void problem09SelectIngredientNameAndShampooBrandByName() throws IOException {
        System.out.print("Enter shampoo brand: ");
        String shampooBrand = this.reader.readLine();

        List<String> result = this.shampooService.getShampooBrandAndIngredientsTotalPrice(shampooBrand);
        System.out.println("Shampoo brand: " + result.get(0));
        System.out.println("Shampoo ingredients total price: " + result.get(1));
    }

    /*
    Create a method that deletes ingredients by a given name. Use named query.
     */
    private void problem10DeleteIngredientsByName() throws IOException {
        System.out.print("Enter ingredient name: ");
        String ingredientName = this.reader.readLine();

        this.ingredientService.deleteIngredientsByName(ingredientName);
    }

    /*
    Create a method that increases the price of all ingredients by 10%. Use named query.
     */
    private void problem11UpdateIngredientsPrice() throws IOException {
        System.out.print("Enter percentage for update: ");
        String percentage = this.reader.readLine();
        this.ingredientService.updateIngredientsPricesByPercentage(percentage);
    }

    /*
    Create a method that updates the price of all ingredients, which names are in a given list.
     */
    private void problem12UpdateIngredientsByNames() throws IOException {
        List<String> ingredientNames = new ArrayList<>();
        System.out.println("Enter names of ingredients and type in 'end' when everything is entered: ");

        String input;
        while (!"end".equals(input = this.reader.readLine())) {
            ingredientNames.add(input);
        }

        System.out.print("Enter percentage: ");
        String percentage = this.reader.readLine();

        this.ingredientService.updateIngredientsPricesByPercentage(ingredientNames, percentage);
    }
}
