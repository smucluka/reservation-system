package hr.fer.opp.projekt.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import hr.fer.opp.projekt.dao.KorisnikDao;
import hr.fer.opp.projekt.model.Korisnik;

@Repository
public class KorisnikDaoImpl implements KorisnikDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Korisnik insertKorisnik(Korisnik korisnik) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" INSERT INTO korisnik ");
		tSqlBuilder.append(" (IME, PREZIME, ULICA_KBR, GRAD, DRZAVA, BR_TELEFONA, EMAIL, ID_ROLE, PASSWORD, ENABLED) ");
		tSqlBuilder.append(" VALUES ");
		tSqlBuilder.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {korisnik.getIme(), korisnik.getPrezime(), korisnik.getUlicaKbr(), korisnik.getGrad(), korisnik.getDrzava(), korisnik.getBrTelefona(), korisnik.getEmail(), korisnik.getIdRole(), korisnik.getPassword(), korisnik.isEnabled()});
		return korisnik;
	}

	@Override
	public Korisnik findByEmail(String email) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" SELECT ");
		tSqlBuilder.append(" ID_KORISNIKA, IME, PREZIME, ULICA_KBR, GRAD, DRZAVA, BR_TELEFONA, EMAIL, ID_ROLE, PASSWORD, ENABLED ");
		tSqlBuilder.append(" FROM korisnik ");
		tSqlBuilder.append(" WHERE EMAIL = ? ");
		String tSql = tSqlBuilder.toString();
		return jdbcTemplate.queryForObject(tSql, new Object[] {email}, new BeanPropertyRowMapper<>(Korisnik.class));
	}

	@Override
	public void updateKorisnik(Integer idKorisnika, Integer status) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE korisnik ");
		tSqlBuilder.append(" SET ENABLED = ? ");
		tSqlBuilder.append(" WHERE ID_KORISNIKA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {status, idKorisnika});
	}
	
	@Override
	public void updateKorisnikRola(Integer idKorisnika, Integer idRole) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE korisnik ");
		tSqlBuilder.append(" SET ID_ROLE = ? ");
		tSqlBuilder.append(" WHERE ID_KORISNIKA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {idRole, idKorisnika});
	}

	@Override
	public void azurirajKorisnika(Korisnik korisnik) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE korisnik ");
		tSqlBuilder.append(" SET IME = ?, PREZIME = ?, ULICA_KBR = ?, GRAD = ?, DRZAVA = ?, BR_TELEFONA = ?");
		tSqlBuilder.append(" WHERE ID_KORISNIKA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {korisnik.getIme(), korisnik.getPrezime(), korisnik.getUlicaKbr(), korisnik.getGrad(), 
				korisnik.getDrzava(), korisnik.getBrTelefona(), korisnik.getIdKorisnika()}); 
		
		return;
	}
	
	@Override
	public void azurirajLozinku(Korisnik korisnik) {
		
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE korisnik ");
		tSqlBuilder.append(" SET PASSWORd = ?");
		tSqlBuilder.append(" WHERE ID_KORISNIKA = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {korisnik.getPassword(), korisnik.getIdKorisnika()}); 
		
		return;
	}

	@Override
	public Korisnik getEmailById(int korisnikID) {
			StringBuilder tSqlBuilder = new StringBuilder();
			tSqlBuilder.append(" SELECT ");
			tSqlBuilder.append(" IME, PREZIME, EMAIL, ULICA_KBR, GRAD, DRZAVA, BR_TELEFONA, EMAIL, ID_ROLE, PASSWORD, ENABLED ");
			tSqlBuilder.append(" FROM korisnik ");
			tSqlBuilder.append(" WHERE ID_KORISNIKA = ? ");
			String tSql = tSqlBuilder.toString();
			return jdbcTemplate.queryForObject(tSql, new Object[] {korisnikID}, new BeanPropertyRowMapper<>(Korisnik.class));
	}
	
	@Override
	public List<String> getAdminEmail() {
			StringBuilder tSqlBuilder = new StringBuilder();
			tSqlBuilder.append(" SELECT EMAIl");
			tSqlBuilder.append(" FROM korisnik ");
			tSqlBuilder.append(" WHERE ID_ROLE =2; ");
			String tSql = tSqlBuilder.toString();
			return jdbcTemplate.queryForList(tSql, String.class);
	}

	@Override
	public void azurirajLozinku(String novaLozinka, String email) {
		StringBuilder tSqlBuilder = new StringBuilder();
		tSqlBuilder.append(" UPDATE korisnik ");
		tSqlBuilder.append(" SET PASSWORD = ?");
		tSqlBuilder.append(" WHERE EMAIL = ? ");
		String tSql = tSqlBuilder.toString();
		jdbcTemplate.update(tSql, new Object[] {novaLozinka, email}); 		
	}

}
