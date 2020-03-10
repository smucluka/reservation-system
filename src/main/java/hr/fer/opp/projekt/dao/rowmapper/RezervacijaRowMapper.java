package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.Korisnik;
import hr.fer.opp.projekt.model.Objekt;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;

public class RezervacijaRowMapper implements RowMapper<Rezervacija> {

	@Override
	public Rezervacija mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Rezervacija rezervacija = new Rezervacija();
		
		rezervacija.setRezervacijaID(rs.getInt("ID_REZERVACIJA"));
		rezervacija.setBrOdraslih(rs.getString("BR_ODRASLIH"));
		rezervacija.setBrDjece0_1(rs.getString("BR_DJECE_0_1"));
		rezervacija.setBrDjece2_7(rs.getString("BR_DJECE_2_7"));
		rezervacija.setBrDjece8_14(rs.getString("BR_DJECE_8_14"));
		rezervacija.setStatus(rs.getInt("STATUS"));
		rezervacija.setDatOd(rs.getString("DAT_OD"));
		rezervacija.setDatDo(rs.getString("DAT_DO"));
		rezervacija.setDatRez(rs.getString("TIMESTAMP"));
		
		Korisnik korisnik = new Korisnik();
		korisnik.setIdKorisnika(rs.getInt("ID_KORISNIKA"));
		korisnik.setEmail(rs.getString("EMAIL"));
		korisnik.setIme(rs.getString("IME"));
		korisnik.setPrezime(rs.getString("PREZIME"));
		korisnik.setDrzava(rs.getString("DRZAVA"));
		korisnik.setGrad(rs.getString("GRAD"));
		korisnik.setUlicaKbr(rs.getString("ULICA_KBR"));
		korisnik.setBrTelefona(rs.getString("BR_TELEFONA"));
		
		SmjestajnaJedinica smjJed = new SmjestajnaJedinica();
		smjJed.setIdSmjestJed(rs.getInt("ID_SMJEST_JED"));
		smjJed.setNazSmjestJed(rs.getString("NAZ_SMJEST_JED"));
		smjJed.setKat(rs.getInt("KAT"));
		
		TipSmjestJed tipSmjestJed = new TipSmjestJed();
		tipSmjestJed.setIdTipSmjestJed(rs.getInt("ID_TIP_SMJEST_JED"));
		tipSmjestJed.setTipSmjestJed(rs.getString("TIP_SMJEST_JED"));
		tipSmjestJed.setPogled(rs.getString("POGLED"));
		tipSmjestJed.setKapacitetOd(rs.getInt("KAPACITET_OD"));
		tipSmjestJed.setKapacitetDo(rs.getInt("KAPACITET_DO"));
		
		Objekt objekt = new Objekt();
		objekt.setIdObjekta(rs.getInt("ID_OBJEKTA"));
		objekt.setBrKatova(rs.getInt("BR_KATOVA"));
		objekt.setNazObjekta(rs.getString("NAZ_OBJEKTA"));
		
		smjJed.setTipSmjestJed(tipSmjestJed);
		smjJed.setObjekt(objekt);
		
		rezervacija.setSmjJed(smjJed);
		rezervacija.setKorisnik(korisnik);
		
		return rezervacija;
	}
	

}
