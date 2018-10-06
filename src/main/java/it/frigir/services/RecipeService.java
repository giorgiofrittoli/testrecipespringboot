package it.frigir.services;

import it.frigir.commands.RecipeCommand;
import it.frigir.model.Recipe;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long l);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(Long aLong);


	void deleteById(Long aLong);
}
