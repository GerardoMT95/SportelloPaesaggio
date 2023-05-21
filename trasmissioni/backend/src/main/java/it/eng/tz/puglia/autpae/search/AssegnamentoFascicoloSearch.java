package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table assegnamento_fascicolo
 */
public class AssegnamentoFascicoloSearch extends WhereClauseGenerator<AssegnamentoFascicolo> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idFascicolo;
	private Integer idOrganizzazione;
	private String fase;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private Short numAssegnazioni;
	private Date dataOperazione;
	
	
	public AssegnamentoFascicoloSearch() { }
	
	
	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
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
	public Short getNumAssegnazioni() {
		return numAssegnazioni;
	}
	public void setNumAssegnazioni(Short numAssegnazioni) {
		this.numAssegnazioni = numAssegnazioni;
	}
	public Date getDataOperazione() {
		return dataOperazione;
	}
	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataOperazione == null) ? 0 : dataOperazione.hashCode());
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((idOrganizzazione == null) ? 0 : idOrganizzazione.hashCode());
		result = prime * result + ((numAssegnazioni == null) ? 0 : numAssegnazioni.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
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
		AssegnamentoFascicoloSearch other = (AssegnamentoFascicoloSearch) obj;
		if (dataOperazione == null) {
			if (other.dataOperazione != null)
				return false;
		} else if (!dataOperazione.equals(other.dataOperazione))
			return false;
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
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idOrganizzazione == null) {
			if (other.idOrganizzazione != null)
				return false;
		} else if (!idOrganizzazione.equals(other.idOrganizzazione))
			return false;
		if (numAssegnazioni == null) {
			if (other.numAssegnazioni != null)
				return false;
		} else if (!numAssegnazioni.equals(other.numAssegnazioni))
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
		return "AssegnamentoFascicoloSearch [idFascicolo=" + idFascicolo + ", idOrganizzazione=" + idOrganizzazione
				+ ", fase=" + fase + ", usernameFunzionario=" + usernameFunzionario + ", denominazioneFunzionario="
				+ denominazioneFunzionario + ", numAssegnazioni=" + numAssegnazioni + ", dataOperazione="
				+ dataOperazione + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.id_fascicolo.columnName());
			parameters.put(AssegnamentoFascicolo.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
		if(idOrganizzazione != null)
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.id_organizzazione.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.id_organizzazione.columnName());
			parameters.put(AssegnamentoFascicolo.id_organizzazione.columnName(), idOrganizzazione);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(fase))
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.fase.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.fase.columnName());
			parameters.put(AssegnamentoFascicolo.fase.columnName(), fase);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(usernameFunzionario))
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.username_funzionario.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.username_funzionario.columnName());
			parameters.put(AssegnamentoFascicolo.username_funzionario.columnName(), usernameFunzionario);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(denominazioneFunzionario))
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.denominazione_funzionario.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.denominazione_funzionario.columnName());
			parameters.put(AssegnamentoFascicolo.denominazione_funzionario.columnName(), denominazioneFunzionario);
			separator = " and ";
		}
		if(numAssegnazioni != null)
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.num_assegnazioni.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.num_assegnazioni.columnName());
			parameters.put(AssegnamentoFascicolo.num_assegnazioni.columnName(), numAssegnazioni);
			separator = " and ";
		}
		if(dataOperazione != null)
		{
			sql.append(separator)
			   .append(AssegnamentoFascicolo.data_operazione.getCompleteName())
			   .append(" = :")
			   .append(AssegnamentoFascicolo.data_operazione.columnName());
			parameters.put(AssegnamentoFascicolo.data_operazione.columnName(), dataOperazione);
			separator = " and ";
		}
	}
	
}