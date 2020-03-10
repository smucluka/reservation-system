package hr.fer.opp.projekt.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.Korisnik;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private KorisnikDao korisnikDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikDao.findByEmail(email);
		if(korisnik == null) {
			throw new UsernameNotFoundException("Nije pronađen korisnik s emailom: " + email);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
//		return new org.springframework.security.core.userdetails.User(korisnik.getEmail(), korisnik.getPassword().toLowerCase(), korisnik.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(korisnik.getRoles()));
		return null;
	}
	
	private static List<GrantedAuthority> getAuthorities(List<String> roles){
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
