package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {
    CategoryCommandToCategory converter;
    public final String DESCRIPTION="description";
    public final String ID="13";

    @Before
    public void setUp() throws Exception {
        converter=new CategoryCommandToCategory();
    }
    @Test
    public void testNull()
    {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty()
    {
        assertNotNull(converter.convert(new CategoryCommand()));
    }
    @Test
    public void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setDescription(DESCRIPTION);
        command.setId(ID);
        Category domain = converter.convert(command);
        assertNotNull(domain);
        assertEquals(ID,domain.getId());
        assertEquals(DESCRIPTION,domain.getDescription());
    }
}