package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.DodatnaUsluga;


public interface DodatnaUslugaDao {
	
	public List<DodatnaUsluga> getUslugeList();
	public void dodajNovuUslugu(DodatnaUsluga usluga);
	public void izbrisiUslugu(Integer idUsluge);
	List<DodatnaUsluga> getUsluguById(Integer idRezervacijeDodUsl);

}
