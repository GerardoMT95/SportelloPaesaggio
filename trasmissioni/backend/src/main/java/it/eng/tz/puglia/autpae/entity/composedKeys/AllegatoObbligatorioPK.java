package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

public class AllegatoObbligatorioPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private TipoAllegato type;
	private String codiceTipoProcedimento;
	
	public AllegatoObbligatorioPK() { }

	public AllegatoObbligatorioPK(TipoAllegato type, String codiceTipoProcedimento) {
		this.type = type;
		this.codiceTipoProcedimento = codiceTipoProcedimento;
	}

	public TipoAllegato getType() {
		return type;
	}

	public void setType(TipoAllegato type) {
		this.type = type;
	}

	public String getCodiceTipoProcedimento() {
		return codiceTipoProcedimento;
	}

	public void setCodiceTipoProcedimento(String codiceTipoProcedimento) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceTipoProcedimento == null) ? 0 : codiceTipoProcedimento.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AllegatoObbligatorioPK other = (AllegatoObbligatorioPK) obj;
		if (codiceTipoProcedimento == null) {
			if (other.codiceTipoProcedimento != null)
				return false;
		} else if (!codiceTipoProcedimento.equals(other.codiceTipoProcedimento))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoObbligatorioPK [type=" + type + ", codiceTipoProcedimento=" + codiceTipoProcedimento + "]";
	}

}
