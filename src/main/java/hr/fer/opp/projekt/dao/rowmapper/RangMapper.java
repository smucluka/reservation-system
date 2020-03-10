package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.Rang;

public class RangMapper implements RowMapper<Rang> {
	private String value;
	private String count;
	
	public RangMapper(String value, String count) {
		this.value = value;
		this.count = count;
	}
	
	@Override
	public Rang mapRow(ResultSet rs, int rowNum) throws SQLException {
		Rang rang = new Rang();

		rang.setName(rs.getString(value));
		rang.setValue(rs.getInt(count));
		return rang;
	}
}
