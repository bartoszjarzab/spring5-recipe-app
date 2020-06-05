package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;
    public final String DESCRIPTION="description";
    public final Long ID=13L;

    @Before
    public void setUp() throws Exception {
        converter=new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNull(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        UnitOfMeasure domain = new UnitOfMeasure();
        domain.setId(ID);
        domain.setDescription(DESCRIPTION);
        UnitOfMeasureCommand command=converter.convert(domain);

        assertNotNull(command);
        assertEquals(ID,command.getId());
        assertEquals(DESCRIPTION,command.getDescription());
    }
}