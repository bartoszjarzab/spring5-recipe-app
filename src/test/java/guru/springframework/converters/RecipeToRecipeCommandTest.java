package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    RecipeToRecipeCommand converter;

    public final Long ID=13L;
    public final String DESCRIPTION = "description";
    public final Integer PREP_TIME = 10;
    public final Integer COOK_TIME = 12;
    public final Integer SERVINGS = 2;
    public final String SOURCE = "source";
    public final String URL = "http://url";
    public final String DIRECTIONS="directions";
    public final Difficulty DIFFICULTY=Difficulty.EASY;
    public final Byte[] IMAGE={1,0,1,1,1,0,1,0,1};
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;




    @Before
    public void setUp() throws Exception {
        NotesToNotesCommand notesToNotesCommand = new NotesToNotesCommand();
        CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);

        converter = new RecipeToRecipeCommand(notesToNotesCommand,categoryToCategoryCommand,ingredientToIngredientCommand);
    }
    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new Recipe()));
    }
    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID_2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommand command = converter.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
    }
}