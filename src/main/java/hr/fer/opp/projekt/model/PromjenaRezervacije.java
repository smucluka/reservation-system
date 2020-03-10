package hr.fer.opp.projekt.model;

import java.util.List;

public class PromjenaRezervacije {
	private int trenActive;
	private int korisnikID;
	private int rezervacijaId;
	private int tipSmjestJedId;;
	private String datumOd;
	private String datumDo;
	private int brOdraslih;
	private int brDj01;
	private int brDj27;
	private int brDj814;
	private List<Integer> dodUsluge;
	
	
	
	public int getTrenActive() {
		return trenActive;
	}
	public void setTrenActive(int trenActive) {
		this.trenActive = trenActive;
	}
	public int getKorisnikID() {
		return korisnikID;
	}
	public void setKorisnikID(int korisnikID) {
		this.korisnikID = korisnikID;
	}
	public int getRezervacijaId() {
		return rezervacijaId;
	}
	public void setRezervacijaId(int rezervacijaId) {
		this.rezervacijaId = rezervacijaId;
	}
	public int getTipSmjestJedId() {
		return tipSmjestJedId;
	}
	public void setTipSmjestJedId(int tipSmjestJedId) {
		this.tipSmjestJedId = tipSmjestJedId;
	}
	public String getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(String datumOd) {
		this.datumOd = datumOd;
	}
	public String getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(String datumDo) {
		this.datumDo = datumDo;
	}
	public List<Integer> getDodUsluge() {
		return dodUsluge;
	}
	public void setDodUsluge(List<Integer> dodUsluge) {
		this.dodUsluge = dodUsluge;
	}
	public int getBrOdraslih() {
		return brOdraslih;
	}
	public void setBrOdraslih(int brOdraslih) {
		this.brOdraslih = brOdraslih;
	}
	public int getBrDj01() {
		return brDj01;
	}
	public void setBrDj01(int brDj01) {
		this.brDj01 = brDj01;
	}
	public int getBrDj27() {
		return brDj27;
	}
	public void setBrDj27(int brDj27) {
		this.brDj27 = brDj27;
	}
	public int getBrDj814() {
		return brDj814;
	}
	public void setBrDj814(int brDj814) {
		this.brDj814 = brDj814;
	}
	
	
}
