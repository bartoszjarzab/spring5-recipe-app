package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    CategoryToCategoryCommand converter;
    public final String DESCRIPTION="description";
    public final Long ID=13L;

    @Before
    public void setUp() throws Exception {
        converter=new CategoryToCategoryCommand();
    }
    @Test
    public void testNull()
    {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty()
    {
        assertNotNull(converter.convert(new Category()));
    }
    @Test
    public void convert() {
        Category domain = new Category();
        domain.setDescription(DESCRIPTION);
        domain.setId(ID);
        CategoryCommand command = converter.convert(domain);
        assertNotNull(command);
        assertEquals(ID,command.getId());
        assertEquals(DESCRIPTION,command.getDescription());
    }
}