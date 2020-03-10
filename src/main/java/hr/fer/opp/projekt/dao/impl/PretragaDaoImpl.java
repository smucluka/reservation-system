package hr.fer.opp.projekt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.PretragaDao;
import hr.fer.opp.projekt.dao.rowmapper.PregledSmjestJedRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.SlikaRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.SmjestJedRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.TipResultSetExtractor;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.Pretraga;
import hr.fer.opp.projekt.model.Slika;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;

@Repository
public class PretragaDaoImpl implements PretragaDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void getSmjestajneJedinice() {

		// TODO vrati listu smjestajnih jedinica
	}

	@Override
	public List<PregledSmjestajnaJedinica> getSmjestJedByTip(Integer tipId) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT");
		tSqlBuilder.append(" r.STATUS, r.ID_REZERVACIJA, s.ID_SMJEST_JED, s.NAZ_SMJEST_JED, s.kat, o.ID_OBJEKTA AS ID_OBJ, o.NAZ_OBJEKTA, o.BR_KATOVA,  t.ID_TIP_SMJEST_JED AS ID_TIP, t.TIP_SMJEST_JED, t.POGLED, t.KAPACITET_OD, t.KAPACITET_DO, r.DAT_OD, r.DAT_DO");
		tSqlBuilder.append(" FROM smjest_jed AS s  JOIN objekt AS o ON s.ID_OBJEKTA = o.ID_OBJEKTA JOIN tip_smjest_jed AS t ON s.ID_TIP_SMJEST_JED = t.ID_TIP_SMJEST_JED ");
		tSqlBuilder.append(" LEFT JOIN rezervacija AS r ON  s.ID_SMJEST_JED = r.ID_SMJEST_JED  ");
		tSqlBuilder.append(" WHERE t.ID_TIP_SMJEST_JED=" + tipId + ";");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new PregledSmjestJedRowMapper());
		
	}
	
	@Override
	public List<PregledSmjestajnaJedinica> getSmjestJedByKapacitet(String kapacitet) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT");
		tSqlBuilder.append(" r.STATUS, r.ID_REZERVACIJA, s.ID_SMJEST_JED, s.NAZ_SMJEST_JED, s.kat, o.ID_OBJEKTA AS ID_OBJ, o.NAZ_OBJEKTA, o.BR_KATOVA,  t.ID_TIP_SMJEST_JED AS ID_TIP, t.TIP_SMJEST_JED, t.POGLED, t.KAPACITET_OD, t.KAPACITET_DO, r.DAT_OD, r.DAT_DO");
		tSqlBuilder.append(" FROM smjest_jed AS s  JOIN objekt AS o ON s.ID_OBJEKTA = o.ID_OBJEKTA JOIN tip_smjest_jed AS t ON s.ID_TIP_SMJEST_JED = t.ID_TIP_SMJEST_JED ");
		tSqlBuilder.append(" LEFT JOIN rezervacija AS r ON  s.ID_SMJEST_JED = r.ID_SMJEST_JED  ");
		tSqlBuilder.append(" WHERE KAPACITET_OD <= " + kapacitet + " AND KAPACITET_DO >= " + kapacitet + ";");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new PregledSmjestJedRowMapper());
			
	}
	
	@Override
	public List<Slika> getSlike() {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT *");
		tSqlBuilder.append(" FROM slika;");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new SlikaRowMapper());
			
	}

}
