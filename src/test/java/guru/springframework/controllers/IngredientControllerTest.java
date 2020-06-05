package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    IngredientController controller;
    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller=new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }
    @Test
    public void listIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();


        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService,times(1)).findCommandById(anyLong());
    }
}