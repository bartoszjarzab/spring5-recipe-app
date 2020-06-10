package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    ImageController controller;
    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller=new ImageController(imageService,recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }
    @Test
    public void getImageForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService,times(1)).findCommandById(anyLong());
    }
    @Test
    public void handleImagePost() throws Exception {

        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imagefile","testing.txt","text/plain","Testing phrase".getBytes());
        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/1/show"));
        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }
    @Test
    public void renderImageFromDbTest() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s="quasi-imagefile";
        Byte[] byteObject = new Byte[s.getBytes().length];

        int i =0;
        for(byte b: s.getBytes()){
            byteObject[i++]=b;
        }
        recipeCommand.setImage(byteObject);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length,responseBytes.length);

    }
    @Test
    public void getImageNumberFormatException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/asd/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}