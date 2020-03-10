package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.Slika;
public class SlikaRowMapper implements RowMapper<Slika> {

	@Override
	public Slika mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Slika slika = new Slika();
		slika.setSlikaTipId(rs.getInt("ID_TIP_SMJEST_JED"));
		slika.setSlika(rs.getString("SLIKA"));
		
		return slika;
	}
}
