package hr.fer.opp.projekt.dao;

import java.util.List;

import hr.fer.opp.projekt.model.Korisnik;

public interface KorisnikDao {
	
	public Korisnik insertKorisnik(Korisnik korisnik);
	public Korisnik findByEmail(String email);
	public void updateKorisnik(Integer idKorisnika, Integer status);
	public void azurirajKorisnika(Korisnik korisnik); //moj profil uređivanje
	public Korisnik getEmailById(int korisnikID);
	void azurirajLozinku(Korisnik korisnik);
	public void azurirajLozinku(String novaLozinka, String email);
	void updateKorisnikRola(Integer idKorisnika, Integer idRole);
	List<String> getAdminEmail();

	
}
