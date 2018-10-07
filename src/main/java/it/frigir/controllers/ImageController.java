package it.frigir.controllers;

import it.frigir.commands.RecipeCommand;
import it.frigir.services.ImageService;
import it.frigir.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;

	@Value("classpath:static/images/default_image.jpg")
	private Resource default_imgage;

	public ImageController(RecipeService recipeService, ImageService imageService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}


	@GetMapping("recipe/{recipeId}/image")
	public String getImageForm(@PathVariable String recipeId, Model model) {
		log.debug("request for recipe image form" + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/image/form";
	}

	@PostMapping("recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		log.debug("request for uploading image");
		imageService.saveImageFile(Long.valueOf(recipeId), file);
		return "redirect:/recipe/" + recipeId + "/show";
	}

	@GetMapping("recipe/{recipeId}/recipeimage")
	public void getRecipeImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		response.setContentType("image/jpeg");
		InputStream is = null;
		if (recipeCommand.getImage() != null && recipeCommand.getImage().length > 0) {
			is = new ByteArrayInputStream(ArrayUtils.toPrimitive(recipeCommand.getImage()));

		} else {
			is = default_imgage.getInputStream();
		}
		IOUtils.copy(is, response.getOutputStream());

	}

}