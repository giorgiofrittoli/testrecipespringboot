package fritz.test.recepie.services;

import fritz.test.recepie.commands.IngredientCommand;
import fritz.test.recepie.converters.IngredientToIngredientCommand;
import fritz.test.recepie.model.Recipe;
import fritz.test.recepie.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientCommand) {
		this.recipeRepository = recipeRepository;
		ingredientToIngredientCommand = ingredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (!optionalRecipe.isPresent())
			throw new RuntimeException("Recipe not present");

		Recipe recipe = optionalRecipe.get();

		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if (!ingredientCommandOptional.isPresent())
			throw new RuntimeException("Ingredient not present");

		return ingredientCommandOptional.get();

	}

	IngredientService ingredientService;

}
