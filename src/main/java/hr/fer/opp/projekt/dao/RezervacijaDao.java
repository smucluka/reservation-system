package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;

public interface RezervacijaDao{

	void insertRezervacija(Rezervacija rezervacija);

	List<Rezervacija> getRezervacijeByStatus(int sts);

	void potvrdiOdbijRezervaciju(Integer rezervacijaID, Integer status);

	List<Rezervacija> getRezervacijeByKor(int korId);

}
