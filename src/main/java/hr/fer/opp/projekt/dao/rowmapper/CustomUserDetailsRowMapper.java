package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.CustomUserDetails;
import hr.fer.opp.projekt.model.Role;

public class CustomUserDetailsRowMapper implements RowMapper<CustomUserDetails> {

	@Override
	public CustomUserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setUsername(rs.getString("EMAIL"));
		customUserDetails.setPassword(rs.getString("PASSWORD"));
		customUserDetails.setFirstName(rs.getString("IME"));
		customUserDetails.setLastName(rs.getString("PREZIME"));
		List<Role> role = new ArrayList<>();
		Role rola = new Role();
		rola.setIdRole(rs.getInt("ID_ROLE"));
		rola.setNazRole(rs.getString("NAZ_ROLE"));
		role.add(rola);
		customUserDetails.setAuthorities(role);
		return customUserDetails;
	}

}
