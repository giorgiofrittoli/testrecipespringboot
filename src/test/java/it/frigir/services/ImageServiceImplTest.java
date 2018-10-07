package it.frigir.services;

import it.frigir.model.Recipe;
import it.frigir.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;

	ImageService imageService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}


	@Test
	public void saveImgTest() throws Exception {

		//given
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "aaa", "text/plain"
				, "aaaa".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Optional<Recipe> optionalRecipe = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

		ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		//when
		imageService.saveImageFile(Long.valueOf(1L), multipartFile);

		//then
		verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());
		Recipe savedRecipe = recipeArgumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

	}
}