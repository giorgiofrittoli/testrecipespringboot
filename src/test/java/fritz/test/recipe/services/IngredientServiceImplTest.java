package fritz.test.recipe.services;

import fritz.test.recipe.commands.IngredientCommand;
import fritz.test.recipe.converters.IngredientCommandToIngredient;
import fritz.test.recipe.converters.IngredientToIngredientCommand;
import fritz.test.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import fritz.test.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import fritz.test.recipe.model.Ingredient;
import fritz.test.recipe.model.Recipe;
import fritz.test.recipe.repositories.RecipeRepository;
import fritz.test.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

	@Mock
	RecipeRepository recipeRepository;
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	IngredientService ingredientService;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
	}

	@Test
	public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(1L);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		//when
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testDeleteIngredient() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Ingredient ingredient = new Ingredient();
		ingredient.setRecipe(recipe);
		ingredient.setId(1L);
		recipe.addIngredient(ingredient);
		Optional<Recipe> optionalRecipe = Optional.of(recipe);

		//when
		when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
		ingredientService.deleteIngredient(1L, 1L);

		//then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));


	}
}