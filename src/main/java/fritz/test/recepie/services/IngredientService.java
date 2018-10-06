package fritz.test.recepie.services;

import fritz.test.recepie.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

}
