package hr.fer.opp.projekt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import hr.fer.opp.projekt.dao.TipSmjestJedDao;
import hr.fer.opp.projekt.dao.rowmapper.TipResultSetExtractor;
import hr.fer.opp.projekt.model.TipSmjestJed;

@Repository
public class TipSmjestJedDaoImpl implements TipSmjestJedDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer dodajTipSmjestJed(TipSmjestJed tipSmjestJed) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO tip_smjest_jed ");
		tSqlBuilder.append(" (TIP_SMJEST_JED, POGLED, KAPACITET_OD, KAPACITET_DO) ");
		tSqlBuilder.append(" VALUES (?, ?, ?, ?) ");
		String tSql = tSqlBuilder.toString();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
						
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(tSql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, tipSmjestJed.getTipSmjestJed());
				preparedStatement.setString(2, tipSmjestJed.getPogled());
				preparedStatement.setInt(3, tipSmjestJed.getKapacitetOd());
				preparedStatement.setInt(4, tipSmjestJed.getKapacitetDo());
				return preparedStatement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<TipSmjestJed> getTipSmjestJedList() {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" tip_smjest_jed.ID_TIP_SMJEST_JED, TIP_SMJEST_JED, POGLED, KAPACITET_OD, KAPACITET_DO, SLIKA ");
		tSqlBuilder.append(" FROM tip_smjest_jed LEFT JOIN slika ON slika.ID_TIP_SMJEST_JED = tip_smjest_jed.ID_TIP_SMJEST_JED ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new TipResultSetExtractor());
	}

	@Override
	public void izbrisiTip(Integer idTipSmjestJed) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" DELETE FROM tip_smjest_jed ");
		tSqlBuilder.append(" WHERE ID_TIP_SMJEST_JED = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {idTipSmjestJed});		
	}

}
