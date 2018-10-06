package fritz.test.recipe.services;

import fritz.test.recipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	void deleteIngredient(Long recipeId, Long id);
}
