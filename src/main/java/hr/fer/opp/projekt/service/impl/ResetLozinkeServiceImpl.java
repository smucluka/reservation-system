package hr.fer.opp.projekt.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.service.ResetLozinkeService;

@Service
public class ResetLozinkeServiceImpl implements ResetLozinkeService {
	
	@Autowired
	KorisnikDao korisnikDao;
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void resetirajLozinku(String email) {
		String novaLozinka;
		novaLozinka = RandomStringUtils.randomAlphanumeric(10);
		korisnikDao.azurirajLozinku(novaLozinka, email);
		SimpleMailMessage emailMsg = new SimpleMailMessage();
        emailMsg.setTo(email);
        emailMsg.setSubject("Reset lozinke");
        emailMsg.setText("Na vaš zahtjev resetirana vam je lozinka.\nNova lozinka je: " + novaLozinka);
        mailSender.send(emailMsg);
	}

}
