package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.form.SmjestJedForm;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;

public interface SmjestJedDao {
	
	public void dodajSmjestJed(SmjestJedForm smjestJedForm);
	public List<SmjestajnaJedinica> getSmjestJedList();
	public void izbrisiSmjestJed(Integer idSmjestJed);

}
