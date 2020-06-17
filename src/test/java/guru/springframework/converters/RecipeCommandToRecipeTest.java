package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
    RecipeCommandToRecipe converter;

    public final String ID="13";
    public final String DESCRIPTION = "description";
    public final Integer PREP_TIME = 10;
    public final Integer COOK_TIME = 12;
    public final Integer SERVINGS = 2;
    public final String SOURCE = "source";
    public final String URL = "http://url";
    public final String DIRECTIONS="directions";
    public final Difficulty DIFFICULTY=Difficulty.EASY;
    public final Byte[] IMAGE={1,0,1,1,1,0,1,0,1};
    public static final String CAT_ID_1 = "1";
    public static final String CAT_ID_2 = "2";
    public static final String INGRED_ID_1 = "3";
    public static final String INGRED_ID_2 = "4";
    public static final String NOTES_ID = "5";




    @Before
    public void setUp() throws Exception {
        NotesCommandToNotes notesCommandToNotes = new NotesCommandToNotes();
        CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();
        UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);

        converter = new RecipeCommandToRecipe(notesCommandToNotes,categoryCommandToCategory,ingredientCommandToIngredient);
    }
    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new RecipeCommand()));
    }
    @Test
    public void convert() {
        //given
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID_2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        Recipe domain = converter.convert(recipe);

        //then
        assertNotNull(domain);
        assertEquals(ID, domain.getId());
        assertEquals(COOK_TIME, domain.getCookTime());
        assertEquals(PREP_TIME, domain.getPrepTime());
        assertEquals(DESCRIPTION, domain.getDescription());
        assertEquals(DIFFICULTY, domain.getDifficulty());
        assertEquals(DIRECTIONS, domain.getDirections());
        assertEquals(SERVINGS, domain.getServings());
        assertEquals(SOURCE, domain.getSource());
        assertEquals(URL, domain.getUrl());
        assertEquals(NOTES_ID, domain.getNotes().getId());
        assertEquals(2, domain.getCategories().size());
        assertEquals(2, domain.getIngredients().size());
    }
}