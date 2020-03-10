package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.Rang;

public interface StatistikaDao {

	List<Rang> getRangDrzava();

	List<Rang> getRangGrad();

	List<Rang>  getRangUsluge();
	
	List<Rang> getRangUslugeDrzava();
	
	List<Rang> getRangUslugeBrGostiju();

}
