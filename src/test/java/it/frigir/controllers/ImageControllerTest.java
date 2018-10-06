package it.frigir.controllers;

import it.frigir.commands.RecipeCommand;
import it.frigir.services.ImageService;
import it.frigir.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest {

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	MockMvc mockMvc;

	ImageController imageController;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		imageController = new ImageController(recipeService);

		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	@Test
	public void getImageForm() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(1L)).thenReturn(recipeCommand);

		//when
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));

		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}
}