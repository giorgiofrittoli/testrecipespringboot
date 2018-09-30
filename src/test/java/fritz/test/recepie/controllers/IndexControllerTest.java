package fritz.test.recepie.controllers;

import fritz.test.recepie.Model.Recipe;
import fritz.test.recepie.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {


	IndexController indexController;

	@Mock
	RecipeServiceImpl recipeService;
	@Mock
	Model model;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		indexController = new IndexController(recipeService);

	}

	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		//eseguo chiamata get all'indirizzo chiamato e controllo la chiamata sia ok, controllo che la chiamata restituisca la view index
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void getIndexPage() {

		//creo nuovo set

		//given
		Set<Recipe> recipes = new HashSet<>();

		Recipe rec1 = new Recipe();
		rec1.setId(1L);

		Recipe rec2 = new Recipe();
		rec1.setId(2L);


		recipes.add(rec1);
		recipes.add(rec2);


		when(recipeService.getRecipes()).thenReturn(recipes);


		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		//when
		String viewName = indexController.getIndexPage(model);

		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}
}