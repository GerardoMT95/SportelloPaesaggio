package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.ValoriAssegnamento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table valori_assegnamento
 */
public class ValoriAssegnamentoSearch extends WhereClauseGenerator<ValoriAssegnamento> {

	private static final long serialVersionUID = 6693642738L;
	
	private Integer idOrganizzazione;
	private String fase;
	private String idComuneTipoProcedimento;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	
	
	public ValoriAssegnamentoSearch() { }
	
	
	public Integer getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(Integer idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getIdComuneTipoProcedimento() {
		return idComuneTipoProcedimento;
	}
	public void setIdComuneTipoProcedimento(String idComuneTipoProcedimento) {
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
	}
	public String getUsernameFunzionario() {
		return usernameFunzionario;
	}
	public void setUsernameFunzionario(String usernameFunzionario) {
		this.usernameFunzionario = usernameFunzionario;
	}
	public String getDenominazioneFunzionario() {
		return denominazioneFunzionario;
	}
	public void setDenominazioneFunzionario(String denominazioneFunzionario) {
		this.denominazioneFunzionario = denominazioneFunzionario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idComuneTipoProcedimento == null) ? 0 : idComuneTipoProcedimento.hashCode());
		result = prime * result + ((idOrganizzazione == null) ? 0 : idOrganizzazione.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
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
		ValoriAssegnamentoSearch other = (ValoriAssegnamentoSearch) obj;
		if (denominazioneFunzionario == null) {
			if (other.denominazioneFunzionario != null)
				return false;
		} else if (!denominazioneFunzionario.equals(other.denominazioneFunzionario))
			return false;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (idComuneTipoProcedimento == null) {
			if (other.idComuneTipoProcedimento != null)
				return false;
		} else if (!idComuneTipoProcedimento.equals(other.idComuneTipoProcedimento))
			return false;
		if (idOrganizzazione == null) {
			if (other.idOrganizzazione != null)
				return false;
		} else if (!idOrganizzazione.equals(other.idOrganizzazione))
			return false;
		if (usernameFunzionario == null) {
			if (other.usernameFunzionario != null)
				return false;
		} else if (!usernameFunzionario.equals(other.usernameFunzionario))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ValoriAssegnamentoSearch [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", idComuneTipoProcedimento=" + idComuneTipoProcedimento + ", usernameFunzionario="
				+ usernameFunzionario + ", denominazioneFunzionario=" + denominazioneFunzionario + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(idOrganizzazione != null)
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.id_organizzazione.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.id_organizzazione.columnName());
			parameters.put(ValoriAssegnamento.id_organizzazione.columnName(), idOrganizzazione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(fase))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.fase.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.fase.columnName());
			parameters.put(ValoriAssegnamento.fase.columnName(), fase);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(idComuneTipoProcedimento))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.id_comune_tipo_procedimento.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.id_comune_tipo_procedimento.columnName());
			parameters.put(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), idComuneTipoProcedimento);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameFunzionario))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.username_funzionario.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.username_funzionario.columnName());
			parameters.put(ValoriAssegnamento.username_funzionario.columnName(), usernameFunzionario);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(denominazioneFunzionario))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.denominazione_funzionario.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.denominazione_funzionario.columnName());
			parameters.put(ValoriAssegnamento.denominazione_funzionario.columnName(), denominazioneFunzionario);
			separator = " and ";
		}
	}
	
}