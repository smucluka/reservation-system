package hr.fer.opp.projekt.service;

import java.util.List;

import hr.fer.opp.projekt.model.Objekt;

public interface ObjektService {
	
	public List<Objekt> getObjektList();
	public void dodajNoviObjekt(Objekt objekt);
	public void izbrisiObjekt(Integer idObjekta);

}
