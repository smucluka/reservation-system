package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.PromjenaRezervacijeDao;
import hr.fer.opp.projekt.dao.rowmapper.RezervacijaRowExtractor;
import hr.fer.opp.projekt.model.PromjenaRezervacije;
import hr.fer.opp.projekt.model.Rezervacija;

@Repository
public class PromjenaRezervacijeDaoImpl implements PromjenaRezervacijeDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Rezervacija> getRezervacijeById(int id) {

		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT *");
		tSqlBuilder.append(" FROM rezervacija AS r LEFT JOIN smjest_jed AS s ON r.ID_SMJEST_JED = s.ID_SMJEST_JED LEFT JOIN korisnik AS k ON k.ID_KORISNIKA=r.ID_KORISNIKA LEFT JOIN tip_smjest_jed AS t ON t.ID_TIP_SMJEST_JED = s.ID_TIP_SMJEST_JED LEFT JOIN objekt AS o ON s.ID_OBJEKTA=o.ID_OBJEKTA ");
		tSqlBuilder.append("		LEFT JOIN rezervacija_dod_usl AS rdu ON rdu.ID_REZERVACIJA_DOD_USL = r.ID_REZERVACIJA LEFT JOIN dodatna_usluga AS du ON du.ID_USLUGE = rdu.ID_DODATNE_USLUGE");
		tSqlBuilder.append(" WHERE r.ID_REZERVACIJA = " + id + "; ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RezervacijaRowExtractor());
	}

	@Override
	public void napraviPromjenuByRezById(Rezervacija rezervacija) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE rezervacija");
		tSqlBuilder.append(" SET ID_SMJEST_JED = ?, BR_ODRASLIH = ?, BR_DJECE_0_1 = ?, BR_DJECE_2_7 = ?, BR_DJECE_8_14 = ?, DAT_OD = ?, DAT_DO = ?");
		tSqlBuilder.append(" WHERE ID_REZERVACIJA = ? ;");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {rezervacija.getSmjJed().getIdSmjestJed(), rezervacija.getBrOdraslih(), 
				rezervacija.getBrDjece0_1(), rezervacija.getBrDjece2_7(), rezervacija.getBrDjece8_14(), rezervacija.getDatOd(), rezervacija.getDatDo(), rezervacija.getRezervacijaID()});
	}

	@Override
	public void promjeniKapacitetByRezervacijaId(PromjenaRezervacije promjenaRezervacije, int rezervacijaID) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE rezervacija");
		tSqlBuilder.append(" SET BR_ODRASLIH = ?, BR_DJECE_0_1 = ?, BR_DJECE_2_7 = ?, BR_DJECE_8_14 = ?");
		tSqlBuilder.append(" WHERE ID_REZERVACIJA = ? ;");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {promjenaRezervacije.getBrOdraslih(), promjenaRezervacije.getBrDj01(), promjenaRezervacije.getBrDj27(), promjenaRezervacije.getBrDj814(), rezervacijaID});
	}

	@Override
	public void promjeniDodatneUslugeByRezervacijaId(PromjenaRezervacije promjenaRezervacije, int rezervacijaID) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" DELETE FROM rezervacija_dod_usl");
		tSqlBuilder.append(" WHERE ID_REZERVACIJA_DOD_USL = ? ;");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {rezervacijaID});
	
		for (Integer idDodUsl : promjenaRezervacije.getDodUsluge()) {
			StringBuilder tSqlBuilder2 = new StringBuilder();
			tSqlBuilder2.append(" INSERT INTO rezervacija_dod_usl ");
			tSqlBuilder2.append(" VALUES ");
			tSqlBuilder2.append(" (?, ?) ");
			String tSql2 = tSqlBuilder2.toString();
			jdbcTemplate.update(tSql2, new Object[] { rezervacijaID, idDodUsl });
		}
	}
	
	
}
