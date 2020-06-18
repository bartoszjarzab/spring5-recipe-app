package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;

import java.util.List;

public interface CategoryService {
    List<CategoryCommand> listAllCategoryCommands();
    CategoryCommand findByDescription(String description);
}
