package hr.fer.opp.projekt.form.validation;

import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.model.Korisnik;

public class RegistracijaFormValidator {

	public Korisnik validate(RegistracijaForm registracijaForm) {
		
		if(registracijaForm.getIme().trim().contains(" ") || registracijaForm.getIme().trim().isEmpty()) {
			return null;
		}
		if(registracijaForm.getPrezime().trim().contains(" ") || registracijaForm.getPrezime().trim().isEmpty()) {
			return null;
		}
		if(registracijaForm.getEmail().trim().contains(" ") || registracijaForm.getEmail().trim().isEmpty()) {
			return null;
		}
		if( registracijaForm.getDrzava().trim().isEmpty()) {
			return null;
		}
		if( registracijaForm.getGrad().trim().isEmpty()) {
			return null;
		}
		if(registracijaForm.getUlicaKbr().trim().isEmpty()) {
			return null;
		}
		if(registracijaForm.getPassword().trim().isEmpty()) {
			return null;
		}
		if(registracijaForm.getPonovljeniPassword().trim().contains(" ") || registracijaForm.getPonovljeniPassword().trim().isEmpty()) {
			return null;
		}
		
		if(!registracijaForm.getPonovljeniPassword().equals(registracijaForm.getPassword())) {
			return null;
		}
		return new Korisnik();
		
		
	}

}
