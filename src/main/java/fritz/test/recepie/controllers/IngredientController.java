package fritz.test.recepie.controllers;

import fritz.test.recepie.services.IngredientService;
import fritz.test.recepie.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/list")
	public String getIngredientList(@PathVariable String recipeId, Model model) {
		log.debug("request ingredient list for recipe " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(new Long(recipeId)));
		return "recipe/ingredient/list";
	}


	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String getIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Request for ingredient " + id);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(new Long(recipeId), new Long(id)));
		return "/recipe/ingredient/show";
	}


}
