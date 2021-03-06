package it.frigir.controllers;

import it.frigir.commands.IngredientCommand;
import it.frigir.commands.RecipeCommand;
import it.frigir.services.IngredientService;
import it.frigir.services.RecipeService;
import it.frigir.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	@Mock
	IngredientService ingredientService;
	@Mock
	UnitOfMeasureService unitOfMeasureService;

	IngredientController ingredientController;

	MockMvc mockMvc;

	@Before
	public void SetUp() {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void testLisIngredient() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		//when
		mockMvc.perform(get("/recipe/1/ingredient/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));

		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testShowIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		//then
		mockMvc.perform(get("/recipe/1/ingredient/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
	}

	@Test
	public void testUpdateIngredientForm() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.getAllUnitOfMeasure()).thenReturn(new HashSet<>());

		//then
		mockMvc.perform(get("/recipe/1/ingredient/1/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/form"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("unitOfMeasureList"));


	}

	@Test
	public void testNewIngredientForm() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		//when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.getAllUnitOfMeasure()).thenReturn(new HashSet<>());

		//then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/form"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("unitOfMeasureList"));


	}

	@Test
	public void testDeleteIngredient() throws Exception {
		mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));

		verify(ingredientService, times(1)).deleteIngredient(anyLong(), anyLong());
	}
}
