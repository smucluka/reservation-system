package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.Objekt;

public interface ObjektDao {
	
	public List<Objekt> getObjektList();
	public void dodajNoviObjekt(Objekt objekt);
	public void izbrisiObjekt(Integer idObjekta);

}
