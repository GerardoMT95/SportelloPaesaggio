package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

/**
 * DTO for table public.allegato_ente
 */
public class AllegatoEnteDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private String codice;
	private String descrizione;
	
	public AllegatoEnteDTO() {}

	public AllegatoEnteDTO(Long idAllegato, String codiceEnte, String descrizioneEnte) {
		this.idAllegato = idAllegato;
		this.codice = codiceEnte;
		this.descrizione = descrizioneEnte;
	}

	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
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
		AllegatoEnteDTO other = (AllegatoEnteDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoEnteDTO [idAllegato=" + idAllegato + ", codice=" + codice + ", descrizione=" + descrizione
				+ "]";
	}

}
