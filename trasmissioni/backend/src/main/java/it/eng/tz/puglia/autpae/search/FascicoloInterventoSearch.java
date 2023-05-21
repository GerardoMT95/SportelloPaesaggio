package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.fascicolo_intervento
 */
public class FascicoloInterventoSearch extends WhereClauseGenerator<FascicoloIntervento> {

	private static final long serialVersionUID = 432024893431691149L;
	
	private Long idFascicolo;
	private Long idTipiQualificazioni;

	public FascicoloInterventoSearch() { }

	public FascicoloInterventoSearch(Long idFascicolo, Long idTipiQualificazioni) {
		super();
		this.idFascicolo = idFascicolo;
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public Long getIdTipiQualificazioni() {
		return idTipiQualificazioni;
	}

	public void setIdTipiQualificazioni(Long idTipiQualificazioni) {
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((idTipiQualificazioni == null) ? 0 : idTipiQualificazioni.hashCode());
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
		FascicoloInterventoSearch other = (FascicoloInterventoSearch) obj;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idTipiQualificazioni == null) {
			if (other.idTipiQualificazioni != null)
				return false;
		} else if (!idTipiQualificazioni.equals(other.idTipiQualificazioni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FascicoloInterventoSearch [idFascicolo=" + idFascicolo + ", idTipiQualificazioni=" + idTipiQualificazioni + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(FascicoloIntervento.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(FascicoloIntervento.id_fascicolo.columnName());
			parameters.put(FascicoloIntervento.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
		if(idTipiQualificazioni != null)
		{
			sql.append(separator)
			   .append(FascicoloIntervento.id_tipi_qualificazioni.getCompleteName())
			   .append(" = :")
			   .append(FascicoloIntervento.id_tipi_qualificazioni.columnName());
			parameters.put(FascicoloIntervento.id_tipi_qualificazioni.columnName(), idTipiQualificazioni);
			separator = " and ";
		}
		
	}

}
