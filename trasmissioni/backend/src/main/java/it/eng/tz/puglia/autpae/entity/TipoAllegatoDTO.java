package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

/**
 * DTO for table public.tipo_allegato
 */
public class TipoAllegatoDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private TipoAllegato type;
	private String descrizione;

	public TipoAllegatoDTO() { }

	public TipoAllegatoDTO(TipoAllegato type, String descrizione, boolean inviaMail) {
		this.type = type;
		this.descrizione = descrizione;
	}

	public TipoAllegato getType() {
		return type;
	}

	public void setType(TipoAllegato type) {
		this.type = type;
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
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
		TipoAllegatoDTO other = (TipoAllegatoDTO) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoProcedimentoDTO [type=" + type + ", descrizione=" + descrizione + "]";
	}

}
