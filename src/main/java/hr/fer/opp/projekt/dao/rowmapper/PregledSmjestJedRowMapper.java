package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import hr.fer.opp.projekt.model.Objekt;
import hr.fer.opp.projekt.model.PregledSmjestajnaJedinica;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;

public class PregledSmjestJedRowMapper  implements RowMapper<PregledSmjestajnaJedinica> {
	
	@Override
	public PregledSmjestajnaJedinica mapRow(ResultSet rs, int rowNum) throws SQLException {
		PregledSmjestajnaJedinica smjestJed = new PregledSmjestajnaJedinica();
		smjestJed.setIdSmjestJed(rs.getInt("ID_SMJEST_JED"));
		smjestJed.setNazSmjestJed(rs.getString("NAZ_SMJEST_JED"));
		smjestJed.setStatus(rs.getString("STATUS"));
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
		smjestJed.setDatOd(rs.getString("DAT_OD"));
		smjestJed.setDatDo(rs.getString("DAT_DO"));
		smjestJed.setRezervacijaId(rs.getInt("ID_REZERVACIJA"));
		return smjestJed;
	}
}
