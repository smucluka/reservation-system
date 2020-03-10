package hr.fer.opp.projekt.service;

import java.util.List;

import hr.fer.opp.projekt.exception.EmailExistsException;
import hr.fer.opp.projekt.form.RegistracijaForm;
import hr.fer.opp.projekt.model.Korisnik;

public interface AdminService {
	
	public List<Korisnik> getAdminList();
	public Korisnik registerNewAdmin(RegistracijaForm noviAdmin) throws EmailExistsException;

}
