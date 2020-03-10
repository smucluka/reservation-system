package hr.fer.opp.projekt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import hr.fer.opp.projekt.dao.RezervacijaDao;
import hr.fer.opp.projekt.dao.rowmapper.PregledSmjestJedRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.RezervacijaRowExtractor;
import hr.fer.opp.projekt.dao.rowmapper.RezervacijaRowMapper;
import hr.fer.opp.projekt.dao.rowmapper.SmjestJedRowMapper;
import hr.fer.opp.projekt.model.Rezervacija;
import hr.fer.opp.projekt.model.SmjestajnaJedinica;
import hr.fer.opp.projekt.model.TipSmjestJed;

@Repository
public class RezervacijaDaoImpl implements RezervacijaDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insertRezervacija(Rezervacija rezervacija) {

		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO rezervacija ");
		tSqlBuilder.append(" (ID_SMJEST_JED, ID_KORISNIKA, BR_ODRASLIH, BR_DJECE_0_1, BR_DJECE_2_7, BR_DJECE_8_14, STATUS, DAT_OD, DAT_DO, TIMESTAMP) ");
		tSqlBuilder.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		String tSql = tSqlBuilder.toString();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(tSql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, rezervacija.getSmjJed().getIdSmjestJed());
				preparedStatement.setInt(2, rezervacija.getKorisnik().getIdKorisnika());
				preparedStatement.setString(3,rezervacija.getBrOdraslih());
				preparedStatement.setString(4, rezervacija.getBrDjece0_1());
				preparedStatement.setString(5, rezervacija.getBrDjece2_7());
				preparedStatement.setString(6, rezervacija.getBrDjece8_14());
				preparedStatement.setString(7, "0");
				preparedStatement.setString(8, rezervacija.getDatOd());
				preparedStatement.setString(9, rezervacija.getDatDo());
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				date.setHours(date.getHours()+1);
				String currentDate = dateFormat.format(date);
				
				preparedStatement.setString(10, currentDate);
				return preparedStatement;
			}
		}, keyHolder);
		
		Integer idRezervacije = keyHolder.getKey().intValue();

		for (Integer idDodUsl : rezervacija.getDodUsluge()) {
			StringBuilder tSqlBuilder2 = new StringBuilder();
			tSqlBuilder2.append(" INSERT INTO rezervacija_dod_usl ");
			tSqlBuilder2.append(" VALUES ");
			tSqlBuilder2.append(" (?, ?) ");
			String tSql2 = tSqlBuilder2.toString();
			jdbcTemplate.update(tSql2, new Object[] { idRezervacije, idDodUsl });
		}

	}

	@Override
	public List<Rezervacija> getRezervacijeByStatus(int sts) {

		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT *");
		tSqlBuilder.append(" FROM rezervacija AS r LEFT JOIN smjest_jed AS s ON r.ID_SMJEST_JED = s.ID_SMJEST_JED LEFT JOIN korisnik AS k ON k.ID_KORISNIKA=r.ID_KORISNIKA LEFT JOIN tip_smjest_jed AS t ON t.ID_TIP_SMJEST_JED = s.ID_TIP_SMJEST_JED LEFT JOIN objekt AS o ON s.ID_OBJEKTA=o.ID_OBJEKTA ");
		tSqlBuilder.append("		LEFT JOIN rezervacija_dod_usl AS rdu ON rdu.ID_REZERVACIJA_DOD_USL = r.ID_REZERVACIJA LEFT JOIN dodatna_usluga AS du ON du.ID_USLUGE = rdu.ID_DODATNE_USLUGE");
		tSqlBuilder.append(" WHERE r.STATUS = " + sts + "; ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RezervacijaRowExtractor());
	}

	@Override
	public List<Rezervacija> getRezervacijeByKor(int korId) {

		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT *");
		tSqlBuilder.append(" FROM rezervacija AS r LEFT JOIN smjest_jed AS s ON r.ID_SMJEST_JED = s.ID_SMJEST_JED LEFT JOIN korisnik AS k ON k.ID_KORISNIKA=r.ID_KORISNIKA LEFT JOIN tip_smjest_jed AS t ON t.ID_TIP_SMJEST_JED = s.ID_TIP_SMJEST_JED LEFT JOIN objekt AS o ON s.ID_OBJEKTA=o.ID_OBJEKTA");
		tSqlBuilder.append(" WHERE r.ID_KORISNIKA = " + korId +"; ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.query(tSql, new RezervacijaRowMapper());
	}

	@Override
	public void potvrdiOdbijRezervaciju(Integer rezervacijaID, Integer status) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE rezervacija ");
		tSqlBuilder.append(" SET STATUS = ? ");
		tSqlBuilder.append(" WHERE ID_REZERVACIJA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {status, rezervacijaID});
	}

}
