package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.constants.Constants;
import hr.fer.opp.projekt.dao.AdminDao;
import hr.fer.opp.projekt.model.Korisnik;

@Repository
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Korisnik>getAdminList() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_KORISNIKA, IME, PREZIME, ULICA_KBR, GRAD, DRZAVA, BR_TELEFONA, EMAIL, ID_ROLE, PASSWORD, ENABLED ");
		tSqlBuilder.append(" FROM korisnik ");
		tSqlBuilder.append(" WHERE ID_ROLE = ? ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new Object[] {Constants.ROLA_ADMIN}, new BeanPropertyRowMapper<>(Korisnik.class));
	}

}
