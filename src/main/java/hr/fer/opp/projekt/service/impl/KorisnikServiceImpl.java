package hr.fer.opp.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.constants.Constants;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PromjeniAdmin;
import hr.fer.opp.projekt.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService {
	
	@Autowired
	private KorisnikDao korisnikDao;
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public Korisnik registerNewUserAccount(RegistracijaForm registracijaForm) throws EmailExistsException {
		
		if(emailExist(registracijaForm.getEmail())) {
			throw new EmailExistsException("Već postoji korisnik s email adresom: " + registracijaForm.getEmail());
		}
		Korisnik korisnik = new Korisnik();
		korisnik.setIme(registracijaForm.getIme());
		korisnik.setPrezime(registracijaForm.getPrezime());
		korisnik.setEmail(registracijaForm.getEmail());
		korisnik.setUlicaKbr(registracijaForm.getUlicaKbr());
		korisnik.setGrad(registracijaForm.getGrad());
		korisnik.setDrzava(registracijaForm.getDrzava());
		korisnik.setBrTelefona(registracijaForm.getBrTelefona());
		korisnik.setPassword(registracijaForm.getPassword());
		korisnik.setIdRole(Constants.ROLA_KORISNIK);
		return korisnikDao.insertKorisnik(korisnik);
	}
	
	private boolean emailExist(String email) {
		try {
			korisnikDao.findByEmail(email);
		} catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public void sendActivationEmail(String url, String sendTo, String token) {
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(sendTo);
        email.setSubject("Potvrda registracije");
        String confirmationUrl = url + "/potvrdaRegistracije?token=" + token;
        email.setText("Aktivirajte profil klikom na link: " + " " + "http://kodnasjenajljepse.azurewebsites.net" + confirmationUrl);
        mailSender.send(email);
	}

	@Override
	public void activateUser(Integer idKorisnika) {
		korisnikDao.updateKorisnik(idKorisnika, 1);
		
	}

	@Override
	public boolean validateUser(Korisnik korisnik, PromjeniAdmin promjeniAdmin) {
		
		if(!promjeniAdmin.getEmailRealAdmin().trim().equals(korisnik.getEmail())) {
			if(emailExist(korisnik.getEmail())) {
				return false;
			}
		}
		if(korisnik.getPrezime().trim().contains(" ") || korisnik.getPrezime().trim().isEmpty()) {
			return false;
		}
		if(korisnik.getEmail().trim().contains(" ") || korisnik.getEmail().trim().isEmpty()) {
			return false;
		}
		if( korisnik.getDrzava().trim().isEmpty()) {
			return false;
		}
		if( korisnik.getGrad().trim().isEmpty()) {
			return false;
		}
		if(korisnik.getUlicaKbr().trim().isEmpty()) {
			return false;
		}
		return true;
		
	}

}
