package hr.fer.opp.projekt.model;

public class SmjestajnaJedinica {
	
	private Integer idSmjestJed;
	private String nazSmjestJed;
	private Objekt objekt;
	private TipSmjestJed tipSmjestJed;
	private Integer kat;
	
	public Integer getIdSmjestJed() {
		return idSmjestJed;
	}
	public void setIdSmjestJed(Integer idSmjestJed) {
		this.idSmjestJed = idSmjestJed;
	}
	public String getNazSmjestJed() {
		return nazSmjestJed;
	}
	public void setNazSmjestJed(String nazSmjestJed) {
		this.nazSmjestJed = nazSmjestJed;
	}
	public Objekt getObjekt() {
		return objekt;
	}
	public void setObjekt(Objekt objekt) {
		this.objekt = objekt;
	}
	public TipSmjestJed getTipSmjestJed() {
		return tipSmjestJed;
	}
	public void setTipSmjestJed(TipSmjestJed tipSmjestJed) {
		this.tipSmjestJed = tipSmjestJed;
	}
	public Integer getKat() {
		return kat;
	}
	public void setKat(Integer kat) {
		this.kat = kat;
	}
		
}
