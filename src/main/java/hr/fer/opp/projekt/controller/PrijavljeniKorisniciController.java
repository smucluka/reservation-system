package hr.fer.opp.projekt.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.model.CustomUserDetails;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Role;

@Controller
@PreAuthorize("hasAnyAuthority('VLASNIK', 'ADMIN')")
public class PrijavljeniKorisniciController {

	@Autowired
	SessionRegistry sessionRegistry;

	@RequestMapping(value = "/prijavljeniKorisnici", method = RequestMethod.GET)
	public ModelAndView pregledPrijavljenihKorisnikaGet(Principal pPrincipal, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		String trenKorEmail = pPrincipal.getName();

		List<Object> principals = sessionRegistry.getAllPrincipals();

		List<Korisnik> korisnikList = new ArrayList<>();
		List<Korisnik> adminList = new ArrayList<>();

		for (Object principal : principals) {
			if (principal instanceof CustomUserDetails) {
				List<Role> lR = (List<Role>) ((CustomUserDetails) principal).getAuthorities();
				if (!((CustomUserDetails) principal).getUsername().equals(trenKorEmail)) {
					Korisnik korisnik = new Korisnik();
					korisnik.setIme(((CustomUserDetails) principal).getFirstName());
					korisnik.setPrezime(((CustomUserDetails) principal).getLastName());
					korisnik.setEmail(((CustomUserDetails) principal).getUsername());
					if (lR.get(0).getIdRole() == 3)
						korisnikList.add(korisnik);
					else if (lR.get(0).getIdRole() == 2)
						adminList.add(korisnik);
				}
			}
		}
		model.addAttribute("classActivePrijavljeniKorisnici", "active");
		model.addAttribute("classActiveVlasnikSucelje", "active");
		model.addAttribute("classActiveAdminSucelje", "active");
		model.addAttribute("korisnikList", korisnikList);
		model.addAttribute("adminList", adminList);
		modelAndView.setViewName("prijavljeniKorisnici");
		return modelAndView;
	}

}
