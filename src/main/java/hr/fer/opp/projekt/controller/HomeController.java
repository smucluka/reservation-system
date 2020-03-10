package hr.fer.opp.projekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"/pocetna", "/[]", "/@%7B/pocetna%7D"}, method = RequestMethod.GET)
	public ModelAndView homeGet() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public ModelAndView pocetnaGet(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("classActivePocetna","active");
		modelAndView.setViewName("home");
		return modelAndView;
	}

	
	
}
