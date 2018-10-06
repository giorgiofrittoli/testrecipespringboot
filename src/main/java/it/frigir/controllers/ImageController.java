package it.frigir.controllers;

import it.frigir.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ImageController {

	private final RecipeService recipeService;

	public ImageController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@GetMapping("/recipe/{recipeId}/image")
	public String getImageForm(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/image/form";
	}



}