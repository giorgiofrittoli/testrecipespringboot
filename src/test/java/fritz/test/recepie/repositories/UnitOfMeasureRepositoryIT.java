package fritz.test.recepie.repositories;

import fritz.test.recepie.Model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	//@DirtiesContext
	public void findByDescriptionTeaspoon() {
		Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

		assertEquals("Teaspoon", optionalUnitOfMeasure.get().getDescription());
	}

	@Test
	public void findByDescriptionCup() {
		Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");

		assertEquals("Cup", optionalUnitOfMeasure.get().getDescription());
	}
}