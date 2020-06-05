package guru.springframework.converters;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Autowired
    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes, CategoryCommandToCategory categoryCommandToCategory, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand==null){
            return null;
        }
        Recipe domain = new Recipe();
        domain.setId(recipeCommand.getId());
        domain.setDescription(recipeCommand.getDescription());
        domain.setPrepTime(recipeCommand.getPrepTime());
        domain.setCookTime(recipeCommand.getCookTime());
        domain.setServings(recipeCommand.getServings());
        domain.setSource(recipeCommand.getSource());
        domain.setUrl(recipeCommand.getUrl());
        domain.setDirections(recipeCommand.getDirections());
        domain.setDifficulty(recipeCommand.getDifficulty());
        domain.setImage(recipeCommand.getImage());
        domain.setNotes(
                notesCommandToNotes.convert(recipeCommand.getNotes())
        );
        if(recipeCommand.getCategories()!=null && recipeCommand.getCategories().size()>0){
            recipeCommand.getCategories().forEach(category->domain.getCategories().add(categoryCommandToCategory.convert(category)));
        }
        if(recipeCommand.getIngredients()!=null && recipeCommand.getIngredients().size()>0){
            recipeCommand.getIngredients().forEach(ingredient->domain.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
        }
        return domain;
    }
}
