package fritz.test.recipe.repositories;

import fritz.test.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

	public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
