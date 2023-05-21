package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TipiEQualificazioni;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.tipi_e_qualificazioni
 */
public class TipiEQualificazioniSearch extends WhereClauseGenerator<TipiEQualificazioni> {

	private static final long serialVersionUID = 3043457197166101455L;
	
	private Long id;
	private String stile;
	private Integer zona;
	private String label;
	private Integer ordine;
	private String raggruppamento;

	public TipiEQualificazioniSearch() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStile() {
		return stile;
	}

	public void setStile(String stile) {
		this.stile = stile;
	}

	public Integer getZona() {
		return zona;
	}

	public void setZona(Integer zona) {
		this.zona = zona;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}
	
	public String getRaggruppamento() {
		return raggruppamento;
	}

	public void setRaggruppamento(String raggruppamento) {
		this.raggruppamento = raggruppamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((ordine == null) ? 0 : ordine.hashCode());
		result = prime * result + ((stile == null) ? 0 : stile.hashCode());
		result = prime * result + ((zona == null) ? 0 : zona.hashCode());
		result = prime * result + ((raggruppamento == null) ? 0 : raggruppamento.hashCode());
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
		TipiEQualificazioniSearch other = (TipiEQualificazioniSearch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (raggruppamento == null) {
			if (other.raggruppamento != null)
				return false;
		} else if (!raggruppamento.equals(other.raggruppamento))
			return false;
		if (ordine == null) {
			if (other.ordine != null)
				return false;
		} else if (!ordine.equals(other.ordine))
			return false;
		if (stile == null) {
			if (other.stile != null)
				return false;
		} else if (!stile.equals(other.stile))
			return false;
		if (zona == null) {
			if (other.zona != null)
				return false;
		} else if (!zona.equals(other.zona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipiEQualificazioniSearch [id=" + id + ", stile=" + stile + ", zona=" + zona + ", label=" + label
				+ ", ordine=" + ordine + ", raggruppamento=" + raggruppamento + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.id.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.id.columnName());
			parameters.put(TipiEQualificazioni.id.columnName(), id);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(stile))
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.stile.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.stile.columnName());
			parameters.put(TipiEQualificazioni.stile.columnName(), stile);
			separator = " and ";
		}
		if(zona != null)
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.zona.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.zona.columnName());
			parameters.put(TipiEQualificazioni.zona.columnName(), zona);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(label))
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.label.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.label.columnName());
			parameters.put(TipiEQualificazioni.label.columnName(), label);
			separator = " and ";
		}
		if(ordine != null)
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.ordine.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.ordine.columnName());
			parameters.put(TipiEQualificazioni.ordine.columnName(), ordine);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(raggruppamento))
		{
			sql.append(separator)
			   .append(TipiEQualificazioni.raggruppamento.getCompleteName())
			   .append(" = :")
			   .append(TipiEQualificazioni.raggruppamento.columnName());
			parameters.put(TipiEQualificazioni.raggruppamento.columnName(), raggruppamento);
			separator = " and ";
		}
		
	}

}
