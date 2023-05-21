package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;

public class InterventoTabDTO implements Serializable
{
	private static final long serialVersionUID = 6985990040232694483L;
	
	private List<TipiEQualificazioniDTO> lista;
	

	public InterventoTabDTO() {
		lista = new ArrayList<TipiEQualificazioniDTO>();
	}
	
	public InterventoTabDTO(List<TipiEQualificazioniDTO> lista) {
		this.lista = lista;
	}

	public List<TipiEQualificazioniDTO> getLista() {
		return lista;
	}

	public void setLista(List<TipiEQualificazioniDTO> lista) {
		this.lista = lista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
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
		InterventoTabDTO other = (InterventoTabDTO) obj;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else if (!lista.equals(other.lista))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InterventoTabCustomDTO [lista=" + lista + "]";
	}
	
}
