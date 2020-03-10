package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.OnamaDao;
import hr.fer.opp.projekt.dao.rowmapper.SlikaRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.TipResultSetExtractor;
import hr.fer.opp.projekt.model.Slika;
import hr.fer.opp.projekt.model.TipSmjestJed;

@Repository
public class OnamaDaoImpl implements OnamaDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TipSmjestJed> getTipSmjestajneJedinice() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" tip_smjest_jed.ID_TIP_SMJEST_JED, TIP_SMJEST_JED, POGLED, KAPACITET_OD, KAPACITET_DO, SLIKA ");
		tSqlBuilder.append(" FROM tip_smjest_jed LEFT JOIN slika ON slika.ID_TIP_SMJEST_JED = tip_smjest_jed.ID_TIP_SMJEST_JED ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new TipResultSetExtractor());
		
	}

}
