package hr.fer.opp.projekt.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.service.RezervacijaService;

@Controller
@PreAuthorize("hasAnyAuthority('KORISNIK')")
public class RezervacijaController {

	@Autowired
	KorisnikDao korisnikDao;
	@Autowired
	RezervacijaService rezervacijaService;

	@RequestMapping(value = "/rezervacija", method = RequestMethod.POST)
	public ModelAndView rezervirajPost(Model model, PregledRezervacija pregledRezervacija, Principal principal) throws ParseException {
		
		Korisnik korisnik = korisnikDao.findByEmail(principal.getName());
		pregledRezervacija.setKorisnikID(korisnik.getIdKorisnika());
		rezervacijaService.obaviRezervaciju(pregledRezervacija);
		rezervacijaService.zaprimljenaRezervacija(principal.getName());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/korisnik/rezervacije");
		return modelAndView;
	}
	
	@RequestMapping(value = "/rezervacija", method = RequestMethod.GET)
	public ModelAndView rezervirajGet() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/pocetna");
		return modelAndView;
	}

}
