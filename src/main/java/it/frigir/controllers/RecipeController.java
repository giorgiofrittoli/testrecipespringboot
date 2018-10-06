package it.frigir.controllers;

import it.frigir.commands.RecipeCommand;
import it.frigir.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RecipeController {

	final private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({"/recipe/{id}/show"})
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		return "recipe/show";
	}

	@GetMapping({"recipe/new"})
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/form";
	}

	@GetMapping({"recipe/{id}/update"})
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
		return "recipe/form";
	}

	@PostMapping("recipe")
	//@RequestMapping(name = "recipe", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + +savedRecipeCommand.getId() + "/show";
	}

	@GetMapping("recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		recipeService.deleteById(new Long(id));
		return "redirect:/";
	}

}
