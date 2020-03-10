package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.TipSmjestJed;

public interface TipSmjestJedDao {
	
	public Integer dodajTipSmjestJed(TipSmjestJed tipSmjestJed);
	public List<TipSmjestJed> getTipSmjestJedList();
	public void izbrisiTip(Integer idTipSmjestJed);

}
