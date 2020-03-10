package hr.fer.opp.projekt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.ObjektDao;
import hr.fer.opp.projekt.model.Objekt;
import hr.fer.opp.projekt.service.ObjektService;

@Service
public class ObjektServiceImpl implements ObjektService {
	
	@Autowired
	ObjektDao objektDao;

	@Override
	public List<Objekt> getObjektList() {
		return objektDao.getObjektList();
	}

	@Override
	public void dodajNoviObjekt(Objekt objekt) {
		objektDao.dodajNoviObjekt(objekt);		
	}

	@Override
	public void izbrisiObjekt(Integer idObjekta) {
		objektDao.izbrisiObjekt(idObjekta);
	}

}
