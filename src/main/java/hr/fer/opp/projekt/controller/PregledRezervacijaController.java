package hr.fer.opp.projekt.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.DodatnaUslugaDao;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.dao.PromjenaRezervacijeDao;
import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.PromjenaRezervacijeService;
import hr.fer.opp.projekt.service.RezervacijaService;
import hr.fer.opp.projekt.service.TipSmjestJedService;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN, VLASNIK')")
public class PregledRezervacijaController {

	private String currentStatus = "-";
	private String currentStatusSuccess = "+";

	@Autowired
	RezervacijaDao rezervacijaDao;
	@Autowired
	RezervacijaService rezervacijaService;
	@Autowired
	DodatnaUslugaDao dodatnaUslugaDao;
	@Autowired
	KorisnikDao korisnikDao;
	@Autowired
	TipSmjestJedService tipSmjestJedService;
	@Autowired
	PromjenaRezervacijeService promjenaRezervacijeService;
	
	private int trenActive = 0;

	@RequestMapping(value = "/pregledRezervacija", method = RequestMethod.GET)
	public ModelAndView pregledRezervacija(Model model, Principal principal) throws ParseException {

		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("rezervacija", new Rezervacija());
		
		List<Rezervacija> listRez = rezervacijaService.getRezervacijeByStatus(0);
		List<Rezervacija> listNepotvrdenihRez = rezervacijaService.provjeriRok(listRez);

		model.addAttribute("rezervacijeFalseList", listNepotvrdenihRez);
		model.addAttribute("rezervacijeTrueList", rezervacijaService.getRezervacijeByStatus(1));
		model.addAttribute("rezervacijeOdbijeneList", rezervacijaService.getRezervacijeByStatus(2));

		List<TipSmjestJed> tipSmjestJedList = tipSmjestJedService.getTipSmjestJedList();
		model.addAttribute("tipSmjestJedList", tipSmjestJedList);
		model.addAttribute("promjenaRezervacije", new PromjenaRezervacije());
		modelAndView.addObject("dodatneUslugeList", dodatnaUslugaDao.getUslugeList());

		model.addAttribute("classActiveRezervacije", "active");
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		model.addAttribute("rezPromjene", currentStatus);
		model.addAttribute("rezPromjeneUspjeh", currentStatusSuccess);
		modelAndView.setViewName("pregledRezervacija");
		currentStatus = "-";
		currentStatusSuccess = "+";

		switch (trenActive) {
		case 0: 
			model.addAttribute("nonActiveClass", "active");
			model.addAttribute("nonShowClass", "show active");
			break;
		case 1:
			model.addAttribute("potActiveClass", "active");
			model.addAttribute("potShowClass", "show active");
			break;
		case 2:
			model.addAttribute("odbActiveClass", "active");
			model.addAttribute("odbShowClass", "show active");
			break;
		default: 
			model.addAttribute("nonActiveClass", "active");
			model.addAttribute("nonShowClass", "show active");
			break;
		}
		trenActive=0;
		
		return modelAndView;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/pregledRezervacija/uredi", method = RequestMethod.POST)
	public ModelAndView urediRez(Model model, PromjenaRezervacije promjenaRezervacije, @PathVariable("trenActive") Integer trenActive) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		this.trenActive = trenActive;
		String b = promjenaRezervacijeService.napraviPromjenu(promjenaRezervacije);
		if (b.equals("Uspješna promjena parametara rezervacije!")) {
			currentStatusSuccess = b;
			rezervacijaService.uredenaRezervacija(promjenaRezervacije.getKorisnikID());
			
		} else {
			currentStatus = b;
		}
		
		modelAndView.setViewName("redirect:/pregledRezervacija");
		return modelAndView;
	}
	
	@RequestMapping(value = "/pregledRezervacija/potvrdi/{rezervacijaID}/{korisnikID}/{trenActive}", method = RequestMethod.POST)
	public ModelAndView potvrdiRez(@PathVariable("rezervacijaID") Integer rezervacijaID,
			@PathVariable("korisnikID") Integer korisnikID, @PathVariable("trenActive") Integer trenActive) {
		ModelAndView modelAndView = new ModelAndView();
		this.trenActive = trenActive;
		rezervacijaDao.potvrdiOdbijRezervaciju(rezervacijaID, 1);
		rezervacijaService.potvrdenaRezervacijaMail(korisnikID);
		modelAndView.setViewName("redirect:/pregledRezervacija");
		return modelAndView;
	}
	
	@RequestMapping(value = "/pregledRezervacija/ponisti/{rezervacijaID}/{korisnikID}/{trenActive}", method = RequestMethod.POST)
	public ModelAndView ponistiRez(@PathVariable("rezervacijaID") Integer rezervacijaID,
			@PathVariable("korisnikID") Integer korisnikID, @PathVariable("trenActive") Integer trenActive) {
		ModelAndView modelAndView = new ModelAndView();
		this.trenActive = trenActive;
		rezervacijaDao.potvrdiOdbijRezervaciju(rezervacijaID, 2);
		rezervacijaService.ponistenaRezervacijaMail(korisnikID);

		modelAndView.setViewName("redirect:/pregledRezervacija");
		return modelAndView;
	}
}
