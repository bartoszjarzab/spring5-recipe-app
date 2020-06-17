package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    IngredientToIngredientCommand converter;
    public final String ID = "13";
    public final BigDecimal AMOUNT = BigDecimal.valueOf(13);
    public final String DESCRIPTION = "description";
    public final UnitOfMeasure UOM = new UnitOfMeasure();


    @Before
    public void setUp() throws Exception {
        UOM.setId("1");
        UOM.setDescription("uom description");
        converter=new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new Ingredient()));
    }


    @Test
    public void convert() {
        Ingredient domain = new Ingredient();
        domain.setId(ID);
        domain.setAmount(AMOUNT);
        domain.setDescription(DESCRIPTION);
        domain.setUom(UOM);

        IngredientCommand command = converter.convert(domain);
        assertNotNull(command);
        assertEquals(ID,command.getId());
        assertEquals(AMOUNT,command.getAmount());
        assertEquals(DESCRIPTION,command.getDescription());
        assertEquals(UOM.getId(),command.getUom().getId());
        assertEquals(UOM.getDescription(),command.getUom().getDescription());


    }
}