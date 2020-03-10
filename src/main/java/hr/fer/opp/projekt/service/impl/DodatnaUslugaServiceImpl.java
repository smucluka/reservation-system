package hr.fer.opp.projekt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.DodatnaUslugaDao;
import hr.fer.opp.projekt.model.DodatnaUsluga;
import hr.fer.opp.projekt.service.DodatnaUslugaService;

@Service
public class DodatnaUslugaServiceImpl implements DodatnaUslugaService {
	
	@Autowired
	DodatnaUslugaDao dodatnaUslugaDao;

	@Override
	public List<DodatnaUsluga> getUslugaList() {
		return dodatnaUslugaDao.getUslugeList();
	}

	@Override
	public void dodajNovuUslugu(DodatnaUsluga usluga) {
		dodatnaUslugaDao.dodajNovuUslugu(usluga);
	}

	@Override
	public void izbrisiUslugu(Integer idUsluge) {
		dodatnaUslugaDao.izbrisiUslugu(idUsluge);
	}

}
