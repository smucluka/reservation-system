package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.Pretraga;
import hr.fer.opp.projekt.model.Slika;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;

public interface PretragaDao {

	void getSmjestajneJedinice();

	List<PregledSmjestajnaJedinica> getSmjestJedByKapacitet(String kapacitet);

	List<PregledSmjestajnaJedinica> getSmjestJedByTip(Integer tipId);

	List<Slika> getSlike();

}
