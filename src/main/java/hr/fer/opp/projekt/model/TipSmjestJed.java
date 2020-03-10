package hr.fer.opp.projekt.model;

import java.util.ArrayList;
import java.util.List;

public class TipSmjestJed {
	
	private Integer idTipSmjestJed;
	private String tipSmjestJed;
	private String pogled;
	private Integer kapacitetOd;
	private Integer kapacitetDo;
	private List<String> slika = new ArrayList<>();
	
	public TipSmjestJed() {
		super();
	}
	public TipSmjestJed(Integer idTipSmjestJed, String tipSmjestJed, String pogled, Integer kapacitetOd,
			Integer kapacitetDo) {
		super();
		this.idTipSmjestJed = idTipSmjestJed;
		this.tipSmjestJed = tipSmjestJed;
		this.pogled = pogled;
		this.kapacitetOd = kapacitetOd;
		this.kapacitetDo = kapacitetDo;
	}
	
	public void add(String str) {
		this.slika.add(str);
	}
	
	public Integer getIdTipSmjestJed() {
		return idTipSmjestJed;
	}
	public void setIdTipSmjestJed(Integer idTipSmjestJed) {
		this.idTipSmjestJed = idTipSmjestJed;
	}
	public String getTipSmjestJed() {
		return tipSmjestJed;
	}
	public void setTipSmjestJed(String tipSmjestJed) {
		this.tipSmjestJed = tipSmjestJed;
	}
	public String getPogled() {
		return pogled;
	}
	public void setPogled(String pogled) {
		this.pogled = pogled;
	}
	public Integer getKapacitetOd() {
		return kapacitetOd;
	}
	public void setKapacitetOd(Integer kapacitetOd) {
		this.kapacitetOd = kapacitetOd;
	}
	public Integer getKapacitetDo() {
		return kapacitetDo;
	}
	public void setKapacitetDo(Integer kapacitetDo) {
		this.kapacitetDo = kapacitetDo;
	}
	public List<String> getSlika() {
		return slika;
	}
	public void setSlika(List<String> slika) {
		this.slika = slika;
	}
		
}
