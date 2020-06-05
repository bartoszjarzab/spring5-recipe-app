package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    IngredientCommandToIngredient converter;
    public final Long ID = 13L;
    public final BigDecimal AMOUNT = BigDecimal.valueOf(13);
    public final String DESCRIPTION = "description";
    public final UnitOfMeasureCommand UOM = new UnitOfMeasureCommand();


    @Before
    public void setUp() throws Exception {
        UOM.setId(1L);
        UOM.setDescription("uom description");
        converter=new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new IngredientCommand()));
    }


    @Test
    public void convert() {
        IngredientCommand command = new IngredientCommand();
        command.setId(ID);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        command.setUom(UOM);

        Ingredient domain = converter.convert(command);
        assertNotNull(domain);
        assertEquals(ID,domain.getId());
        assertEquals(AMOUNT,domain.getAmount());
        assertEquals(DESCRIPTION,domain.getDescription());
        assertEquals(UOM.getId(),domain.getUom().getId());
        assertEquals(UOM.getDescription(),domain.getUom().getDescription());


    }
}