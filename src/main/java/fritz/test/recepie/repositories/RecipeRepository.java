package fritz.test.recepie.repositories;

import fritz.test.recepie.model.Recipe;
import org.springframework.data.repository.CrudRepository;

	public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
