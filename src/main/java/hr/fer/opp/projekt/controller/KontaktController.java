package hr.fer.opp.projekt.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.Kontakt;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.service.KontaktService;
@Controller
@PreAuthorize("!hasAuthority('ADMIN')")
public class KontaktController {
	
	@Autowired
	KorisnikDao korisnikDao;
	
	@Autowired
	KontaktService kontaktService;
	
	@RequestMapping(value = "/kontakt", method = RequestMethod.GET)
	public ModelAndView kontaktGet() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/kontakt");
		modelAndView.addObject("kontakt", new Kontakt());
		modelAndView.addObject("classActiveKontakt","active");

		modelAndView.addObject("provjera", "0");
		return modelAndView;
	}
	
	@RequestMapping(value = "/kontakt", method = RequestMethod.POST)
	public ModelAndView kontaktPost(Kontakt kontakt, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		
		String email = kontakt.getEmail();
		String text = kontakt.getText();
		String ime = kontakt.getIme();
		String prezime = kontakt.getPrezime();
		if(email == null && ime == null && prezime == null) {
			Korisnik korisnik = korisnikDao.findByEmail(principal.getName());
			email = korisnik.getEmail();
			ime = korisnik.getIme();
			prezime = korisnik.getPrezime();
		}
		
		int br = kontaktService.sendEmail(email, text, ime, prezime);
		if(br == 1)
			modelAndView.addObject("provjera", "1");
		else
			modelAndView.addObject("provjera", "2");
		
		modelAndView.addObject("kontakt", new Kontakt());
		modelAndView.addObject("classActiveKontakt","active");
		modelAndView.setViewName("kontakt");
		
		
		
		return modelAndView;
	}
}
