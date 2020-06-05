package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Autowired
    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe==null){
            return null;
        }
        RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setDescription(recipe.getDescription());
        command.setPrepTime(recipe.getPrepTime());
        command.setCookTime(recipe.getCookTime());
        command.setServings(recipe.getServings());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setDirections(recipe.getDirections());
        command.setDifficulty(recipe.getDifficulty());
        command.setImage(recipe.getImage());
        command.setNotes(
                notesToNotesCommand.convert(recipe.getNotes())
        );
        if(recipe.getCategories()!=null && recipe.getCategories().size()>0){
            recipe.getCategories().forEach(category->command.getCategories().add(categoryToCategoryCommand.convert(category)));
        }
        if(recipe.getIngredients()!=null && recipe.getIngredients().size()>0){
            recipe.getIngredients().forEach(ingredient->command.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }
        return command;
    }
}
