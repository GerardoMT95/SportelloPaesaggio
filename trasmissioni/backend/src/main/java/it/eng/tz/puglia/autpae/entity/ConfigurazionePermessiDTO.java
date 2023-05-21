package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

public class ConfigurazionePermessiDTO implements Serializable {

	private static final long serialVersionUID = -7857613372208342240L;

	private String  codiceEnte;				// codice del Gruppo sul profilatore in formato XXXX_id   (senza _Y)
	private boolean permessoDocumentazione;
	private boolean permessoOsservazione;
	private boolean permessoComunicazione;
	private Boolean statoRegistrazionePubblico;
	private Boolean esitoVerificaPubblico;
	
	
	public ConfigurazionePermessiDTO() { }


	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public boolean isPermessoDocumentazione() {
		return permessoDocumentazione;
	}
	public void setPermessoDocumentazione(boolean permessoDocumentazione) {
		this.permessoDocumentazione = permessoDocumentazione;
	}
	public boolean isPermessoOsservazione() {
		return permessoOsservazione;
	}
	public void setPermessoOsservazione(boolean permessoOsservazione) {
		this.permessoOsservazione = permessoOsservazione;
	}
	public boolean isPermessoComunicazione() {
		return permessoComunicazione;
	}
	public void setPermessoComunicazione(boolean permessoComunicazione) {
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
		int result = 1;
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((esitoVerificaPubblico == null) ? 0 : esitoVerificaPubblico.hashCode());
		result = prime * result + (permessoComunicazione ? 1231 : 1237);
		result = prime * result + (permessoDocumentazione ? 1231 : 1237);
		result = prime * result + (permessoOsservazione ? 1231 : 1237);
		result = prime * result + ((statoRegistrazionePubblico == null) ? 0 : statoRegistrazionePubblico.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurazionePermessiDTO other = (ConfigurazionePermessiDTO) obj;
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
		if (permessoComunicazione != other.permessoComunicazione)
			return false;
		if (permessoDocumentazione != other.permessoDocumentazione)
			return false;
		if (permessoOsservazione != other.permessoOsservazione)
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
		return "ConfigurazionePermessiDTO [codiceEnte=" + codiceEnte + ", permessoDocumentazione="
				+ permessoDocumentazione + ", permessoOsservazione=" + permessoOsservazione + ", permessoComunicazione="
				+ permessoComunicazione + ", statoRegistrazionePubblico=" + statoRegistrazionePubblico
				+ ", esitoVerificaPubblico=" + esitoVerificaPubblico + "]";
	}
	

}