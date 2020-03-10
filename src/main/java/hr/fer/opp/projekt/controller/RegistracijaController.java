package hr.fer.opp.projekt.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.form.validation.RegistracijaFormValidator;
import hr.fer.opp.projekt.model.AktivacijskiToken;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.service.AktivacijaService;
import hr.fer.opp.projekt.service.KorisnikService;

@Controller
public class RegistracijaController {

	@Autowired
	KorisnikService korisnikService;

	@Autowired
	AktivacijaService aktivacijaService;

	@Autowired
	JavaMailSender mail;
	
	
	@RequestMapping(value = "/registracija", method = RequestMethod.GET)
	public ModelAndView registracijaGet(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("registracijaForm", new RegistracijaForm());
		model.addAttribute("provjera", "0");
		model.addAttribute("provjera2", "0");
		modelAndView.setViewName("registracija");
		return modelAndView;
	}

	@RequestMapping(value = "/registracija", method = RequestMethod.POST)
	public ModelAndView registrirajKorisnikaPost(@ModelAttribute("korisnik") RegistracijaForm registracijaForm, BindingResult result, WebRequest request, Model model) {
		Korisnik registriran = new Korisnik();

		if(registracijaForm.getBrTelefona() == null || registracijaForm.getBrTelefona().trim().equals("")) {
			registracijaForm.setBrTelefona("Broj telefona nepoznat");
		}
		
		registriran = new RegistracijaFormValidator().validate(registracijaForm);
		if (registriran != null) {
			registriran = createUserAccount(registracijaForm, result);
		}else {
			model.addAttribute("registracijaForm", registracijaForm);
			model.addAttribute("provjera","0");
			model.addAttribute("provjera2","1");
			return new ModelAndView();
		}
		
		if (registriran == null) {
			result.rejectValue("email", "message.regError");
			model.addAttribute("registracijaForm", registracijaForm);
			model.addAttribute("provjera","1");
			model.addAttribute("provjera2","0");
			return new ModelAndView();
		} else {
			String email = registracijaForm.getEmail();
			korisnikService.sendActivationEmail(request.getContextPath(), email,
					aktivacijaService.generirajToken(email));

			if (result.hasErrors()) {
				return new ModelAndView();
			}else {
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.addObject("provjera", "0");
				modelAndView.setViewName("login");
				modelAndView.addObject("aktiviraj", "1");
				return modelAndView;
			}
		}
	}

	private Korisnik createUserAccount(RegistracijaForm registracijaForm, BindingResult result) {
		Korisnik registered = null;
		try {
			registered = korisnikService.registerNewUserAccount(registracijaForm);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

	@RequestMapping(value = "/potvrdaRegistracije", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		AktivacijskiToken aktivacijskiToken = aktivacijaService.getAktivacijskiToken(token);

		// TODO
		if (aktivacijskiToken == null) {
			return "redirect:/badUser";
		}

		if (new Timestamp(System.currentTimeMillis()).after(aktivacijskiToken.getTstampIsteka())) {
			// isteklo vrijeme za aktivaciju
			return "redirect:/badUser";
		}

		korisnikService.activateUser(aktivacijskiToken.getIdKorisnika());
		return "redirect:/login";
	}

}
