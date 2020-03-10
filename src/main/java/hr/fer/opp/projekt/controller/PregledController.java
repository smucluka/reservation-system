package hr.fer.opp.projekt.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.DodatnaUslugaDao;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.model.Pretraga;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.PretragaService;


@Controller
public class PregledController {
	
	@Autowired
	PretragaService pretragaService;
	@Autowired
	DodatnaUslugaDao dodatnaUslugaDao;
	
	
	@RequestMapping(value = "/pregled", method = RequestMethod.GET)
	public ModelAndView pregledGet() {
		ModelAndView modelAndView = new ModelAndView();
		
		Pretraga pretraga = new Pretraga();
		pretraga.setDatumOd("01.05.2018.");
		pretraga.setDatumDo("02.05.2018.");
		modelAndView.addObject("pretraga", pretraga);
		modelAndView.addObject("pregledRezervacija", new PregledRezervacija());
		modelAndView.addObject("dodatneUslugeList", dodatnaUslugaDao.getUslugeList());
		modelAndView.addObject("provjera", "1");
		
		modelAndView.addObject("classActivePregled","active");
		
		modelAndView.setViewName("pregled");
		return modelAndView;
	}
	
	@RequestMapping(value = "/pregled", method = RequestMethod.POST)
	public ModelAndView pregledPost(Pretraga pretraga) throws ParseException {
		
		ModelAndView modelAndView = new ModelAndView();
		List<TipSmjestJed> t = pretragaService.dohvatiPregledTipove(pretraga);
		modelAndView.addObject("pregledTipList", t);
		modelAndView.addObject("pregledRezervacija", new PregledRezervacija());
		modelAndView.addObject("dodatneUslugeList", dodatnaUslugaDao.getUslugeList());
		modelAndView.addObject("provjera", "0");
		
		modelAndView.addObject("classActivePregled","active");
		modelAndView.setViewName("pregled");
		return modelAndView;
	}
	
}
