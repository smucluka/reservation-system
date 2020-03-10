package hr.fer.opp.projekt.model;

import java.util.List;

public class PregledRezervacija {
	
	private int tipID;
	private int korisnikID;
	private String brOdraslih;
	private String brDjece;
	private String datumOd;
	private String datumDo;
	private String brDjece0_1;
	private String brDjece2_7;
	private String brDjece8_14;
	
	private List<Integer> dodUsluge;

	public int getTipID() {
		return tipID;
	}
	public void setTipID(int tipID) {
		this.tipID = tipID;
	}
	public String getBrOdraslih() {
		return brOdraslih;
	}
	public void setBrOdraslih(String brOdraslih) {
		this.brOdraslih = brOdraslih;
	}
	public String getBrDjece() {
		return brDjece;
	}
	public void setBrDjece(String brDjece) {
		this.brDjece = brDjece;
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
	public int getKorisnikID() {
		return korisnikID;
	}
	public void setKorisnikID(int korisnikID) {
		this.korisnikID = korisnikID;
	}
	public List<Integer> getDodUsluge() {
		return dodUsluge;
	}
	public void setDodUsluge(List<Integer> dodUsluge) {
		this.dodUsluge = dodUsluge;
	}
	
	
	
	
}
