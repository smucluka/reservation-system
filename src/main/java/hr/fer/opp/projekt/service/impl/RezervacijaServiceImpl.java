package hr.fer.opp.projekt.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.dao.PretragaDao;
import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.PregledRezervacija;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.RezervacijaService;

@Service
public class RezervacijaServiceImpl implements RezervacijaService {

	@Autowired
	RezervacijaDao rezervacijaDao;
	@Autowired
	PretragaDao pretragaDao;
	@Autowired
	KorisnikDao korisnikDao;
	@Autowired
	JavaMailSender mailSender;

	@Override
	public boolean obaviRezervaciju(PregledRezervacija pregledRezervacija) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		Date datOd = formatter.parse(pregledRezervacija.getDatumOd());
		Date datDo = formatter.parse(pregledRezervacija.getDatumDo());

		List<PregledSmjestajnaJedinica> smjestJedList = pretragaDao.getSmjestJedByTip(pregledRezervacija.getTipID());

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

		if (pomMapaSmjJed.isEmpty())
			return false;

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

		rezervacija.setSmjJed(smjJed);

		Korisnik korisnik = new Korisnik();
		korisnik.setIdKorisnika(pregledRezervacija.getKorisnikID());

		rezervacija.setKorisnik(korisnik);
		rezervacija.setBrOdraslih(pregledRezervacija.getBrOdraslih());
		rezervacija.setBrDjece0_1(pregledRezervacija.getBrDjece0_1());
		rezervacija.setBrDjece2_7(pregledRezervacija.getBrDjece2_7());
		rezervacija.setBrDjece8_14(pregledRezervacija.getBrDjece8_14());
		rezervacija.setDodUsluge(pregledRezervacija.getDodUsluge());

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = format.format(datOd);
		String dateTo = format.format(datDo);

		rezervacija.setDatOd(dateFrom);
		rezervacija.setDatDo(dateTo);

		rezervacijaDao.insertRezervacija(rezervacija);

