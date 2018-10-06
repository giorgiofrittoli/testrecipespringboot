package it.frigir.controllers;

import it.frigir.commands.IngredientCommand;
import it.frigir.commands.RecipeCommand;
import it.frigir.commands.UnitOfMeasureCommand;
import it.frigir.services.IngredientService;
import it.frigir.services.RecipeService;
import it.frigir.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	@GetMapping({"recipe/{recipeId}/ingredient/list", "recipe/{recipeId}/ingredients"})
	public String getIngredientList(@PathVariable String recipeId, Model model) {
		log.debug("request ingredient list for recipe " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(new Long(recipeId)));
		return "/recipe/ingredient/list";
	}


	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String getIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Request for ingredient " + id);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(new Long(recipeId), new Long(id)));
		return "/recipe/ingredient/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("request for ingredient " + id + " form ");
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.getAllUnitOfMeasure());
		return "/recipe/ingredient/form";
	}

	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String insertIngredient(@PathVariable String recipeId, Model model) {
		log.debug("request for new ingredient ingredient form");

		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

		if (recipeCommand == null)
			throw new RuntimeException("Recipe not found");

		IngredientCommand ingredientCommand = IngredientCommand.builder().recipeId(Long.valueOf(recipeId)).unitOfMeasure(new UnitOfMeasureCommand()).build();
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.getAllUnitOfMeasure());
		return "/recipe/ingredient/form";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.debug("request delete for recipe " + recipeId + " ingredient" + id);
		ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(id));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
		log.debug("request for update/save ingredient " + ingredientCommand.getId());
		IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		return "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredient/" + saveIngredientCommand.getId() + "/show";
	}


}
