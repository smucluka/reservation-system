package hr.fer.opp.projekt.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	private Integer idRole;
	private String nazRole;
	
	public Integer getIdRole() {
		return idRole;
	}
	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
	public String getNazRole() {
		return nazRole;
	}
	public void setNazRole(String nazRole) {
		this.nazRole = nazRole;
	}
	@Override
	public String getAuthority() {
		return nazRole;
	}

}
