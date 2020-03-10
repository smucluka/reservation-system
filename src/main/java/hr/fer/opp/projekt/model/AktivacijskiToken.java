package hr.fer.opp.projekt.model;


import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class AktivacijskiToken {
	
	private static final int TRAJANJE = 15;
	
	private Integer idTokena;
	private String token;
	private Integer idKorisnika;
	private Timestamp tstampIsteka;
	
	public AktivacijskiToken() {
		super();
	}
	
	public AktivacijskiToken(String token, Integer idKorisnika) {
		this.token = token;
		this.idKorisnika = idKorisnika;
		this.tstampIsteka = calculateTstampIsteka(TRAJANJE);
	}
	
	private static Timestamp calculateTstampIsteka(int trajanje) {
		Timestamp tstamp = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(trajanje));
		return tstamp;
	}
	
	public Integer getIdTokena() {
		return idTokena;
	}
	public void setIdTokena(Integer idTokena) {
		this.idTokena = idTokena;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getIdKorisnika() {
		return idKorisnika;
	}
	public void setIdKorisnika(Integer idKorisnika) {
		this.idKorisnika = idKorisnika;
	}
	public Timestamp getTstampIsteka() {
		return tstampIsteka;
	}
	public void setTstampIsteka(Timestamp tstampIsteka) {
		this.tstampIsteka = tstampIsteka;
	}
	
	

}