		return true;
	}

	public int getDifferenceDays(Date d1, Date d2) {
		int daysdiff = 0;
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		daysdiff = (int) diffDays;
		return daysdiff;
	}

	@Override
	public void zaprimljenaRezervacija(String name) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(name);
		email.setSubject("Zaprimljena rezervacija");
		email.setText(
				"Vaša rezervacija je zaprimljena. Administrator će u roku od 3 dana potvrditi/odbiti vašu rezervaciju. Pogledajte svoje rezervacije na: http://kodnasjenajljepse.azurewebsites.net/korisnik/rezervacije");
		mailSender.send(email);
	}

	@Override
	public void potvrdenaRezervacijaMail(int korisnikID) {

		String name = korisnikDao.getEmailById(korisnikID).getEmail();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(name);
		email.setSubject("Potvrđena rezervacija");
		email.setText(
				"Vaša rezervacija je potvrđena. Više detalja o rezervaciji pogledajte na: http://http://kodnasjenajljepse.azurewebsites.net/korisnik/rezervacije");
		mailSender.send(email);
	}

	@Override
	public void ponistenaRezervacijaMail(int korisnikID) {

		String name = korisnikDao.getEmailById(korisnikID).getEmail();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(name);
		email.setSubject("Odbijena rezervacija");
		email.setText(
				"Vaša rezervacija je poništena. Više detalja o rezervaciji pogledajte na: http://http://kodnasjenajljepse.azurewebsites.net/korisnik/rezervacije");
		mailSender.send(email);
	}

	@Override
	public List<Rezervacija> getRezervacije(Integer idKorisnika) throws ParseException {
		List<Rezervacija> rezList = rezervacijaDao.getRezervacijeByKor(idKorisnika);

		Collections.sort(rezList, new Comparator<Rezervacija>() {
			public int compare(Rezervacija o1, Rezervacija o2) {

				DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

				Date d1 = null;
				Date d2 = null;
				try {
					d1 = df.parse(o1.getDatRez());
					d2 = df.parse(o2.getDatRez());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return d1.before(d2) ? 1 : -1;
			}
		});

		for (Rezervacija rez : rezList) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dfRez = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			DateFormat formatterRez = new SimpleDateFormat("dd.MM.yyyy HH:mm");

			Date d1 = df.parse(rez.getDatOd());
			Date d2 = df.parse(rez.getDatDo());

			if (rez.getDatRez() != null) {
				Date d3 = dfRez.parse(rez.getDatRez());

				DateTime date = new DateTime(d3);
				d3 = date.toDate();
				String d33 = formatterRez.format(d3);
				rez.setDatRez(d33);
			}
			

			String d11 = formatter.format(d1);
			String d22 = formatter.format(d2);

			rez.setDatOd(d11);
			rez.setDatDo(d22);

			provjeri(rez);

		}
		return rezList;
	}

	@Override
	public List<Rezervacija> getRezervacijeByStatus(int i) throws ParseException {
		List<Rezervacija> rezList = rezervacijaDao.getRezervacijeByStatus(i);

		Collections.sort(rezList, new Comparator<Rezervacija>() {
			public int compare(Rezervacija o1, Rezervacija o2) {

				DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

				Date d1 = null;
				Date d2 = null;
				try {
					d1 = df.parse(o1.getDatRez());
					d2 = df.parse(o2.getDatRez());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return d1.before(d2) ? -1 : 1;
			}
		});

		for (Rezervacija rez : rezList) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dfRez = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			DateFormat formatterRez = new SimpleDateFormat("dd.MM.yyyy HH:mm");

			Date d1 = df.parse(rez.getDatOd());
			Date d2 = df.parse(rez.getDatDo());
			if (rez.getDatRez() != null) {
				Date d3 = dfRez.parse(rez.getDatRez());

				DateTime date = new DateTime(d3);
				d3 = date.toDate();
				String d33 = formatterRez.format(d3);
				rez.setDatRez(d33);
			}

			String d11 = formatter.format(d1);
			String d22 = formatter.format(d2);

			rez.setDatOd(d11);
			rez.setDatDo(d22);

			provjeri(rez);

		}
		return rezList;
	}

	public void provjeri(Rezervacija rez) {
		if (rez.getBrDjece0_1() == null) {
			rez.setBrDjece0_1("NEPOZNAT BROJ");
		}
		if (rez.getBrDjece2_7() == null) {
			rez.setBrDjece2_7("NEPOZNAT BROJ");
		}
		if (rez.getBrDjece8_14() == null) {
			rez.setBrDjece8_14("NEPOZNAT BROJ");
		}
		if (rez.getBrOdraslih() == null) {
			rez.setBrOdraslih("NEPOZNAT BROJ");
		}
		if (rez.getDatOd() == null) {
			rez.setDatOd("NEPOZNATD");
		}
		if (rez.getDatDo() == null) {
			rez.setDatDo("NEPOZNATO");
		}
		if (rez.getDatRez() == null) {
			rez.setDatDo("NEPOZNAT DATUM REZERVACIJE");
		}
		if (rez.getKorisnik().getIme() == null) {
			rez.getKorisnik().setIme("NEPOZNATO IME");
		}
		if (rez.getKorisnik().getPrezime() == null) {
			rez.getKorisnik().setPrezime("NEPOZNATO PREZIME");
		}
		if (rez.getKorisnik().getEmail() == null) {
			rez.getKorisnik().setEmail("NEPOZNAT EMAIL");
		}
		if (rez.getKorisnik().getDrzava() == null) {
			rez.getKorisnik().setDrzava("NEPOZNATA DRZAVA");
		}
		if (rez.getKorisnik().getGrad() == null) {
			rez.getKorisnik().setGrad("NEPOZNAT GRAD");
		}
		if (rez.getKorisnik().getUlicaKbr() == null) {
			rez.getKorisnik().setUlicaKbr("NEPOZNATA ADRESA");
		}
		if (rez.getKorisnik().getBrTelefona() == null) {
			rez.getKorisnik().setBrTelefona("Kontakt broj nije zabilježen");
		}
		if (rez.getSmjJed().getTipSmjestJed().getTipSmjestJed() == null) {
			rez.getSmjJed().getTipSmjestJed().setTipSmjestJed("NEPOZNAT TIP");
		}
		if (rez.getSmjJed().getNazSmjestJed() == null) {
			rez.getSmjJed().setNazSmjestJed("NEPOZNAT NAZIV");
		}
		if (rez.getDodUslugeOpis().isEmpty() || rez.getDodUslugeOpis().get(0) == null) {
			rez.getDodUslugeOpis().removeAll(rez.getDodUslugeOpis());
		}
		if (rez.getDodUsluge().isEmpty() || rez.getDodUsluge().get(0) == null) {
			rez.getDodUsluge().removeAll(rez.getDodUsluge());
		}
	}

	@Override
	public List<Rezervacija> provjeriRok(List<Rezervacija> listRez) throws ParseException {
		DateFormat formatterRez = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		List<Rezervacija> pomList = new LinkedList<>();
		for(Rezervacija rez : listRez) {
			Date d3 = formatterRez.parse(rez.getDatRez());
			
			int razlika = getDifferenceDays(d3, new Date());
			if(razlika>=3) {
				rezervacijaDao.potvrdiOdbijRezervaciju(rez.getRezervacijaID(), 2);
				ponistenaRezervacijaMail(rez.getKorisnik().getIdKorisnika());
				continue;
			}else {
				pomList.add(rez);
			}
			
		}
		
		return pomList;
	}

	@Override
	public void uredenaRezervacija(int korisnikID) {
		
		String name = korisnikDao.getEmailById(korisnikID).getEmail();
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(name);
		email.setSubject("Zaprimljena rezervacija");
		email.setText("Vaša rezervacija je uređena. Pogledajte svoje rezervacije na: http://kodnasjenajljepse.azurewebsites.net/korisnik/rezervacije \nUkoliko želite daljnje promjene kontaktirajte nas na:"
				+ "http://kodnasjenajljepse.azurewebsites.net/kontakt");
		mailSender.send(email);
	}

}
