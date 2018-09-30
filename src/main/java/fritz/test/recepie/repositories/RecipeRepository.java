package fritz.test.recepie.repositories;

import fritz.test.recepie.Model.Recipe;
import org.springframework.data.repository.CrudRepository;

	public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
