package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCasellaPostale;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

public class ConfigurazioneCasellaPostaleSearch extends WhereClauseGenerator<ConfigurazioneCasellaPostale> {
	
	private static final long serialVersionUID = -1465603400286642044L;
	
	private String email;
	private String configurazione;
	
	public ConfigurazioneCasellaPostaleSearch() {
		super();
	}
	public ConfigurazioneCasellaPostaleSearch(String email, String configurazione) {
		super();
		this.email = email;
		this.configurazione = configurazione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfigurazione() {
		return configurazione;
	}
	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((configurazione == null) ? 0 : configurazione.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurazioneCasellaPostaleSearch other = (ConfigurazioneCasellaPostaleSearch) obj;
		if (configurazione == null) {
			if (other.configurazione != null)
				return false;
		} else if (!configurazione.equals(other.configurazione))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ConfigurazioneCasellaPostaleSearch [email=" + email + ", configurazione=" + configurazione + "]";
	}
	
	@Override
	protected void generateWhereCriteria() {
		String separatoer = " where ";
		if(email != null)
		{
			sql.append(separatoer)
			   .append(ConfigurazioneCasellaPostale.email.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneCasellaPostale.email.columnName());
			parameters.put(ConfigurazioneCasellaPostale.email.columnName(), email);
			separatoer = " and ";
		}
		if(configurazione != null)
		{
			sql.append(separatoer)
			   .append(ConfigurazioneCasellaPostale.configurazione.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneCasellaPostale.configurazione.columnName());
			parameters.put(ConfigurazioneCasellaPostale.configurazione.columnName(), configurazione);
			separatoer = " and ";
		}
	}

}
