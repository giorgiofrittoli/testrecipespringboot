package it.frigir.services;

import it.frigir.commands.UnitOfMeasureCommand;
import it.frigir.converters.UnitOfMeasureToUnitOfMeasureCommand;
import it.frigir.model.UnitOfMeasure;
import it.frigir.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

	UnitOfMeasureServiceImpl unitOfMeasureService;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void getAllUnitOfMeasure() {

		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

		//given
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(1L);
		UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
		unitOfMeasure.setId(2L);

		unitOfMeasures.add(unitOfMeasure);
		unitOfMeasures.add(unitOfMeasure2);

		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

		//when
		Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.getAllUnitOfMeasure();

		//then
		assertEquals(2, unitOfMeasureCommands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();

	}


}