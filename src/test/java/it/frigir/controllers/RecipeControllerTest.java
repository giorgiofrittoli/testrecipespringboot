package it.frigir.controllers;


import it.frigir.commands.RecipeCommand;
import it.frigir.exceptions.NotFoundException;
import it.frigir.model.Recipe;
import it.frigir.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {


	@Mock
	RecipeService recipeService;

	RecipeController controller;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void testGetRecipe() throws Exception {

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testGetNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();

		mockMvc.perform(get("/recipe/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/form"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = RecipeCommand.builder()
				.id(1L).cookTime(1).prepTime(1).servings(1).directions("sadsdasdasda")
				.description("aaaa").build();

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/show"));
	}

	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		mockMvc.perform(get("/recipe/1/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/form"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testDeleteAction() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		verify(recipeService, times(1)).deleteById(anyLong());
	}

	@Test
	public void testGetRecipeNotFound() throws Exception {

		when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/999/show"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("404Error"))
				.andExpect(model().attributeExists("exception"));
	}

	//@Test(expected = NumberFormatException.class)
	public void testGetRecipeNumberFormatEx() throws Exception {

//
//		mockMvc.perform(get("/recipe/asd/show"))
//				.andExpect(status().isBadRequest())
//				.andExpect(view().name("400Error"))
//				.andExpect(model().attributeExists("exception"));
	}
}