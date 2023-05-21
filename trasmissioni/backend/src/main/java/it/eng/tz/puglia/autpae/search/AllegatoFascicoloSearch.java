package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.allegato_fascicolo
 */
public class AllegatoFascicoloSearch extends WhereClauseGenerator<AllegatoFascicolo> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private TipoAllegato type;
	private Long idFascicolo;
	
	
	public AllegatoFascicoloSearch() { }
	
	public AllegatoFascicoloSearch(Long idAllegato, TipoAllegato type, Long idFascicolo) { 
		this.idAllegato = idAllegato;
		this.type = type;
		this.idFascicolo = idFascicolo;
	}
	

	public Long getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}
	public TipoAllegato getType() {
		return type;
	}
	public void setType(TipoAllegato type) {
		this.type = type;
	}
	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
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
		AllegatoFascicoloSearch other = (AllegatoFascicoloSearch) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
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
		return "AllegatoDTO [idAllegato=" + idAllegato + ", type=" + type + ", idFascicolo=" + idFascicolo + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(idAllegato != null)
		{
			sql.append(separator)
			   .append(AllegatoFascicolo.id_allegato.getCompleteName())
			   .append(" = :")
			   .append(AllegatoFascicolo.id_allegato.columnName());
			parameters.put(AllegatoFascicolo.id_allegato.columnName(), idAllegato);
			separator = " and ";
		}
		if(type!=null)
		{
			sql.append(separator)
			   .append(AllegatoFascicolo.type.getCompleteName())
			   .append(" = :")
			   .append(AllegatoFascicolo.type.columnName());
			parameters.put(AllegatoFascicolo.type.columnName(), type.name());
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(AllegatoFascicolo.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(AllegatoFascicolo.id_fascicolo.columnName());
			parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
	}

}