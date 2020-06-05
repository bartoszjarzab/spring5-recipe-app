package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {
    public final String RECIPE_NOTES="notes";
    public final Long ID=13L;
    NotesCommandToNotes converter;
    @Before
    public void setUp() throws Exception {
        converter=new NotesCommandToNotes();
    }
    @Test
    public void testNull(){

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){

        assertNotNull(converter.convert(new NotesCommand()));
    }
    @Test
    public void convert() {

        NotesCommand command = new NotesCommand();
        command.setId(ID);
        command.setRecipeNotes(RECIPE_NOTES);
        Notes domain = converter.convert(command);

        assertNotNull(domain);
        assertEquals(ID,domain.getId());
        assertEquals(RECIPE_NOTES,domain.getRecipeNotes());

    }
}