package hr.fer.opp.projekt.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.DodatnaUslugaDao;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SecurePassword;
import hr.fer.opp.projekt.service.RezervacijaService;

@Controller
public class KorisnikController {
	
	@Autowired
	KorisnikDao korisnikDao;
	@Autowired
	RezervacijaDao rezervacijaDao;
	@Autowired
	DodatnaUslugaDao dodatnaUslugaDao;
	@Autowired
	RezervacijaService rezervacijaService;
	
	
	@RequestMapping(value = "/korisnik/profil", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('KORISNIK, ADMIN, VLASNIK')")
	public ModelAndView korisnikProfilGet(Model model, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		Korisnik korisnik = korisnikDao.findByEmail(principal.getName());
		model.addAttribute("korisnik", korisnik);
		model.addAttribute("securePassword", new SecurePassword());
		model.addAttribute("classActiveMojProfil","active");
		modelAndView.setViewName("korisnikProfil");
		return modelAndView;
	}
	
	@RequestMapping(value = "/korisnik/profil", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('KORISNIK, ADMIN, VLASNIK')")
	public ModelAndView azurirajPodatke(Model model, Korisnik korisnik, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		
		Korisnik korTemp = korisnikDao.findByEmail(principal.getName());
		korisnik.setIdKorisnika(korTemp.getIdKorisnika());
		
		korisnikDao.azurirajKorisnika(korisnik);
		model.addAttribute("classActiveMojProfil","active");
		modelAndView.setViewName("korisnikProfil");
		return modelAndView;
	}

	
	@RequestMapping(value = "/secure", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('KORISNIK, ADMIN, VLASNIK')")
	public ModelAndView promijeniLozinku(SecurePassword securePassword, Model model, Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView();
		Korisnik korTemp = korisnikDao.findByEmail(principal.getName());
		
		Korisnik korisnik = new Korisnik();
		korisnik.setPassword(securePassword.getLozinka());
		korisnik.setIdKorisnika(korTemp.getIdKorisnika());
		korisnikDao.azurirajLozinku(korisnik);

		modelAndView.setViewName("redirect:/korisnik/profil");
		return modelAndView;
	}
	
	@RequestMapping(value = "/korisnik/rezervacije", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('KORISNIK')")
	public ModelAndView korisnikMojeRezervacijeGet(Model model, Principal principal) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		Korisnik korisnik = korisnikDao.findByEmail(principal.getName());
		
		List<Rezervacija> rezervacijaList = rezervacijaService.getRezervacije(korisnik.getIdKorisnika());
		model.addAttribute("mojeRezervacijeList", rezervacijaList);
		model.addAttribute("classActiveMojeRezervacije","active");
		modelAndView.setViewName("mojeRezervacije");
		return modelAndView;
	}
}
