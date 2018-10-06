package it.frigir.converters;

import it.frigir.commands.IngredientCommand;
import it.frigir.commands.RecipeCommand;
import it.frigir.model.*;
import it.frigir.repositories.RecipeRepository;
import it.frigir.repositories.UnitOfMeasureRepository;
import it.frigir.services.IngredientService;
import it.frigir.services.IngredientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class RecipeToRecipeCommandTest {

	public static final Long RECIPE_ID = 1L;
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final String DESCRIPTION = "My Recipe";
	public static final String DIRECTIONS = "Directions";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "Source";
	public static final String URL = "Some URL";
	public static final Long CAT_ID_1 = 1L;
	public static final Long CAT_ID2 = 2L;
	public static final Long INGRED_ID_1 = 3L;
	public static final Long INGRED_ID_2 = 4L;
	public static final Long NOTES_ID = 9L;
	RecipeToRecipeCommand converter;

	@Mock
	RecipeRepository recipeRepository;
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

	IngredientService ingredientService;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);

		converter = new RecipeToRecipeCommand(
				new CategoryToCategoryCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NotesToNotesCommand());
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Recipe()));
	}

	@Test
	public void convert() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		recipe.setCookTime(COOK_TIME);
		recipe.setPrepTime(PREP_TIME);
		recipe.setDescription(DESCRIPTION);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setDirections(DIRECTIONS);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);

		Notes notes = new Notes();
		notes.setId(NOTES_ID);

		recipe.setNotes(notes);

		Category category = new Category();
		category.setId(CAT_ID_1);

		Category category2 = new Category();
		category2.setId(CAT_ID2);

		recipe.getCategories().add(category);
		recipe.getCategories().add(category2);

		Ingredient ingredient = new Ingredient();
		ingredient.setId(INGRED_ID_1);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(INGRED_ID_2);

		recipe.getIngredients().add(ingredient);
		recipe.getIngredients().add(ingredient2);

		//when
		RecipeCommand command = converter.convert(recipe);

		//then
		assertNotNull(command);
		assertEquals(RECIPE_ID, command.getId());
		assertEquals(COOK_TIME, command.getCookTime());
		assertEquals(PREP_TIME, command.getPrepTime());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(DIFFICULTY, command.getDifficulty());
		assertEquals(DIRECTIONS, command.getDirections());
		assertEquals(SERVINGS, command.getServings());
		assertEquals(SOURCE, command.getSource());
		assertEquals(URL, command.getUrl());
		assertEquals(NOTES_ID, command.getNotes().getId());
		assertEquals(2, command.getCategories().size());
		assertEquals(2, command.getIngredients().size());

	}

	@Test
	public void testSaveRecipeCommand() {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		ingredientCommand.setRecipeId(1L);

		Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(1L);
		//savedRecipe.addIngredient(Ingredient.builder().id(1L).build());

		when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);

		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

		//then
		assertEquals(Long.valueOf(1L), savedIngredientCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));


	}
}