package hr.fer.opp.projekt.model;

public class PregledSmjestajnaJedinica {
	
	private Integer idSmjestJed;
	private String nazSmjestJed;
	private Objekt objekt;
	private TipSmjestJed tipSmjestJed;
	private Integer kat;
	private String datOd;
	private String datDo;
	private String status;
	private Integer rezervacijaId;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRezervacijaId() {
		return rezervacijaId;
	}
	public void setRezervacijaId(Integer rezervacijaId) {
		this.rezervacijaId = rezervacijaId;
	}
	
	
}
