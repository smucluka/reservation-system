package hr.fer.opp.projekt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hr.fer.opp.projekt.model.TipSmjestJed;

public interface TipSmjestJedService {
	
	public void dodajTipSmjestJed(TipSmjestJed tipSmjestJed, MultipartFile[] files);
	public List<TipSmjestJed> getTipSmjestJedList();
	public void izbrisiTip(Integer idTipSmjestJed);

}
