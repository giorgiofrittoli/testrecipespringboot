package it.frigir.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ControllerExceptionHandler {

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
