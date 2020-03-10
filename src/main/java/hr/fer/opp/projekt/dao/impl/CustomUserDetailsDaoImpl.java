package hr.fer.opp.projekt.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.CustomUserDetailsDao;
import hr.fer.opp.projekt.dao.rowmapper.CustomUserDetailsRowMapper;
import hr.fer.opp.projekt.model.CustomUserDetails;

@Repository
public class CustomUserDetailsDaoImpl implements CustomUserDetailsDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public CustomUserDetails loadUserByUsername(String username) {
		
		String sqlCount = "SELECT count(ID_KORISNIKA) FROM korisnik NATURAL JOIN role WHERE EMAIL = '" + username + "' AND ENABLED = 1;";
		Integer korisnik = jdbcTemplate.queryForObject(sqlCount, Integer.class);
		
		if(korisnik == 0) {
			return null;
		}
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_KORISNIKA, IME, PREZIME, EMAIL, PASSWORD, ID_ROLE, NAZ_ROLE ");
		tSqlBuilder.append(" FROM korisnik NATURAL JOIN role ");
		tSqlBuilder.append(" WHERE EMAIL = ?  AND ENABLED = ?");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.queryForObject(tSql, new Object[] {username, 1}, new CustomUserDetailsRowMapper());
	}

}
