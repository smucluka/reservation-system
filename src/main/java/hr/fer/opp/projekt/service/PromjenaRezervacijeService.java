package hr.fer.opp.projekt.service;

import java.text.ParseException;

import hr.fer.opp.projekt.model.PromjenaRezervacije;

public interface PromjenaRezervacijeService {

	String napraviPromjenu(PromjenaRezervacije promjenaRezervacije) throws ParseException;

}
