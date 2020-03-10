package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.Objekt;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;

public class SmjestJedRowMapper implements RowMapper<SmjestajnaJedinica> {

	@Override
	public SmjestajnaJedinica mapRow(ResultSet rs, int rowNum) throws SQLException {
		SmjestajnaJedinica smjestJed = new SmjestajnaJedinica();
		smjestJed.setIdSmjestJed(rs.getInt("ID_SMJEST_JED"));
		smjestJed.setNazSmjestJed(rs.getString("NAZ_SMJEST_JED"));
		Objekt objekt = new Objekt();
		objekt.setIdObjekta(rs.getInt("ID_OBJ"));
		objekt.setNazObjekta(rs.getString("NAZ_OBJEKTA"));
		objekt.setBrKatova(rs.getInt("BR_KATOVA"));
		smjestJed.setObjekt(objekt);
		TipSmjestJed tipSmjestJed = new TipSmjestJed();
		tipSmjestJed.setIdTipSmjestJed(rs.getInt("ID_TIP"));
		tipSmjestJed.setTipSmjestJed(rs.getString("TIP_SMJEST_JED"));
		tipSmjestJed.setPogled(rs.getString("POGLED"));
		tipSmjestJed.setKapacitetOd(rs.getInt("KAPACITET_OD"));
		tipSmjestJed.setKapacitetDo(rs.getInt("KAPACITET_DO"));
		tipSmjestJed.setSlika(new ArrayList<>());
		smjestJed.setTipSmjestJed(tipSmjestJed);
		smjestJed.setKat(rs.getInt("KAT"));
		return smjestJed;
	}

}
