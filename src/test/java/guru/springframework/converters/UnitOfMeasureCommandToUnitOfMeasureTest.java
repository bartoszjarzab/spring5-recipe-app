package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;
    public final String DESCRIPTION="description";
    public final String ID="13";

    @Before
    public void setUp() throws Exception {
        converter=new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNull(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);
        UnitOfMeasure domain=converter.convert(command);

        assertNotNull(domain);
        assertEquals(ID,domain.getId());
        assertEquals(DESCRIPTION,domain.getDescription());
    }
}