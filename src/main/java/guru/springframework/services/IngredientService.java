package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(String recipeId, String ingredientId);
}
