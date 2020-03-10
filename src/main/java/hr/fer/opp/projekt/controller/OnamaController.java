package hr.fer.opp.projekt.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.OnamaService;

@Controller
public class OnamaController {
	
	@Autowired
	OnamaService onamaService;
	
	@RequestMapping(value = "/onama", method = RequestMethod.GET)
	public ModelAndView onamaGet(Model model) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("onama");
		modelAndView.addObject("classActiveOnama","active");
		
		List<TipSmjestJed> tipSmjestJedList = onamaService.dohvatiTipove();
		model.addAttribute("tipSmjestJedList", tipSmjestJedList);

		modelAndView.addObject("provjera", "0");
		
		return modelAndView;
	}
}
