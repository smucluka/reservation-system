package hr.fer.opp.projekt.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.OnamaDao;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.OnamaService;

@Service
public class OnamaServiceImpl implements OnamaService{

	@Autowired
	OnamaDao tipSmjestJedDao;
	
	@Override
	public List<TipSmjestJed> dohvatiTipove() throws ParseException {
		return tipSmjestJedDao.getTipSmjestajneJedinice();
	}

}
