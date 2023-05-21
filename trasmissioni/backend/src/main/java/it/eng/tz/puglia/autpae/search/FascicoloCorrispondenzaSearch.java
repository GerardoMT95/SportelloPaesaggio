package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCorrispondenza;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.fascicolo_corrispondenza
 */
public class FascicoloCorrispondenzaSearch extends WhereClauseGenerator<FascicoloCorrispondenza> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private Long idCorrispondenza;
	private Long idFascicolo;
	
	
	public FascicoloCorrispondenzaSearch() { }
	
	public FascicoloCorrispondenzaSearch(Long id, Long idCorrispondenza, Long idFascicolo) {
		this.id = id;
		this.idCorrispondenza = idCorrispondenza;
		this.idFascicolo = idFascicolo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
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
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
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
		FascicoloCorrispondenzaSearch other = (FascicoloCorrispondenzaSearch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FascicoloCorrispondenzaSearch [id=" + id + ", idCorrispondenza=" + idCorrispondenza + ", idFascicolo=" + idFascicolo + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(FascicoloCorrispondenza.id.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCorrispondenza.id.columnName());
			parameters.put(FascicoloCorrispondenza.id.columnName(), id);
			separator = " and ";
		}
		if(idCorrispondenza != null)
		{
			sql.append(separator)
			   .append(FascicoloCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCorrispondenza.id_corrispondenza.columnName());
			parameters.put(FascicoloCorrispondenza.id_corrispondenza.columnName(), idCorrispondenza);
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(FascicoloCorrispondenza.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCorrispondenza.id_fascicolo.columnName());
			parameters.put(FascicoloCorrispondenza.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
	}
	
}
