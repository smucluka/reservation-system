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

import hr.fer.opp.projekt.dao.PretragaDao;
import hr.fer.opp.projekt.dao.PromjenaRezervacijeDao;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.Pretraga;
import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.PromjenaRezervacijeService;
import hr.fer.opp.projekt.service.RezervacijaService;

@Service
public class PromjenaRezervacijeServiceImpl implements PromjenaRezervacijeService {

	@Autowired
	PromjenaRezervacijeDao promjenaRezervacijeDao;
	@Autowired
	RezervacijaService rezervacijaService;
	@Autowired
	PretragaDao pretragaDao;

	@Override
	public String napraviPromjenu(PromjenaRezervacije promjenaRezervacije) throws ParseException {

		List<Rezervacija> staraRezervacijaList = promjenaRezervacijeDao.getRezervacijeById(promjenaRezervacije.getRezervacijaId());

		Rezervacija staraRezervacija = staraRezervacijaList.get(0);
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date datOd = formatter.parse(staraRezervacija.getDatOd());
		Date datDo = formatter.parse(staraRezervacija.getDatDo());

		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		String datOdS = format.format(datOd);
		String datDoS = format.format(datDo);

		if ( (!staraRezervacija.getSmjJed().getTipSmjestJed().getIdTipSmjestJed().equals(promjenaRezervacije.getTipSmjestJedId()))
				|| !promjenaRezervacije.getDatumOd().equals(datOdS)
				|| !promjenaRezervacije.getDatumDo().equals(datDoS)) {

			return promijeniCijeluSmjJed(promjenaRezervacije);

		} else {

			int ukupno = promjenaRezervacije.getBrOdraslih() + promjenaRezervacije.getBrDj01()
					+ promjenaRezervacije.getBrDj27() + promjenaRezervacije.getBrDj814();
			int kapOd = staraRezervacija.getSmjJed().getTipSmjestJed().getKapacitetOd();
			int kapDo = staraRezervacija.getSmjJed().getTipSmjestJed().getKapacitetDo();
			
			if(ukupno < kapOd || ukupno > kapDo) {
				return "Pogrešan kapacitet za rezervirani tip smještajne jedinice!";
			}
			else {
				promjenaRezervacijeDao.promjeniKapacitetByRezervacijaId(promjenaRezervacije, staraRezervacija.getRezervacijaID());
			}
			
			if(!staraRezervacija.getDodUsluge().isEmpty()) {
				if(staraRezervacija.getDodUsluge().get(0).equals(0)) {
					staraRezervacija.setDodUsluge(new ArrayList<>());
				}
			}
			
			promjenaRezervacijeDao.promjeniDodatneUslugeByRezervacijaId(promjenaRezervacije, staraRezervacija.getRezervacijaID());
			
		}

		return "Uspješna promjena parametara rezervacije!";
	}

	public int getDifferenceDays(Date d1, Date d2) {
		int daysdiff = 0;
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		daysdiff = (int) diffDays;
		return daysdiff;
	}

	public String promijeniCijeluSmjJed(PromjenaRezervacije promjenaRezervacije) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		Date datOd = formatter.parse(promjenaRezervacije.getDatumOd());
		Date datDo = formatter.parse(promjenaRezervacije.getDatumDo());

		List<PregledSmjestajnaJedinica> smjestJedList = pretragaDao
				.getSmjestJedByTip(promjenaRezervacije.getTipSmjestJedId());

		// PROVJERI KAPACITET NA TEMELJU BILO KOJEG TIPA SMJESTAJNE JEDINICE

