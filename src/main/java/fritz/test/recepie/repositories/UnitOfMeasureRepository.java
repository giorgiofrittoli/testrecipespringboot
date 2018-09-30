package fritz.test.recepie.repositories;

import fritz.test.recepie.Model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
