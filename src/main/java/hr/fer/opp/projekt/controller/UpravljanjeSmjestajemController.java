package hr.fer.opp.projekt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.form.SmjestJedForm;
import hr.fer.opp.projekt.model.DodatnaUsluga;
import hr.fer.opp.projekt.model.Objekt;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.DodatnaUslugaService;
import hr.fer.opp.projekt.service.ObjektService;
import hr.fer.opp.projekt.service.SmjestJedService;
import hr.fer.opp.projekt.service.TipSmjestJedService;

@Controller
@PreAuthorize("hasAuthority('VLASNIK')")
public class UpravljanjeSmjestajemController {

	@Autowired
	ObjektService objektService;

	@Autowired
	DodatnaUslugaService dodatnaUslugaService;

	@Autowired
	TipSmjestJedService tipSmjestJedService;

	@Autowired
	SmjestJedService smjestJedService;

	private String dodaj = "0";
	private Integer trenActive = 0;

	@RequestMapping(value = "/smjestaj", method = RequestMethod.GET)
	public ModelAndView smjestajneJedinicePregledGet(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("classActiveSmjest", "active");

		List<Objekt> objektList = objektService.getObjektList();
		model.addAttribute("objektList", objektList);
		model.addAttribute("noviObjekt", new Objekt());

		model.addAttribute("uslugeList", dodatnaUslugaService.getUslugaList());
		model.addAttribute("novaUsluga", new DodatnaUsluga());

		List<TipSmjestJed> tipSmjestJedList = tipSmjestJedService.getTipSmjestJedList();
		model.addAttribute("tipSmjestJedList", tipSmjestJedList);
		model.addAttribute("noviTip", new TipSmjestJed());

		model.addAttribute("smjestJedList", smjestJedService.getSmjestJedList());
		if (!objektList.isEmpty() && !tipSmjestJedList.isEmpty()) {
			model.addAttribute("novaSmjestJed", new SmjestJedForm());
		}

		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		model.addAttribute("dodaj", dodaj);
		modelAndView.setViewName("upravljanjeSmjestajem");

		switch (trenActive) {
		case 0: 
			model.addAttribute("objektiActiveClass", "active");
			model.addAttribute("objektiShowClass", "show active");
			break;
		case 1:
			model.addAttribute("dodUslActiveClass", "active");
			model.addAttribute("dodUslShowClass", "show active");
			break;
		case 2:
			model.addAttribute("tipoviActiveClass", "active");
			model.addAttribute("tipoviShowClass", "show active");
			break;		
		case 3:
			model.addAttribute("smjJedActiveClass", "active");
			model.addAttribute("smjJedShowClass", "show active");
			break;
		default: 
			model.addAttribute("objektiActiveClass", "active");
			model.addAttribute("objektiShowClass", "show active");
			break;
		}
		trenActive=0;

		dodaj = "0";
		return modelAndView;
	}

	@RequestMapping(value = "/dodaj/objekt", method = RequestMethod.POST)
	public ModelAndView dodajObjektPost(@ModelAttribute("noviObjekt") Objekt objekt, BindingResult result,
			WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		objektService.dodajNoviObjekt(objekt);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=0;
		return modelAndView;
	}

	@RequestMapping(value = "/ukloni/objekt/{idObjekta}", method = RequestMethod.POST)
	public ModelAndView ukloniObjektPost(Model model, @PathVariable("idObjekta") Integer idObjekta) {
		ModelAndView modelAndView = new ModelAndView();
		objektService.izbrisiObjekt(idObjekta);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=0;
		return modelAndView;
	}

	@RequestMapping(value = "/dodaj/usluga", method = RequestMethod.POST)
	public ModelAndView dodajUsluguPost(@ModelAttribute("novaUsluga") DodatnaUsluga usluga, BindingResult result,
			WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		dodatnaUslugaService.dodajNovuUslugu(usluga);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=1;
		return modelAndView;
	}

	@RequestMapping(value = "/ukloni/usluga/{idUsluge}", method = RequestMethod.POST)
	public ModelAndView ukloniUsluguPost(Model model, @PathVariable("idUsluge") Integer idUsluge) {
		ModelAndView modelAndView = new ModelAndView();
		dodatnaUslugaService.izbrisiUslugu(idUsluge);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=1;
		return modelAndView;
	}

	@RequestMapping(value = "/dodaj/tip", method = RequestMethod.POST)
	public ModelAndView dodajTipPost(@ModelAttribute("noviTip") TipSmjestJed tip,
			@RequestParam("file") MultipartFile[] files, BindingResult result, WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		tipSmjestJedService.dodajTipSmjestJed(tip, files);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=2;
		return modelAndView;
	}

	@RequestMapping(value = "/ukloni/tip/{idTipSmjestJed}", method = RequestMethod.POST)
	public ModelAndView ukloniTipPost(Model model, @PathVariable("idTipSmjestJed") Integer idTipSmjestJed) {
		ModelAndView modelAndView = new ModelAndView();
		tipSmjestJedService.izbrisiTip(idTipSmjestJed);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=2;
		return modelAndView;
	}

	@RequestMapping(value = "/dodaj/smjestajnaJedinica", method = RequestMethod.POST)
	public ModelAndView dodajTipPost(@ModelAttribute("novaSmjestJed") SmjestJedForm smjestJedForm, BindingResult result,
			WebRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		smjestJedService.dodajSmjestJed(smjestJedForm);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=3;
		return modelAndView;
	}

	@RequestMapping(value = "/ukloni/smjestajnaJedinica/{idSmjestJed}", method = RequestMethod.POST)
	public ModelAndView ukloniSmjestJedPost(Model model, @PathVariable("idSmjestJed") Integer idSmjestJed) {
		ModelAndView modelAndView = new ModelAndView();
		smjestJedService.izbrisiSmjestJed(idSmjestJed);
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		modelAndView.setViewName("redirect:/smjestaj");
		dodaj = "1";
		trenActive=3;
		return modelAndView;
	}

}
