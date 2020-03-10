package hr.fer.opp.projekt.service;

import java.text.ParseException;
import java.util.List;

import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;

public interface RezervacijaService {

	boolean obaviRezervaciju(PregledRezervacija pregledRezervacija) throws ParseException;

	void zaprimljenaRezervacija(String name);

	void potvrdenaRezervacijaMail(int korisnikID);

	void ponistenaRezervacijaMail(int korisnikID);

	List<Rezervacija> getRezervacije(Integer idKorisnika) throws ParseException;

	List<Rezervacija> getRezervacijeByStatus(int i) throws ParseException;

	List<Rezervacija> provjeriRok(List<Rezervacija> listRez) throws ParseException;

	void uredenaRezervacija(int korisnikID);


}
