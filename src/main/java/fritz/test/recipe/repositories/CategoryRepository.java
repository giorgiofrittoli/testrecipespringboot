package fritz.test.recipe.repositories;

import fritz.test.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

	Optional<Category> findByDescription(String description);
}
