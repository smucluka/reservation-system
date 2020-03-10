package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.model.CustomUserDetails;

public interface CustomUserDetailsDao {
	
	public CustomUserDetails loadUserByUsername(String username);

}
