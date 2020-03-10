package hr.fer.opp.projekt.service;

import java.text.ParseException;
import java.util.List;

import hr.fer.opp.projekt.model.Rang;

public interface StatistikaService {

	List<Rang> getRangSmjestJed() throws ParseException;

}
