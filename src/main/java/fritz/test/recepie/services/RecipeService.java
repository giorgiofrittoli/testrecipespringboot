package fritz.test.recepie.services;


import fritz.test.recepie.Model.Recipe;

public interface RecipeService {

	Iterable<Recipe> getRecipes();

	Recipe findById(Long l);

}
