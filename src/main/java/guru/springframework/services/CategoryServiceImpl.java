package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryToCategoryCommand categoryToCategoryCommand;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public List<CategoryCommand> listAllCategoryCommands() {
        log.debug("Inside CategoryServiceImpl function listAllCategoryCommands");
        List<CategoryCommand> categoryCommandList = StreamSupport.stream(categoryRepository.findAll().spliterator(),false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toList());
        log.debug("Number of categories: " +categoryCommandList.size());

        return categoryCommandList;
    }

    @Override
    public CategoryCommand findByDescription(String description) {
        return categoryToCategoryCommand.convert(categoryRepository.findByDescription(description).get());
    }

}
