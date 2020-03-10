package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.model.AktivacijskiToken;

public interface AktivacijaService {
	
	public String generirajToken(String email);
	public AktivacijskiToken getAktivacijskiToken(String token);
}
