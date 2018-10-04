package fritz.test.recepie.controllers;

import fritz.test.recepie.commands.RecipeCommand;
import fritz.test.recepie.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

	final private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping({"/recipe/{id}/show"})
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		return "recipe/show";
	}

	@GetMapping
	@RequestMapping({"recipe/new"})
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}

	@GetMapping
	@RequestMapping({"recipe/{id}/update"})
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
		return "recipe/recipeform";
	}

	@PostMapping
	@RequestMapping("recipe")
	//@RequestMapping(name = "recipe", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + +savedRecipeCommand.getId() + "/show";
	}

	@GetMapping
	@RequestMapping("recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) {
		recipeService.deleteById(new Long(id));
		return "redirect:/";
	}

}
