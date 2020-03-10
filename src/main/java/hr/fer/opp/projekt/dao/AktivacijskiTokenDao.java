package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.model.AktivacijskiToken;

public interface AktivacijskiTokenDao {
	
	public void insertToken(AktivacijskiToken aktivacijskiToken);
	public AktivacijskiToken getAktivacijskiToken(String token);

}
