package hr.fer.opp.projekt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.SlikeDao;

@Repository
public class SlikeDaoImpl implements SlikeDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertBatch(Integer idTipSmjestJed, List<String> slika) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO slika ");
		tSqlBuilder.append(" (ID_TIP_SMJEST_JED, SLIKA) ");
		tSqlBuilder.append(" VALUES (?, ?) ");
		String tSql = tSqlBuilder.toString();
		
		List<Object[]> parameters = new ArrayList<Object[]>();
		for(String str : slika) {
			parameters.add(new Object[] {idTipSmjestJed, str});
		}
		
		jdbcTemplate.batchUpdate(tSql, parameters);
	}

}
