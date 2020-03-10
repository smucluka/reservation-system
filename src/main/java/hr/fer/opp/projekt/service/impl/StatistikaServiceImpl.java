package hr.fer.opp.projekt.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.model.Rang;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.service.StatistikaService;

@Service
public class StatistikaServiceImpl implements StatistikaService{

	@Autowired
	RezervacijaDao rezervacijaDao;
	
	@Override
	public List<Rang> getRangSmjestJed() throws ParseException {
		Map<Integer, Rang> mapRang = new HashMap<>();
		List<Rezervacija> listRez = rezervacijaDao.getRezervacijeByStatus(1);
		
		for(Rezervacija rez : listRez) {
			
			if(mapRang.containsKey(rez.getSmjJed().getIdSmjestJed())) {

				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				Date datOd = formatter.parse(rez.getDatOd());
				Date datDo = formatter.parse(rez.getDatDo());
				
				Integer tempBrDana = getDifferenceDays(datOd, datDo);
				
				Integer value = mapRang.get(rez.getSmjJed().getIdSmjestJed()).getValue();
				Integer value2 = mapRang.get(rez.getSmjJed().getIdSmjestJed()).getValue2();
				
				mapRang.get(rez.getSmjJed().getIdSmjestJed()).setValue(value+1);
				mapRang.get(rez.getSmjJed().getIdSmjestJed()).setValue2(value2+tempBrDana);
				
			}else {

				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				Date datOd = formatter.parse(rez.getDatOd());
				Date datDo = formatter.parse(rez.getDatDo());
				
				Integer tempBrDana = getDifferenceDays(datOd, datDo);
				
				Rang rang = new Rang();
				rang.setName(rez.getSmjJed().getNazSmjestJed());
				rang.setValue(1);
				rang.setValue2(tempBrDana);
				
				mapRang.put(rez.getSmjJed().getIdSmjestJed(), rang);
			}
			
		}
		List<Rang> listRang = new ArrayList<Rang>(mapRang.values());
		return listRang;
	}

	public int getDifferenceDays(Date d1, Date d2) {
		int daysdiff = 0;
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		daysdiff = (int) diffDays;

		return daysdiff;
	}
	
}
