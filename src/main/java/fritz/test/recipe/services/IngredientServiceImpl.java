package fritz.test.recipe.services;

import fritz.test.recipe.commands.IngredientCommand;
import fritz.test.recipe.converters.IngredientCommandToIngredient;
import fritz.test.recipe.converters.IngredientToIngredientCommand;
import fritz.test.recipe.model.Ingredient;
import fritz.test.recipe.model.Recipe;
import fritz.test.recipe.repositories.RecipeRepository;
import fritz.test.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		ingredientToIngredientCommand = ingredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (!optionalRecipe.isPresent())
			throw new RuntimeException("Recipe not present");

		Recipe recipe = optionalRecipe.get();

		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if (!ingredientCommandOptional.isPresent())
			throw new RuntimeException("Ingredient not present");

		return ingredientCommandOptional.get();

	}

	@Transactional
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!optionalRecipe.isPresent()) {
			log.debug("Recipe not found");
			throw new RuntimeException("Recipe not found");
		} else {

			Recipe recipe = optionalRecipe.get();
			Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					.findFirst();

			if (!optionalIngredient.isPresent()) {
				//new ingredient, add to recipe
				recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
			} else {
				//update ingredient, with command values
				Ingredient ingredient = optionalIngredient.get();
				ingredient.setDescription(ingredientCommand.getDescription());
				ingredient.setAmount(ingredientCommand.getAmount());
				ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand
						.getUnitOfMeasure().getId())
						.orElseThrow(() -> new RuntimeException("Unit of Measure not foung")));
			}

			Recipe savedRecipe = recipeRepository.save(recipe);
			Optional<Ingredient> savedOptionalIngredient = savedRecipe.getIngredients()
					.stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

			if (!savedOptionalIngredient.isPresent()) {
				savedOptionalIngredient = savedRecipe.getIngredients().stream()
						.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
						.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
						.filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
						.findFirst();
			}

			return ingredientToIngredientCommand.convert(savedOptionalIngredient.get());
		}

	}


}
