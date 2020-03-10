package hr.fer.opp.projekt.model;

import java.util.ArrayList;
import java.util.List;

public class Rezervacija {
	
	private int rezervacijaID;
	private SmjestajnaJedinica smjJed;
	private Korisnik korisnik;
	private String brOdraslih;
	private String brDjece0_1;
	private String brDjece2_7;
	private String brDjece8_14;
	private String datOd;
	private String datDo;
	private int status;
	private List<Integer> dodUsluge = new ArrayList<>();
	public List<String> dodUslugeOpis = new ArrayList<>();
	private String datRez;
	
	public void add(Integer dodUslugeId, String dodUslugeOpis) {
		this.dodUsluge.add(dodUslugeId);
		this.dodUslugeOpis.add(dodUslugeOpis);
	}
	
	
	public int getRezervacijaID() {
		return rezervacijaID;
	}
	public void setRezervacijaID(int rezervacijaID) {
		this.rezervacijaID = rezervacijaID;
	}
	public SmjestajnaJedinica getSmjJed() {
		return smjJed;
	}
	public void setSmjJed(SmjestajnaJedinica smjJed) {
		this.smjJed = smjJed;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public String getBrOdraslih() {
		return brOdraslih;
	}
	public void setBrOdraslih(String brOdraslih) {
		this.brOdraslih = brOdraslih;
	}
	public String getBrDjece0_1() {
		return brDjece0_1;
	}
	public void setBrDjece0_1(String brDjece0_1) {
		this.brDjece0_1 = brDjece0_1;
	}
	public String getBrDjece2_7() {
		return brDjece2_7;
	}
	public void setBrDjece2_7(String brDjece2_7) {
		this.brDjece2_7 = brDjece2_7;
	}
	public String getBrDjece8_14() {
		return brDjece8_14;
	}
	public void setBrDjece8_14(String brDjece8_14) {
		this.brDjece8_14 = brDjece8_14;
	}
	public String getDatOd() {
		return datOd;
	}
	public void setDatOd(String datOd) {
		this.datOd = datOd;
	}
	public String getDatDo() {
		return datDo;
	}
	public void setDatDo(String datDo) {
		this.datDo = datDo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Integer> getDodUsluge() {
		return dodUsluge;
	}
	public void setDodUsluge(List<Integer> dodUsluge) {
		this.dodUsluge = dodUsluge;
	}

	public List<String> getDodUslugeOpis() {
		return dodUslugeOpis;
	}

	public void setDodUslugeOpis(List<String> dodUslugeOpis) {
		this.dodUslugeOpis = dodUslugeOpis;
	}

	public String getDatRez() {
		return datRez;
	}

	public void setDatRez(String datRez) {
		this.datRez = datRez;
	}
	
	
}
