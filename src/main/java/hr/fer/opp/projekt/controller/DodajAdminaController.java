package hr.fer.opp.projekt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.form.validation.RegistracijaFormValidator;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PromjeniAdmin;
import hr.fer.opp.projekt.service.AdminService;
import hr.fer.opp.projekt.service.KorisnikService;

@Controller
@PreAuthorize("hasAuthority('VLASNIK')")
public class DodajAdminaController {
	
	@Autowired
	AdminService adminService;
	@Autowired
	KorisnikDao korisnikDao;
	@Autowired
	KorisnikService korisnikService;
	
	@RequestMapping(value = "/dodaj/admin", method = RequestMethod.GET)
	public ModelAndView dodajAdminaGet(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("classActiveAdmin","active");
		model.addAttribute("classActiveVlasnikSucelje","active");
		model.addAttribute("classActiveAdminSucelje","active");
		model.addAttribute("adminList", adminService.getAdminList());
		model.addAttribute("noviAdmin", new RegistracijaForm());
		model.addAttribute("korisnik", new Korisnik());
		model.addAttribute("promjeniAdmin", new PromjeniAdmin());
		model.addAttribute("provjera", "0");
		model.addAttribute("registriran", "0");
		modelAndView.setViewName("dodajAdmina");
		return modelAndView;
	}
	
	@RequestMapping(value = "/dodaj/admin", method = RequestMethod.POST)
	public ModelAndView dodajAdminaPost(@ModelAttribute("noviAdmin")RegistracijaForm noviAdmin, BindingResult result, WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Korisnik admin = new Korisnik();
		admin = new RegistracijaFormValidator().validate(noviAdmin);
		if(admin != null) {
			admin = createUserAccount(noviAdmin, result);
		}else {
			model.addAttribute("classActiveAdmin","active");
			model.addAttribute("classActiveVlasnikSucelje","active");
			model.addAttribute("classActiveAdminSucelje","active");
			model.addAttribute("adminList", adminService.getAdminList());
			model.addAttribute("noviAdmin", new RegistracijaForm());
			model.addAttribute("korisnik", new Korisnik());
			model.addAttribute("promjeniAdmin", new PromjeniAdmin());
			model.addAttribute("provjera", "1");
			model.addAttribute("registriran", "0");
			modelAndView.setViewName("dodajAdmina");
			return modelAndView;
		}
		
		if(admin == null) {
			result.rejectValue("email", "message.regError");

			model.addAttribute("classActiveAdmin","active");
			model.addAttribute("classActiveVlasnikSucelje","active");
			model.addAttribute("classActiveAdminSucelje","active");
			model.addAttribute("adminList", adminService.getAdminList());
			model.addAttribute("noviAdmin", new RegistracijaForm());
			model.addAttribute("korisnik", new Korisnik());
			model.addAttribute("promjeniAdmin", new PromjeniAdmin());
			model.addAttribute("provjera", "1");
			model.addAttribute("registriran", "0");
			modelAndView.setViewName("dodajAdmina");
			return modelAndView;
		}
		if(result.hasErrors()) {	

			model.addAttribute("classActiveAdmin","active");
			model.addAttribute("classActiveVlasnikSucelje","active");
			model.addAttribute("classActiveAdminSucelje","active");
			model.addAttribute("adminList", adminService.getAdminList());
			model.addAttribute("noviAdmin", new RegistracijaForm());
			model.addAttribute("korisnik", new Korisnik());
			model.addAttribute("promjeniAdmin", new PromjeniAdmin());
			model.addAttribute("provjera", "1");
			model.addAttribute("registriran", "0");
			modelAndView.setViewName("dodajAdmina");
			return modelAndView;
		}

		model.addAttribute("classActiveAdmin","active");
		model.addAttribute("classActiveVlasnikSucelje","active");
		model.addAttribute("classActiveAdminSucelje","active");
		model.addAttribute("adminList", adminService.getAdminList());
		model.addAttribute("noviAdmin", new RegistracijaForm());
		model.addAttribute("korisnik", new Korisnik());
		model.addAttribute("promjeniAdmin", new PromjeniAdmin());
		model.addAttribute("provjera", "0");
		model.addAttribute("registriran", "1");
		modelAndView.setViewName("dodajAdmina");
		return modelAndView;
	}
	
	@RequestMapping(value = "/ukloni/admin/{idKorisnika}", method = RequestMethod.POST)
	public ModelAndView ukloniAdmina(@PathVariable("idKorisnika")Integer idAdmin) {
		ModelAndView modelAndView = new ModelAndView();
		korisnikDao.updateKorisnikRola(idAdmin, 3);
		modelAndView.setViewName("redirect:/dodaj/admin");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uredi/admin", method = RequestMethod.POST)
	public ModelAndView urediAdmina(PromjeniAdmin promjeniAdmin, Model model) {
		
		Korisnik korisnik = new Korisnik();
		korisnik.setIdKorisnika(promjeniAdmin.getIdKorisnikaAdmin());
		korisnik.setIme(promjeniAdmin.getImeAdmin());
		korisnik.setPrezime(promjeniAdmin.getPrezimeAdmin());
		korisnik.setEmail(promjeniAdmin.getEmailAdmin());
		korisnik.setDrzava(promjeniAdmin.getDrzavaAdmin());
		korisnik.setGrad(promjeniAdmin.getGradAdmin());
		korisnik.setUlicaKbr(promjeniAdmin.getUlicaKbrAdmin());
		korisnik.setBrTelefona(promjeniAdmin.getBrTelefonaAdmin());
		
		if(korisnik.getBrTelefona().trim().equals("")) {
			korisnik.setBrTelefona("Broj telefona nepoznat");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		if(!korisnikService.validateUser(korisnik, promjeniAdmin)) {
			model.addAttribute("classActiveAdmin","active");
			model.addAttribute("classActiveVlasnikSucelje","active");
			model.addAttribute("classActiveAdminSucelje","active");
			model.addAttribute("adminList", adminService.getAdminList());
			model.addAttribute("noviAdmin", new RegistracijaForm());
			model.addAttribute("korisnik", new Korisnik());
			model.addAttribute("promjeniAdmin", new PromjeniAdmin());
			model.addAttribute("provjera", "1");
			model.addAttribute("registriran", "0");
			modelAndView.setViewName("dodajAdmina");
			return modelAndView;
		}
		korisnikDao.azurirajKorisnika(korisnik);
		modelAndView.setViewName("redirect:/dodaj/admin");
		return modelAndView;
	}
	
	private Korisnik createUserAccount(RegistracijaForm noviAdmin, BindingResult result) {
		Korisnik registered = null;
		try {
			registered = adminService.registerNewAdmin(noviAdmin);
		} catch(EmailExistsException e){
			return null;
		}
		return registered;
	}
	
	

}
