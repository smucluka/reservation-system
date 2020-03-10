package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.SmjestJedDao;
import hr.fer.opp.projekt.dao.rowmapper.SmjestJedRowMapper;
import hr.fer.opp.projekt.form.SmjestJedForm;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;

@Repository
public class SmjestJedDaoImpl implements SmjestJedDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void dodajSmjestJed(SmjestJedForm smjestJedForm) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO smjest_jed ");
		tSqlBuilder.append(" (NAZ_SMJEST_JED, ID_TIP_SMJEST_JED, ID_OBJEKTA, KAT) ");
		tSqlBuilder.append(" VALUES ");
		tSqlBuilder.append(" (?, ?, ?, ?) ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {smjestJedForm.getNazSmjestJed(), 
				smjestJedForm.getIdTipSmjestJed(),
				smjestJedForm.getIdObjekta(),
				smjestJedForm.getKat()});	
	}

	@Override
	public List<SmjestajnaJedinica> getSmjestJedList() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_SMJEST_JED, NAZ_SMJEST_JED,  ");
		tSqlBuilder.append(" s.ID_OBJEKTA AS ID_OBJ, NAZ_OBJEKTA, BR_KATOVA, ");
		tSqlBuilder.append(" s.ID_TIP_SMJEST_JED AS ID_TIP, TIP_SMJEST_JED, POGLED, ");
		tSqlBuilder.append(" KAPACITET_OD, KAPACITET_DO, KAT ");
		tSqlBuilder.append(" FROM smjest_jed AS s ");
		tSqlBuilder.append(" JOIN objekt AS o ON s.ID_OBJEKTA = o.ID_OBJEKTA ");
		tSqlBuilder.append(" JOIN tip_smjest_jed AS t ON s.ID_TIP_SMJEST_JED = t.ID_TIP_SMJEST_JED ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new SmjestJedRowMapper());
	}

	@Override
	public void izbrisiSmjestJed(Integer idSmjestJed) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" DELETE FROM smjest_jed ");
		tSqlBuilder.append(" WHERE ID_SMJEST_JED = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {idSmjestJed});
	}

}
