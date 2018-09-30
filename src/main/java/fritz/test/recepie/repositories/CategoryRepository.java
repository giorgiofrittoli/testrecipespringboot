package fritz.test.recepie.repositories;

import fritz.test.recepie.Model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

	Optional<Category> findByDescription(String description);
}
