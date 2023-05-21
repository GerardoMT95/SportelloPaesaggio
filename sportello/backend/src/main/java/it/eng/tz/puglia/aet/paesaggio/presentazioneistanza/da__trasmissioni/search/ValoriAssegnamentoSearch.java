package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.ValoriAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table valori_assegnamento
 */
public class ValoriAssegnamentoSearch extends Trasmissioni_WhereClauseGenerator<ValoriAssegnamento> {

	private static final long serialVersionUID = 6693642738L;
	
	private Integer idOrganizzazione;
	private String fase;
	private Integer idComuneTipoProcedimento;
	private Boolean rup;
	private String usernameUtente;
	private String denominazioneUtente;
	
	
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
	public Integer getIdComuneTipoProcedimento() {
		return idComuneTipoProcedimento;
	}
	public void setIdComuneTipoProcedimento(Integer idComuneTipoProcedimento) {
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
	}
	public Boolean getRup() {
		return rup;
	}
	public void setRup(Boolean rup) {
		this.rup = rup;
	}
	public String getUsernameUtente() {
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	public String getDenominazioneUtente() {
		return denominazioneUtente;
	}
	public void setDenominazioneUtente(String denominazioneUtente) {
		this.denominazioneUtente = denominazioneUtente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((denominazioneUtente == null) ? 0 : denominazioneUtente.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idComuneTipoProcedimento == null) ? 0 : idComuneTipoProcedimento.hashCode());
		result = prime * result + ((idOrganizzazione == null) ? 0 : idOrganizzazione.hashCode());
		result = prime * result + ((rup == null) ? 0 : rup.hashCode());
		result = prime * result + ((usernameUtente == null) ? 0 : usernameUtente.hashCode());
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
		if (denominazioneUtente == null) {
			if (other.denominazioneUtente != null)
				return false;
		} else if (!denominazioneUtente.equals(other.denominazioneUtente))
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
		if (rup == null) {
			if (other.rup != null)
				return false;
		} else if (!rup.equals(other.rup))
			return false;
		if (usernameUtente == null) {
			if (other.usernameUtente != null)
				return false;
		} else if (!usernameUtente.equals(other.usernameUtente))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ValoriAssegnamentoSearch [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", idComuneTipoProcedimento=" + idComuneTipoProcedimento + ", rup=" + rup + ", usernameUtente="
				+ usernameUtente + ", denominazioneUtente=" + denominazioneUtente + "]";
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
		if(idComuneTipoProcedimento != null)
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.id_comune_tipo_procedimento.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.id_comune_tipo_procedimento.columnName());
			parameters.put(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), idComuneTipoProcedimento);
			separator = " and ";
		}
		if(rup != null)
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.rup.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.rup.columnName());
			parameters.put(ValoriAssegnamento.rup.columnName(), rup);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameUtente))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.username_utente.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.username_utente.columnName());
			parameters.put(ValoriAssegnamento.username_utente.columnName(), usernameUtente);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(denominazioneUtente))
		{
			sql.append(separator)
			   .append(ValoriAssegnamento.denominazione_utente.getCompleteName())
			   .append(" = :")
			   .append(ValoriAssegnamento.denominazione_utente.columnName());
			parameters.put(ValoriAssegnamento.denominazione_utente.columnName(), denominazioneUtente);
			separator = " and ";
		}
	}
	
}