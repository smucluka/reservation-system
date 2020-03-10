package hr.fer.opp.projekt.service;

import java.text.ParseException;
import java.util.List;

import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.model.Pretraga;

public interface PretragaService {

	List<TipSmjestJed> dohvatiPregledTipove(Pretraga pretraga) throws ParseException;

}
