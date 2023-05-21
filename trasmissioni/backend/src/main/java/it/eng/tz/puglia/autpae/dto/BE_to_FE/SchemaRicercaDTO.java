package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;

public class SchemaRicercaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean registrazione;
	private boolean verifica;
	
	
	public SchemaRicercaDTO() { }

	public SchemaRicercaDTO(boolean registrazione, boolean verifica) { 
		this.registrazione = registrazione;
		this.verifica 	   = verifica;
	}
	
	public SchemaRicercaDTO(ConfigurazionePermessiDTO configurazionePermessi) { 
		this.registrazione = configurazionePermessi.getStatoRegistrazionePubblico();
		this.verifica 	   = configurazionePermessi.getEsitoVerificaPubblico();
	}
	
	
	public boolean isRegistrazione() {
		return registrazione;
	}

	public void setRegistrazione(boolean registrazione) {
		this.registrazione = registrazione;
	}

	public boolean isVerifica() {
		return verifica;
	}

	public void setVerifica(boolean verifica) {
		this.verifica = verifica;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (registrazione ? 1231 : 1237);
		result = prime * result + (verifica ? 1231 : 1237);
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
		SchemaRicercaDTO other = (SchemaRicercaDTO) obj;
		if (registrazione != other.registrazione)
			return false;
		if (verifica != other.verifica)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SchemaRicercaDTO [registrazione=" + registrazione + ", verifica=" + verifica + "]";
	}

}