package hr.fer.opp.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.dao.CustomUserDetailsDao;
import hr.fer.opp.projekt.model.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	CustomUserDetailsDao customUserDetailsDao;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return customUserDetailsDao.loadUserByUsername(username);
	}

}
