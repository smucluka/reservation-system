package hr.fer.opp.projekt.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.AktivacijskiTokenDao;
import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.AktivacijskiToken;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.service.AktivacijaService;

@Service
public class AktivacijaServiceImpl implements AktivacijaService {
	
	@Autowired
	KorisnikDao korisnikDao;
	
	@Autowired
	AktivacijskiTokenDao aktivacijskiTokenDao;

	@Override
	public String generirajToken(String email) {
		Korisnik korisnik = korisnikDao.findByEmail(email);
		String token = UUID.randomUUID().toString();
		aktivacijskiTokenDao.insertToken(new AktivacijskiToken(token, korisnik.getIdKorisnika()));
		return token;
	}

	@Override
	public AktivacijskiToken getAktivacijskiToken(String token) {
		return aktivacijskiTokenDao.getAktivacijskiToken(token);
	}

}