		if (smjestJedList.isEmpty()) {
			return "Nema slobodnih jedinica za traženi tip!";
		} else {

			int ukupno = promjenaRezervacije.getBrOdraslih() + promjenaRezervacije.getBrDj01()
					+ promjenaRezervacije.getBrDj27() + promjenaRezervacije.getBrDj814();
			int kapOd = smjestJedList.get(0).getTipSmjestJed().getKapacitetOd();
			int kapDo = smjestJedList.get(0).getTipSmjestJed().getKapacitetDo();

			if (ukupno < kapOd || ukupno > kapDo) {
				return "Pogrešan kapacitet za traženi tip smještajne jedinice!";
			}

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
				if (sj.getRezervacijaId().equals(promjenaRezervacije.getRezervacijaId())) {
					sj.setDatOd(null);
					sj.setDatDo(null);
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

			if (pomMapaSmjJed.isEmpty()) {
				return "Nije moguće obaviti promjenu rezervacije";
			}

			// odabrati najbolju jedinicu na temelju datuma
			PregledSmjestajnaJedinica odabranaJedinica = new PregledSmjestajnaJedinica();
			Map<Integer, Integer> mapaOptimizacije = new HashMap<>();
			Map<Integer, Integer> mapaPrazneOptimizacije = new HashMap<>();
			DateFormat formats = new SimpleDateFormat("yyyy-MM-dd");

			for (Map.Entry<Integer, List<PregledSmjestajnaJedinica>> entry : pomMapaSmjJed.entrySet()) {

				for (PregledSmjestajnaJedinica psj : entry.getValue()) {

					if (psj.getDatOd() != null && psj.getDatDo() != null) {
						Date datOdZahtjev = datOd;
						Date datDoZahtjev = datDo;
						Date datOdBaza = formats.parse(psj.getDatOd());
						Date datDoBaza = formats.parse(psj.getDatDo());

						Integer razlika = 0;
						if (datOdZahtjev.equals(datDoBaza) || datOdZahtjev.after(datDoBaza)) {
							if (datOdZahtjev.equals(datDoBaza))
								razlika = 1000;
							else
								razlika = getDifferenceDays(datOdZahtjev, datDoBaza);
						} else {
							if (datOdBaza.equals(datDoZahtjev))
								razlika = 1000;
							else
								razlika = getDifferenceDays(datOdBaza, datDoZahtjev);
						}

						if (razlika < 0)
							razlika = 0 - razlika;
						if (razlika == 1000) {
							razlika = -1;
						}
						razlika++;

						if (mapaOptimizacije.containsKey(psj.getIdSmjestJed())) {
							Integer currValue = mapaOptimizacije.get(psj.getIdSmjestJed());
							if (razlika < currValue) {
								mapaOptimizacije.put(psj.getIdSmjestJed(), razlika);
							}
						} else {
							mapaOptimizacije.put(psj.getIdSmjestJed(), razlika);
						}

					} else {
						if (mapaPrazneOptimizacije.containsKey(psj.getIdSmjestJed())) {
							mapaPrazneOptimizacije.put(psj.getIdSmjestJed(), 0);
						} else {
							mapaPrazneOptimizacije.put(psj.getIdSmjestJed(), 0);
						}
					}
				}

			}

			Integer currentMinValue = -1;
			Integer currentID = -1;
			for (Map.Entry<Integer, Integer> entry : mapaOptimizacije.entrySet()) {

				if (currentMinValue == -1) {
					currentMinValue = entry.getValue();
					currentID = entry.getKey();
				} else if (currentMinValue > entry.getValue()) {
					currentMinValue = entry.getValue();
					currentID = entry.getKey();
				}
			}

			if (currentID == -1) {
				for (Map.Entry<Integer, Integer> entry : mapaPrazneOptimizacije.entrySet()) {
					currentID = entry.getKey();
					break;
				}
			}

			// kraj
			Rezervacija rezervacija = new Rezervacija();

			SmjestajnaJedinica smjJed = new SmjestajnaJedinica();

			smjJed.setIdSmjestJed(currentID);
			rezervacija.setRezervacijaID(promjenaRezervacije.getRezervacijaId());
			rezervacija.setSmjJed(smjJed);
			rezervacija.setBrOdraslih(String.valueOf(promjenaRezervacije.getBrOdraslih()));
			rezervacija.setBrDjece0_1(String.valueOf(promjenaRezervacije.getBrDj01()));
			rezervacija.setBrDjece2_7(String.valueOf(promjenaRezervacije.getBrDj27()));
			rezervacija.setBrDjece8_14(String.valueOf(promjenaRezervacije.getBrDj814()));
			rezervacija.setDodUsluge(promjenaRezervacije.getDodUsluge());

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateFrom = format.format(datOd);
			String dateTo = format.format(datDo);

			rezervacija.setDatOd(dateFrom);
			rezervacija.setDatDo(dateTo);

			promjenaRezervacijeDao.napraviPromjenuByRezById(rezervacija);
			return "Uspješna promjena parametara rezervacije!";
		}
	}

}
