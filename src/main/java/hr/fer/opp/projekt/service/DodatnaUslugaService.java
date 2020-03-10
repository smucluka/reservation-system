package hr.fer.opp.projekt.service;

import java.util.List;

import hr.fer.opp.projekt.model.DodatnaUsluga;

public interface DodatnaUslugaService {
	
	public List<DodatnaUsluga> getUslugaList();
	public void dodajNovuUslugu(DodatnaUsluga usluga);
	public void izbrisiUslugu(Integer idUsluge);

}
