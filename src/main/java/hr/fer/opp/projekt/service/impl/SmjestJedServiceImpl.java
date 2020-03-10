package hr.fer.opp.projekt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.SmjestJedDao;
import hr.fer.opp.projekt.form.SmjestJedForm;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.service.SmjestJedService;

@Service
public class SmjestJedServiceImpl implements SmjestJedService {
	
	@Autowired
	SmjestJedDao smjestJedDao;

	@Override
	public void dodajSmjestJed(SmjestJedForm smjestJedForm) {
		smjestJedDao.dodajSmjestJed(smjestJedForm);
	}

	@Override
	public List<SmjestajnaJedinica> getSmjestJedList() {
		return smjestJedDao.getSmjestJedList();
	}

	@Override
	public void izbrisiSmjestJed(Integer idSmjestJed) {
		smjestJedDao.izbrisiSmjestJed(idSmjestJed);
	}

}
