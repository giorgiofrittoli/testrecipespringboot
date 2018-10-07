package it.frigir.services;

import it.frigir.model.Recipe;
import it.frigir.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

		if (!optionalRecipe.isPresent())
			throw new RuntimeException("Recipe not found");

		Recipe foundRecipe = optionalRecipe.get();

		try {
			foundRecipe.setImage(ArrayUtils.toObject(file.getBytes()));
		} catch (IOException e) {
			throw new RuntimeException("Save img error");
		}
		recipeRepository.save(foundRecipe);
	}
}
