package hr.fer.opp.projekt.service.impl;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.service.KontaktService;

@Service
public class KontaktServiceImpl implements KontaktService {

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	KorisnikDao korisnikDao;

	@Override
	public int sendEmail(String email, String text, String ime, String prezime) {

		List<String> admins = korisnikDao.getAdminEmail();

		String[] adminArray = new String[admins.size()];
		adminArray = admins.toArray(adminArray);
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(adminArray);
			mail.setSubject("[Upit od " + ime + " " + prezime + "]");
			mail.setText(text + "\n\n" + "Odgovorite isključivo na korisnikov email: " + email);
			mailSender.send(mail);
		} catch (Exception e) {
			return 2;
		}
		return 1;
	}

}
