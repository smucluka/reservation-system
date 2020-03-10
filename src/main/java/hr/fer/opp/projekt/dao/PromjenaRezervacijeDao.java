package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;

public interface PromjenaRezervacijeDao {

	List<Rezervacija> getRezervacijeById(int id);

	void napraviPromjenuByRezById(Rezervacija rezervacija);

	void promjeniKapacitetByRezervacijaId(PromjenaRezervacije promjenaRezervacije, int rezervacijaID);

	void promjeniDodatneUslugeByRezervacijaId(PromjenaRezervacije promjenaRezervacije, int rezervacijaID);

}
