package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoObbligatorio;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.allegato_obbligatorio
 */
public class AllegatoObbligatorioSearch extends WhereClauseGenerator<AllegatoObbligatorio> {

	private static final long serialVersionUID = 6693642738L;
	
	private TipoAllegato type;
	private TipoProcedimento tipoProcedimento;

	public AllegatoObbligatorioSearch() { }

	public AllegatoObbligatorioSearch(TipoAllegato type, TipoProcedimento tipoProcedimento) {
		this.type = type;
		this.tipoProcedimento = tipoProcedimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AllegatoObbligatorioSearch other = (AllegatoObbligatorioSearch) obj;
		if (tipoProcedimento != other.tipoProcedimento)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public TipoAllegato getType() {
		return type;
	}

	public void setType(TipoAllegato type) {
		this.type = type;
	}

	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	@Override
	public String toString() {
		return "AllegatoObbligatorioSearch [type=" + type + ", tipoProcedimento=" + tipoProcedimento + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(type!=null)
		{
			sql.append(separator)
			   .append(AllegatoObbligatorio.type.getCompleteName())
			   .append(" = :")
			   .append(AllegatoObbligatorio.type.columnName());
			parameters.put(AllegatoObbligatorio.type.columnName(), type.name());
			separator = " and ";
		}
		if(tipoProcedimento != null)
		{
			sql.append(separator)
			   .append(AllegatoObbligatorio.codice_tipo_procedimento.getCompleteName())
			   .append(" = :")
			   .append(AllegatoObbligatorio.codice_tipo_procedimento.columnName());
			parameters.put(AllegatoObbligatorio.codice_tipo_procedimento.columnName(), tipoProcedimento.name());
			separator = " and ";
		}
	}
	
}
