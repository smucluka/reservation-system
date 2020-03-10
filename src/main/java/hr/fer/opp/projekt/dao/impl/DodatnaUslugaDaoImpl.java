package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.DodatnaUslugaDao;
import hr.fer.opp.projekt.model.DodatnaUsluga;

@Repository
public class DodatnaUslugaDaoImpl implements DodatnaUslugaDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<DodatnaUsluga> getUslugeList() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_USLUGE, NAZ_USLUGE ");
		tSqlBuilder.append(" FROM dodatna_usluga ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(DodatnaUsluga.class));
	}

	@Override
	public void dodajNovuUslugu(DodatnaUsluga usluga) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO dodatna_usluga ");
		tSqlBuilder.append(" (NAZ_USLUGE) ");
		tSqlBuilder.append(" VALUES ");
		tSqlBuilder.append(" (?) ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {usluga.getNazUsluge()});	
	}

	@Override
	public void izbrisiUslugu(Integer idUsluge) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" DELETE FROM dodatna_usluga ");
		tSqlBuilder.append(" WHERE ID_USLUGE = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {idUsluge});	
	}
	
	@Override
	public List<DodatnaUsluga> getUsluguById(Integer idRezervacijeDodUsl) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ID_USLUGE, NAZ_USLUGE ");
		tSqlBuilder.append(" FROM rezervacija_dod_usl AS rd JOIN dodatna_usluga AS d ON rd.ID_DODATNE_USLUGE = d.ID_USLUGE ");
		tSqlBuilder.append(" WHERE rd.ID_REZERVACIJA_DOD_USL = ?; ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(DodatnaUsluga.class));
			
	}

}
