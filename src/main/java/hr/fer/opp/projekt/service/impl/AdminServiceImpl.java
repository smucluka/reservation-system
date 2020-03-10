package hr.fer.opp.projekt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.constants.Constants;
import hr.fer.opp.projekt.dao.AdminDao;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	KorisnikDao korisnikDao;

	@Override
	public List<Korisnik> getAdminList() {
		return adminDao.getAdminList();
	}

	@Override
	public Korisnik registerNewAdmin(RegistracijaForm noviAdmin) throws EmailExistsException {
		if (emailExist(noviAdmin.getEmail())) {
			throw new EmailExistsException("Već postoji korisnik s email adresom: " + noviAdmin.getEmail());
		}
		Korisnik korisnik = new Korisnik();
		korisnik.setIme(noviAdmin.getIme());
		korisnik.setPrezime(noviAdmin.getPrezime());
		korisnik.setEmail(noviAdmin.getEmail());
		korisnik.setUlicaKbr(noviAdmin.getUlicaKbr());
		korisnik.setGrad(noviAdmin.getGrad());
		korisnik.setDrzava(noviAdmin.getDrzava());
		korisnik.setBrTelefona(noviAdmin.getBrTelefona());
		korisnik.setPassword(noviAdmin.getPassword());
		korisnik.setIdRole(Constants.ROLA_ADMIN);
		korisnik.setEnabled(true);
		return korisnikDao.insertKorisnik(korisnik);
	}

	private boolean emailExist(String email) {

		try {
			korisnikDao.findByEmail(email);
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

}
