package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.allegato_ente
 */
public class AllegatoEnteSearch extends WhereClauseGenerator<AllegatoEnte> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private String codice;
	private String descrizione;
	
	public AllegatoEnteSearch() { }

	public AllegatoEnteSearch(Long idAllegato, String codice, String descrizione) {
		this.idAllegato = idAllegato;
		this.codice = codice;
		this.descrizione = descrizione;
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
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
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
		AllegatoEnteSearch other = (AllegatoEnteSearch) obj;
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
		return "AllegatoEnteSearch [idAllegato=" + idAllegato + ", codice=" + codice + ", descrizione=" + descrizione + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(idAllegato != null)
		{
			sql.append(separator)
			   .append(AllegatoEnte.id_allegato.getCompleteName())
			   .append(" = :")
			   .append(AllegatoEnte.id_allegato.columnName());
			parameters.put(AllegatoEnte.id_allegato.columnName(), idAllegato);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(codice))
		{
			sql.append(separator)
			   .append(AllegatoEnte.codice.getCompleteName())
			   .append(" = :")
			   .append(AllegatoEnte.codice.columnName());
			parameters.put(AllegatoEnte.codice.columnName(), codice);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(AllegatoEnte.descrizione.getCompleteName())
			   .append(" = :")
			   .append(AllegatoEnte.descrizione.columnName());
			parameters.put(AllegatoEnte.descrizione.columnName(), descrizione);
			separator = " and ";
		}
	}
	
}
