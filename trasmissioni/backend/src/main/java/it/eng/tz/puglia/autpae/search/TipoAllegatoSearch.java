package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TipoAllegato;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.tipo_allegato
 */
public class TipoAllegatoSearch extends WhereClauseGenerator<TipoAllegato> {

	private static final long serialVersionUID = 6693642738L;
	
	private it.eng.tz.puglia.autpae.enumeratori.TipoAllegato type;
	private String descrizione;

	public TipoAllegatoSearch() { }
	
	public TipoAllegatoSearch(it.eng.tz.puglia.autpae.enumeratori.TipoAllegato type, String descrizione, Boolean inviaMail) {
		this.type = type;
		this.descrizione = descrizione;
	}

	public it.eng.tz.puglia.autpae.enumeratori.TipoAllegato getType() {
		return type;
	}
	public void setType(it.eng.tz.puglia.autpae.enumeratori.TipoAllegato type) {
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
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
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
		TipoAllegatoSearch other = (TipoAllegatoSearch) obj;
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
		return "TipoAllegatoSearch [type=" + type + ", descrizione=" + descrizione + "]";
	}


	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(type!=null)
		{
			sql.append(separator)
			   .append(TipoAllegato.type.getCompleteName())
			   .append(" = :")
			   .append(TipoAllegato.type.columnName());
			parameters.put(TipoAllegato.type.columnName(), type.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(TipoAllegato.descrizione.getCompleteName())
			   .append(" = :")
			   .append(TipoAllegato.descrizione.columnName());
			parameters.put(TipoAllegato.descrizione.columnName(), descrizione);
			separator = " and ";
		}
	}
	
}
