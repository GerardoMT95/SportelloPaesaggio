package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.allegato_corrispondenza
 */
public class AllegatoCorrispondenzaSearch extends WhereClauseGenerator<AllegatoCorrispondenza> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private Long idCorrispondenza;
	
	public AllegatoCorrispondenzaSearch() { }
	
	public AllegatoCorrispondenzaSearch(Long idAllegato, Long idCorrispondenza) {
		this.idAllegato = idAllegato;
		this.idCorrispondenza = idCorrispondenza;
	}

	@Override
	public String toString() {
		return "AllegatoCorrispondenzaSearch [idAllegato=" + idAllegato + ", idCorrispondenza=" + idCorrispondenza + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
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
		AllegatoCorrispondenzaSearch other = (AllegatoCorrispondenzaSearch) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		return true;
	}

	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}

	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(idAllegato != null)
		{
			sql.append(separator)
			   .append(AllegatoCorrispondenza.id_allegato.getCompleteName())
			   .append(" = :")
			   .append(AllegatoCorrispondenza.id_allegato.columnName());
			parameters.put(AllegatoCorrispondenza.id_allegato.columnName(), idAllegato);
			separator = " and ";
		}
		if(idCorrispondenza != null)
		{
			sql.append(separator)
			   .append(AllegatoCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = :")
			   .append(AllegatoCorrispondenza.id_corrispondenza.columnName());
			parameters.put(AllegatoCorrispondenza.id_corrispondenza.columnName(), idCorrispondenza);
			separator = " and ";
		}
	}
	
}
