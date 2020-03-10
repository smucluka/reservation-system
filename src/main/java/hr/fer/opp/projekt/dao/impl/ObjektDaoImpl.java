package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.ObjektDao;
import hr.fer.opp.projekt.model.Objekt;

@Repository
public class ObjektDaoImpl implements ObjektDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Objekt> getObjektList() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_OBJEKTA, NAZ_OBJEKTA ");
		tSqlBuilder.append(" FROM objekt ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(Objekt.class));
	}

	@Override
	public void dodajNoviObjekt(Objekt objekt) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO objekt ");
		tSqlBuilder.append(" (NAZ_OBJEKTA) ");
		tSqlBuilder.append(" VALUES ");
		tSqlBuilder.append(" (?) ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {objekt.getNazObjekta()});	
	}

	@Override
	public void izbrisiObjekt(Integer idObjekta) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" DELETE FROM objekt ");
		tSqlBuilder.append(" WHERE ID_OBJEKTA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {idObjekta});		
	}

}
