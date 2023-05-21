package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCampionamento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.fascicolo_campionamento
 */
public class FascicoloCampionamentoSearch extends WhereClauseGenerator<FascicoloCampionamento> {

	private static final long serialVersionUID = 1695008947369852015L;
	
	private Long idCampionamento;
	private Long idFascicolo;
	
	
	public FascicoloCampionamentoSearch() {	}
	
	
	public FascicoloCampionamentoSearch(Long idCampionamento, Long idFascicolo) {
		this.idCampionamento = idCampionamento;
		this.idFascicolo = idFascicolo;
	}

	
	public Long getIdCampionamento() {
		return idCampionamento;
	}

	public void setIdCampionamento(Long idCampionamento) {
		this.idCampionamento = idCampionamento;
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
		result = prime * result + ((idCampionamento == null) ? 0 : idCampionamento.hashCode());
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
		FascicoloCampionamentoSearch other = (FascicoloCampionamentoSearch) obj;
		if (idCampionamento == null) {
			if (other.idCampionamento != null)
				return false;
		} else if (!idCampionamento.equals(other.idCampionamento))
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
		return "FascicoloCampionamentoSearch [idCampionamento=" + idCampionamento + ", idFascicolo=" + idFascicolo + "]";
	}

	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(idCampionamento != null)
		{
			sql.append(separator)
			   .append(FascicoloCampionamento.id_campionamento.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCampionamento.id_campionamento.columnName());
			parameters.put(FascicoloCampionamento.id_campionamento.columnName(), idCampionamento);
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(FascicoloCampionamento.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCampionamento.id_fascicolo.columnName());
			parameters.put(FascicoloCampionamento.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
	}

}
