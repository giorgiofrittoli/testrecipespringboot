package it.frigir.controllers;

import it.frigir.commands.RecipeCommand;
import it.frigir.services.ImageService;
import it.frigir.services.RecipeService;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	MockMvc mockMvc;

	ImageController imageController;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		imageController = new ImageController(recipeService, imageService);

		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	@Test
	public void getImageForm() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(1L)).thenReturn(recipeCommand);

		//when
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));

		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void handleImagePostTest() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "test", "text/plain", "aaaaa".getBytes());
		mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));

		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}


	@Test
	public void renderImage() throws  Exception{

		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		String s = "dsasdasda";

		recipeCommand.setImage(ArrayUtils.toObject(s.getBytes()));

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();


		assertEquals(s.getBytes().length,response.getContentAsByteArray().length);



	}
}