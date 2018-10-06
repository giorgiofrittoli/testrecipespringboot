package fritz.test.recipe.controllers;

import fritz.test.recipe.commands.IngredientCommand;
import fritz.test.recipe.services.IngredientService;
import fritz.test.recipe.services.RecipeService;
import fritz.test.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping
	@RequestMapping({"recipe/{recipeId}/ingredient/list", "recipe/{recipeId}/ingredients"})
	public String getIngredientList(@PathVariable String recipeId, Model model) {
		log.debug("request ingredient list for recipe " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(new Long(recipeId)));
		return "/recipe/ingredient/list";
	}


	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String getIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Request for ingredient " + id);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(new Long(recipeId), new Long(id)));
		return "/recipe/ingredient/show";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("request for ingredient " + id + " form ");
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.getAllUnitOfMeasure());
		return "/recipe/ingredient/form";
	}

	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
		log.debug("request for update/save ingredient " + ingredientCommand.getId());
		IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		return "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredient/" + saveIngredientCommand.getId() + "/show";
	}


}
