package hr.fer.opp.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.fer.opp.projekt.form.ResetLozinkeForm;
import hr.fer.opp.projekt.form.validation.ResetLozinkeFormValidator;
import hr.fer.opp.projekt.service.ResetLozinkeService;

@Controller
public class ResetLozinkeController {
	
	@Autowired
	ResetLozinkeService resetLozinkeService;
	
	@Autowired
	ResetLozinkeFormValidator validator;
	
	@RequestMapping(value = "/lozinka/reset", method = RequestMethod.GET)
	public ModelAndView resetGet(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("resetLozinkeForm", new ResetLozinkeForm());
		modelAndView.setViewName("resetLozinke");
		return modelAndView;
	}
	
	@RequestMapping(value = "/lozinka/reset", method = RequestMethod.POST)
	public ModelAndView resetPost(@ModelAttribute("resetLozinkeForm") ResetLozinkeForm resetLozinkeForm, BindingResult result, WebRequest request, Model model, RedirectAttributes ra) {
		ModelAndView modelAndView = new ModelAndView();
		validator.validate(resetLozinkeForm, result);
		if(!result.hasErrors()) {
			resetLozinkeService.resetirajLozinku(resetLozinkeForm.getEmail());
			ra.addFlashAttribute("message", "Lozinka je uspješno resetirana. Nova lozinka vam je poslana na mail.");
			modelAndView.setViewName("redirect:/login");
			return modelAndView;
		}
		modelAndView.setViewName("resetLozinke");
		return modelAndView;
	}

}
