package hr.fer.opp.projekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	private String temp = "0";
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("aktiviraj", "0");
		modelAndView.addObject("provjera", temp);
		modelAndView.setViewName("login");
		temp="0";
		return modelAndView;
	}
	
	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public ModelAndView loginError() {
		ModelAndView modelAndView = new ModelAndView();
		temp="1";
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}
}
