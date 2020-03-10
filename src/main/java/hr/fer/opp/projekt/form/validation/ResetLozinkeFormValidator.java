package hr.fer.opp.projekt.form.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.form.ResetLozinkeForm;

@Component
public class ResetLozinkeFormValidator implements Validator {
	
	@Autowired
	KorisnikDao korisnikDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return ResetLozinkeForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ResetLozinkeForm form = (ResetLozinkeForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EmptyEmail");
		
		try {
			korisnikDao.findByEmail(form.getEmail());
		} catch(Exception ex) {
			errors.rejectValue("email", "NepostojeciEmail","Ne postoji korisnik s unesenim emailom.");
		}
		
	}

}
