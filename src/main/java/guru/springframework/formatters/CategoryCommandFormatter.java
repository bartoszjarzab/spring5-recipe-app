package guru.springframework.formatters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class CategoryCommandFormatter implements Formatter<CategoryCommand> {

    private final CategoryService categoryService;

    @Autowired
    public CategoryCommandFormatter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryCommand parse(String s, Locale locale) throws ParseException {
        CategoryCommand foundCategoryCommand = categoryService.findByDescription(s);
        if(foundCategoryCommand!=null){
            return foundCategoryCommand;
        }
        throw new ParseException("Could not find category: "+s,0);
    }

    @Override
    public String print(CategoryCommand categoryCommand, Locale locale) {

        return categoryCommand.getDescription();
    }
}
