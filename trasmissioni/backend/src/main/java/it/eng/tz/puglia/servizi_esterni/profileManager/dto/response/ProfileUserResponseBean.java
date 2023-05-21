package it.eng.tz.puglia.servizi_esterni.profileManager.dto.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileUserResponseBean implements Serializable {

	private static final long serialVersionUID = -3652228505765657255L;
	
	private int id;
	private String username;
	private String nome;
	private String cognome;
	private String email;
	private String pec;
	private String codIstat;
	private List<RoleResponseBean> roles;
	private Map<String, List<EnteResponseBean>> enti = new HashMap<>();

	public Map<String, List<EnteResponseBean>> getEnti() {
		return enti;
	}

	public void setEnti(Map<String, List<EnteResponseBean>> enti) {
		this.enti = enti;
	}

	public List<RoleResponseBean> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleResponseBean> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof ProfileUserResponseBean == false)
			return false;
		ProfileUserResponseBean other = (ProfileUserResponseBean) obj;
		return (this.username == null && other.username == null)
				|| (this.username != null && other.username != null && this.username.equals(other.username));
	}

	@Override
	public String toString() {
		return "ProfileUserResponseBean [username=" + username + "]";
	}

}
