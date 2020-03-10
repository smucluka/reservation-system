package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PromjeniAdmin;

public interface KorisnikService {
	
	public Korisnik registerNewUserAccount(RegistracijaForm registracijaForm) throws EmailExistsException;
	public void activateUser(Integer idKorisnika);
	public void sendActivationEmail(String url, String sendTo, String token);
	boolean validateUser(Korisnik korisnik, PromjeniAdmin promjeniAdmin);

}
