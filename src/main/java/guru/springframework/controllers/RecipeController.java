package guru.springframework.controllers;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.CategoryService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping({"/recipe"})
public class RecipeController {
    private static final String RECIPE_RECIPEFORM_URL="recipe/recipeform";
    private final RecipeService recipeService;
    private final CategoryService categoryService;


    @Autowired
    public RecipeController(RecipeService recipeService,CategoryService categoryService) {
        log.debug("Inside recipe controller constructor...");
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }
    @GetMapping({"/{id}/show"})
    public String getById(@PathVariable String id, Model model){
        log.debug("About to return index.html to webpage...");
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/show";
    }
    @GetMapping({"/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));
        model.addAttribute("allCategories",categoryService.listAllCategoryCommands());
        return RECIPE_RECIPEFORM_URL;
    }
    @GetMapping("/new")
    public String newRecipe(Model model){
        RecipeCommand newRecipeCommand = new RecipeCommand();
        newRecipeCommand.setId(UUID.randomUUID().toString());
        model.addAttribute("allCategories",categoryService.listAllCategoryCommands());
        model.addAttribute("recipe", newRecipeCommand);
        return RECIPE_RECIPEFORM_URL;
    }
    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(
                    objectError -> log.debug(objectError.toString())
            );
            return RECIPE_RECIPEFORM_URL;
        }
////////////// BLOCK DUE TO SOME BUG REGARDING MONGODB NOT KEEPING ID's
        List<CategoryCommand> tempCategories = new ArrayList<>();
        command.getCategories().forEach(categoryCommand -> {
            if(categoryCommand.getDescription()!=null){
                tempCategories.add(categoryService.findByDescription(categoryCommand.getDescription()));
            }
        });
        command.getCategories().clear();
        command.setCategories(tempCategories);
/////////////
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundError(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",exception);

        return modelAndView;
    }

}
