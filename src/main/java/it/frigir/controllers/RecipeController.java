package it.frigir.controllers;

import it.frigir.commands.RecipeCommand;
import it.frigir.exceptions.NotFoundException;
import it.frigir.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFoundEx(Exception e) {
		log.debug("404 exception thrown " + e.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404Error");
		modelAndView.addObject("exception", e);
		return modelAndView;

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormatException(Exception e) {
		log.debug("400 exception thrown " + e.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400Error");
		modelAndView.addObject("exception", e);
		return modelAndView;

	}

}
