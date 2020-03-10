package hr.fer.opp.projekt.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.PretragaDao;
import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.Pretraga;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.Slika;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.service.PretragaService;

@Service
public class PretragaServiceImpl implements PretragaService {

	@Autowired
	PretragaDao pretragaDao;

	@Override
	public List<TipSmjestJed> dohvatiPregledTipove(Pretraga pretraga) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		Date datOd = formatter.parse(pretraga.getDatumOd());
		Date datDo = formatter.parse(pretraga.getDatumDo());

		List<PregledSmjestajnaJedinica> smjestJedList = pretragaDao.getSmjestJedByKapacitet(pretraga.getKapacitet());

		// Razvrstne smjestajne jedinice po id-evima
		Map<Integer, List<PregledSmjestajnaJedinica>> mapaSmjJed = new HashMap<>();

		for (PregledSmjestajnaJedinica sj : smjestJedList) {
			Integer idSmjJed = sj.getIdSmjestJed();

			if (sj.getStatus() != null) {
				if (sj.getStatus().equals("2")) {
					sj.setDatDo(null);
					sj.setDatOd(null);
				}
			}

			if (mapaSmjJed.containsKey(idSmjJed)) {
				List<PregledSmjestajnaJedinica> smjTempList = mapaSmjJed.get(idSmjJed);
				smjTempList.add(sj);
				mapaSmjJed.put(idSmjJed, smjTempList);
			} else {
				List<PregledSmjestajnaJedinica> smjTempList = new ArrayList<>();
				smjTempList.add(sj);
				mapaSmjJed.put(idSmjJed, smjTempList);
			}
		}

		// Provjeri datume za pojedine jedinice
		Map<Integer, List<PregledSmjestajnaJedinica>> pomMapaSmjJed = new HashMap<>(mapaSmjJed);
		for (Map.Entry<Integer, List<PregledSmjestajnaJedinica>> entry : mapaSmjJed.entrySet()) {

			List<PregledSmjestajnaJedinica> smjJedList = entry.getValue();

			for (PregledSmjestajnaJedinica iterator : smjJedList) {

				String dateFromS = iterator.getDatOd();
				String dateToS = iterator.getDatDo();

				if (dateFromS == null || dateToS == null) {
					break;
				}

				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFrom = format.parse(dateFromS);
				Date dateTo = format.parse(dateToS);

				if (datOd.equals(dateFrom) && datDo.equals(dateTo)) {
					pomMapaSmjJed.remove(entry.getKey());
					break;
				}
				if (datOd.after(dateFrom) && datOd.before(dateTo)) {
					pomMapaSmjJed.remove(entry.getKey());
					break;
				} else if (datDo.after(dateFrom) && datDo.before(dateTo)) {
					pomMapaSmjJed.remove(entry.getKey());
					break;
				} else if (dateFrom.after(datOd) && dateFrom.before(datDo)) {
					pomMapaSmjJed.remove(entry.getKey());
					break;
				} else if (dateTo.after(datOd) && dateTo.before(datDo)) {
					pomMapaSmjJed.remove(entry.getKey());
					break;
				}

			}

		}

		// Vrati distinct listu tipova jedinica
		List<Integer> idTipList = new ArrayList<>();
		List<TipSmjestJed> tipList = new ArrayList<>();
		for (Map.Entry<Integer, List<PregledSmjestajnaJedinica>> entry : pomMapaSmjJed.entrySet()) {

			if (!idTipList.contains(entry.getValue().get(0).getTipSmjestJed().getIdTipSmjestJed())) {
				idTipList.add(entry.getValue().get(0).getTipSmjestJed().getIdTipSmjestJed());
				tipList.add(entry.getValue().get(0).getTipSmjestJed());
			}
		}

		List<Slika> slikaList = pretragaDao.getSlike();
		Map<Integer, List<String>> slikaMap = new HashMap<>();

		for (Slika slika : slikaList) {
			if (slikaMap.containsKey(slika.getSlikaTipId())) {
				slikaMap.get(slika.getSlikaTipId()).add(slika.getSlika());
			} else {
				List<String> slikaTempList = new ArrayList<>();
				slikaTempList.add(slika.getSlika());
				slikaMap.put(slika.getSlikaTipId(), slikaTempList);
			}
		}

		for (TipSmjestJed tipSmjestJed : tipList) {
			if (slikaMap.containsKey(tipSmjestJed.getIdTipSmjestJed())) {
				tipSmjestJed.setSlika(slikaMap.get(tipSmjestJed.getIdTipSmjestJed()));
			}
		}

		return tipList;
	}

}
