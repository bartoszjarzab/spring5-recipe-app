package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {
    public final String RECIPE_NOTES="notes";
    public final Long ID=13L;
    NotesToNotesCommand converter;
    @Before
    public void setUp() throws Exception {
        converter=new NotesToNotesCommand();
    }
    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new Notes()));
    }
    @Test
    public void convert() {

        Notes domain = new Notes();
        domain.setId(ID);
        domain.setRecipeNotes(RECIPE_NOTES);
        NotesCommand command = converter.convert(domain);

        assertNotNull(command);
        assertEquals(ID,command.getId());
        assertEquals(RECIPE_NOTES,command.getRecipeNotes());

    }
}