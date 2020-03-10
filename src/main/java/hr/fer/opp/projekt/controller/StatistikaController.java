package hr.fer.opp.projekt.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.StatistikaDao;
import hr.fer.opp.projekt.model.Rang;
import hr.fer.opp.projekt.service.StatistikaService;

@Controller
@PreAuthorize("hasAuthority('VLASNIK')")
public class StatistikaController {
	
	@Autowired
	StatistikaDao statistikaDao;
	@Autowired
	StatistikaService statistikaService;
	
	@RequestMapping(value = "/statistika", method = RequestMethod.GET)
	public ModelAndView statistikaGet(Model model) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		
		List<Rang> smjestJed = statistikaService.getRangSmjestJed();
		List<Rang> drzava = statistikaDao.getRangDrzava();
		List<Rang> grad = statistikaDao.getRangGrad();
		List<Rang> dodUsl = statistikaDao.getRangUsluge();
		List<Rang> uslDrz = statistikaDao.getRangUslugeDrzava();
		List<Rang> uslGst = statistikaDao.getRangUslugeBrGostiju();
		
		model.addAttribute("rangSmjestJed", smjestJed);
		model.addAttribute("rangDrzava", drzava);
		model.addAttribute("rangGrad", grad);
		model.addAttribute("rangUsluge", dodUsl);
		model.addAttribute("rangUslugeDrzava", uslDrz);
		model.addAttribute("rangUslugeBrGostiju", uslGst);
		
		model.addAttribute("classActiveStatistika", "active");
		model.addAttribute("classActiveVlasnikSucelje", "active");
		modelAndView.setViewName("statistika");
		return modelAndView;
	}
}
