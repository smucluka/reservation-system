package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.StatistikaDao;
import hr.fer.opp.projekt.dao.rowmapper.RangMapper;
import hr.fer.opp.projekt.model.Rang;


@Repository
public class StatistikaDaoImpl implements StatistikaDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Rang> getRangDrzava() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append("SELECT DRZAVA, count(drzava) ");
		tSqlBuilder.append("FROM korisnik NATURAL JOIN rezervacija ");
		tSqlBuilder.append("WHERE STATUS = 1 ");
		tSqlBuilder.append("GROUP BY DRZAVA;");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RangMapper("DRZAVA", "count(drzava)"));
	}

	@Override
	public List<Rang> getRangGrad() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append("SELECT GRAD, count(grad) ");
		tSqlBuilder.append("FROM korisnik NATURAL JOIN rezervacija ");
		tSqlBuilder.append("WHERE STATUS = 1 ");
		tSqlBuilder.append("GROUP BY GRAD;");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RangMapper("GRAD", "count(grad)"));
	}

	@Override
	public List<Rang> getRangUsluge() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append("SELECT NAZ_USLUGE, count(ID_DODATNE_USLUGE) ");
		tSqlBuilder.append("FROM korisnik NATURAL JOIN rezervacija LEFT JOIN rezervacija_dod_usl ON rezervacija.ID_REZERVACIJA = rezervacija_dod_usl.ID_REZERVACIJA_DOD_USL ");
		tSqlBuilder.append("LEFT JOIN dodatna_usluga ON dodatna_usluga.ID_USLUGE = rezervacija_dod_usl.ID_DODATNE_USLUGE ");
		tSqlBuilder.append("WHERE STATUS = 1 ");
		tSqlBuilder.append("GROUP BY ID_DODATNE_USLUGE; ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RangMapper("NAZ_USLUGE", "count(ID_DODATNE_USLUGE)"));
	}

	@Override
	public List<Rang> getRangUslugeDrzava() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT DRZAVA as name, NAZ_USLUGE as name2, count(ID_DODATNE_USLUGE) as value ");
		tSqlBuilder.append(" FROM korisnik ");
		tSqlBuilder.append(" NATURAL JOIN rezervacija LEFT JOIN rezervacija_dod_usl ON rezervacija.ID_REZERVACIJA = rezervacija_dod_usl.ID_REZERVACIJA_DOD_USL ");
		tSqlBuilder.append(" LEFT JOIN dodatna_usluga ON dodatna_usluga.ID_USLUGE = rezervacija_dod_usl.ID_DODATNE_USLUGE ");
		tSqlBuilder.append(" WHERE STATUS = 1 ");
		tSqlBuilder.append(" GROUP BY ID_DODATNE_USLUGE, DRZAVA ");
		tSqlBuilder.append(" ORDER BY value DESC ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(Rang.class));
	}

	@Override
	public List<Rang> getRangUslugeBrGostiju() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT  (BR_ODRASLIH + BR_DJECE_0_1 + BR_DJECE_2_7 + BR_DJECE_8_14) as value, NAZ_USLUGE as name, count(dodatna_usluga.ID_USLUGE) as value2 ");
		tSqlBuilder.append(" FROM korisnik ");
		tSqlBuilder.append(" NATURAL JOIN rezervacija LEFT JOIN rezervacija_dod_usl ON rezervacija.ID_REZERVACIJA = rezervacija_dod_usl.ID_REZERVACIJA_DOD_USL ");
		tSqlBuilder.append(" LEFT JOIN dodatna_usluga ON dodatna_usluga.ID_USLUGE = rezervacija_dod_usl.ID_DODATNE_USLUGE ");
		tSqlBuilder.append(" WHERE STATUS = 1 ");
		tSqlBuilder.append(" GROUP BY (BR_ODRASLIH + BR_DJECE_0_1 + BR_DJECE_2_7 + BR_DJECE_8_14), ID_DODATNE_USLUGE ");
		tSqlBuilder.append(" ORDER BY value, value2 DESC ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new BeanPropertyRowMapper<>(Rang.class));
	}

}
