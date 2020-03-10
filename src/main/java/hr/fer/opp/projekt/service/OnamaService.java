package hr.fer.opp.projekt.service;

import java.text.ParseException;
import java.util.List;

import hr.fer.opp.projekt.model.TipSmjestJed;

public interface OnamaService {
	public List<TipSmjestJed> dohvatiTipove() throws ParseException;
}
