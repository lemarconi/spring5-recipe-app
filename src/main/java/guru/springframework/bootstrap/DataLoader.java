package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by lgm on 08/25/20
 */
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if (!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if (!tableSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if (!teaSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if (!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if (!pintUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if (!cupsUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //get categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.MODERATE);
        guacRecipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it.");

        // bidirecional relationship
        //guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaSpoonUom ));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tableSpoonUom ));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom ));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom ));
        guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tableSpoonUom ));

        guacRecipe.setServings(2);

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        guacRecipe.setSource("simplyrecipes.com");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        recipes.add(guacRecipe);


        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos Recipe");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setDifficulty(Difficulty.EASY);
        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
         "\n" +
         "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
         "\n" +
         "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
         "\n" +
         "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
         "\n" +
         "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
         "\n" +
         "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
         "\n" +
         "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        //add notes
        Notes tacosNotes = new Notes();

        tacosNotes.setRecipeNotes("I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t" +
                "traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.");

        //bidirecional relationship
        //tacosNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacosNotes);

        tacosRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("salt", new BigDecimal(".5"), teaSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("clove garlic, finely chopped\n", new BigDecimal(1), eachUom ));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom ));
        tacosRecipe.addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal(4), eachUom ));

        tacosRecipe.setServings(4);

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        tacosRecipe.setSource("simplyrecipes.com");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        //add to the list
        recipes.add(tacosRecipe);
        
        return recipes;

    }

}
