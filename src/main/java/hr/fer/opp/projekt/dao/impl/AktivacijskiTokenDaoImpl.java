package hr.fer.opp.projekt.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.AktivacijskiTokenDao;
import hr.fer.opp.projekt.model.AktivacijskiToken;

@Repository
public class AktivacijskiTokenDaoImpl implements AktivacijskiTokenDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertToken(AktivacijskiToken aktivacijskiToken) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO aktivacijski_token ");
		tSqlBuilder.append(" (TOKEN, ID_KORISNIKA, TSTAMP_ISTEKA) ");
		tSqlBuilder.append(" VALUES ");
		tSqlBuilder.append(" (?, ?, ?) ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {aktivacijskiToken.getToken(), aktivacijskiToken.getIdKorisnika(), aktivacijskiToken.getTstampIsteka()});	
	}

	@Override
	public AktivacijskiToken getAktivacijskiToken(String token) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_TOKENA, TOKEN, ID_KORISNIKA, TSTAMP_ISTEKA ");
		tSqlBuilder.append(" FROM aktivacijski_token ");
		tSqlBuilder.append(" WHERE TOKEN = ? ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.queryForObject(tSql, new Object[] {token}, new BeanPropertyRowMapper<>(AktivacijskiToken.class));
	}

}
