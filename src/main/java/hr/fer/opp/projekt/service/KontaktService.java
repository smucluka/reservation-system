package hr.fer.opp.projekt.service;

public interface KontaktService {
	public int sendEmail(String email, String text, String ime, String prezime);
}
