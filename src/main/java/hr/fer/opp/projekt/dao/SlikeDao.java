package hr.fer.opp.projekt.dao;

import java.util.List;

public interface SlikeDao {
	
	public void insertBatch(Integer idTipSmjestJed, List<String> slika);

}
