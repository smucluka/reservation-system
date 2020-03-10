package hr.fer.opp.projekt.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import hr.fer.opp.projekt.dao.CustomUserDetailsDao;
import hr.fer.opp.projekt.model.CustomUserDetails;
import hr.fer.opp.projekt.service.impl.CustomUserDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	CustomUserDetailsDao userDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();
		
		//CustomUserDetails customUser = customUserDetailsService.loadUserByUsername(username);
		CustomUserDetails customUser = userDao.loadUserByUsername(username);
		
		if(customUser == null || !customUser.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Ne postoji korisnik s unesenim emailom.");
		}
		
		if(!password.equals(customUser.getPassword())) {
			throw new BadCredentialsException("Lozinka nije točna.");
		}
		
		Collection<? extends GrantedAuthority> authorities = customUser.getAuthorities();
		
		return new UsernamePasswordAuthenticationToken(customUser, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
