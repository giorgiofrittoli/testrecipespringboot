package it.frigir.services;

import it.frigir.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	void deleteIngredient(Long recipeId, Long id);
}
