package it.eng.tz.puglia.autpae.search;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazionePermessi;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.configurazione_permessi
 */
public class ConfigurazionePermessiSearch extends WhereClauseGenerator<ConfigurazionePermessi> {

	private static final long serialVersionUID = -1230066225357675756L;

	private String  codiceEnte;
	private Boolean permessoDocumentazione;
	private Boolean permessoOsservazione;
	private Boolean permessoComunicazione;
	private Boolean statoRegistrazionePubblico;
	private Boolean esitoVerificaPubblico;

	
	public ConfigurazionePermessiSearch() {	}


	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public Boolean getPermessoDocumentazione() {
		return permessoDocumentazione;
	}
	public void setPermessoDocumentazione(Boolean permessoDocumentazione) {
		this.permessoDocumentazione = permessoDocumentazione;
	}
	public Boolean getPermessoOsservazione() {
		return permessoOsservazione;
	}
	public void setPermessoOsservazione(Boolean permessoOsservazione) {
		this.permessoOsservazione = permessoOsservazione;
	}
	public Boolean getPermessoComunicazione() {
		return permessoComunicazione;
	}
	public void setPermessoComunicazione(Boolean permessoComunicazione) {
		this.permessoComunicazione = permessoComunicazione;
	}
	public Boolean getStatoRegistrazionePubblico() {
		return statoRegistrazionePubblico;
	}
	public void setStatoRegistrazionePubblico(Boolean statoRegistrazionePubblico) {
		this.statoRegistrazionePubblico = statoRegistrazionePubblico;
	}
	public Boolean getEsitoVerificaPubblico() {
		return esitoVerificaPubblico;
	}
	public void setEsitoVerificaPubblico(Boolean esitoVerificaPubblico) {
		this.esitoVerificaPubblico = esitoVerificaPubblico;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((esitoVerificaPubblico == null) ? 0 : esitoVerificaPubblico.hashCode());
		result = prime * result + ((permessoComunicazione == null) ? 0 : permessoComunicazione.hashCode());
		result = prime * result + ((permessoDocumentazione == null) ? 0 : permessoDocumentazione.hashCode());
		result = prime * result + ((permessoOsservazione == null) ? 0 : permessoOsservazione.hashCode());
		result = prime * result + ((statoRegistrazionePubblico == null) ? 0 : statoRegistrazionePubblico.hashCode());
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
		ConfigurazionePermessiSearch other = (ConfigurazionePermessiSearch) obj;
		if (codiceEnte == null) {
			if (other.codiceEnte != null)
				return false;
		} else if (!codiceEnte.equals(other.codiceEnte))
			return false;
		if (esitoVerificaPubblico == null) {
			if (other.esitoVerificaPubblico != null)
				return false;
		} else if (!esitoVerificaPubblico.equals(other.esitoVerificaPubblico))
			return false;
		if (permessoComunicazione == null) {
			if (other.permessoComunicazione != null)
				return false;
		} else if (!permessoComunicazione.equals(other.permessoComunicazione))
			return false;
		if (permessoDocumentazione == null) {
			if (other.permessoDocumentazione != null)
				return false;
		} else if (!permessoDocumentazione.equals(other.permessoDocumentazione))
			return false;
		if (permessoOsservazione == null) {
			if (other.permessoOsservazione != null)
				return false;
		} else if (!permessoOsservazione.equals(other.permessoOsservazione))
			return false;
		if (statoRegistrazionePubblico == null) {
			if (other.statoRegistrazionePubblico != null)
				return false;
		} else if (!statoRegistrazionePubblico.equals(other.statoRegistrazionePubblico))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ConfigurazionePermessiSearch [codiceEnte=" + codiceEnte + ", permessoDocumentazione="
				+ permessoDocumentazione + ", permessoOsservazione=" + permessoOsservazione + ", permessoComunicazione="
				+ permessoComunicazione + ", statoRegistrazionePubblico=" + statoRegistrazionePubblico
				+ ", esitoVerificaPubblico=" + esitoVerificaPubblico + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separatore = " where ";
		if(!StringUtil.isEmpty(codiceEnte))
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.codice_ente.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.codice_ente.columnName());
			parameters.put(ConfigurazionePermessi.codice_ente.columnName(), codiceEnte);
			separatore = " and ";
		}
		if(permessoDocumentazione != null)
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.permesso_documentazione.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.permesso_documentazione.columnName());
			parameters.put(ConfigurazionePermessi.permesso_documentazione.columnName(), permessoDocumentazione);
			separatore = " and ";
		}
		if(permessoOsservazione != null)
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.permesso_osservazione.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.permesso_osservazione.columnName());
			parameters.put(ConfigurazionePermessi.permesso_osservazione.columnName(), permessoOsservazione);
			separatore = " and ";
		}
		if(permessoComunicazione != null)
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.permesso_comunicazione.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.permesso_comunicazione.columnName());
			parameters.put(ConfigurazionePermessi.permesso_comunicazione.columnName(), permessoComunicazione);
			separatore = " and ";
		}
		if(statoRegistrazionePubblico != null)
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.stato_registrazione_pubblico.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.stato_registrazione_pubblico.columnName());
			parameters.put(ConfigurazionePermessi.stato_registrazione_pubblico.columnName(), statoRegistrazionePubblico);
			separatore = " and ";
		}
		if(esitoVerificaPubblico != null)
		{
			sql.append(separatore)
			.append(ConfigurazionePermessi.esito_verifica_pubblico.getCompleteName())
			.append(" = :")
			.append(ConfigurazionePermessi.esito_verifica_pubblico.columnName());
			parameters.put(ConfigurazionePermessi.esito_verifica_pubblico.columnName(), esitoVerificaPubblico);
			separatore = " and ";
		}
	}

}