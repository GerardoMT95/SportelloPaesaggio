package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.StoricoAssegnamento;
import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table storico_assegnamento
 */
public class StoricoAssegnamentoSearch extends WhereClauseGenerator<StoricoAssegnamento> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private Long idFascicolo;
	private Integer idOrganizzazione;
	private String fase;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private TipoOperazione tipoOperazione;
	private Date dataOperazione;
	
	
	public StoricoAssegnamentoSearch() { }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public TipoOperazione getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(TipoOperazione tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
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
		int result = super.hashCode();
		result = prime * result + ((dataOperazione == null) ? 0 : dataOperazione.hashCode());
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((idOrganizzazione == null) ? 0 : idOrganizzazione.hashCode());
		result = prime * result + ((tipoOperazione == null) ? 0 : tipoOperazione.hashCode());
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
		StoricoAssegnamentoSearch other = (StoricoAssegnamentoSearch) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (tipoOperazione != other.tipoOperazione)
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
		return "StoricoAssegnamentoSearch [id=" + id + ", idFascicolo=" + idFascicolo + ", idOrganizzazione="
				+ idOrganizzazione + ", fase=" + fase + ", usernameFunzionario=" + usernameFunzionario
				+ ", denominazioneFunzionario=" + denominazioneFunzionario + ", tipoOperazione=" + tipoOperazione
				+ ", dataOperazione=" + dataOperazione + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.id.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.id.columnName());
			parameters.put(StoricoAssegnamento.id.columnName(), id);
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.id_fascicolo.columnName());
			parameters.put(StoricoAssegnamento.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
		if(idOrganizzazione != null)
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.id_organizzazione.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.id_organizzazione.columnName());
			parameters.put(StoricoAssegnamento.id_organizzazione.columnName(), idOrganizzazione);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(fase))
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.fase.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.fase.columnName());
			parameters.put(StoricoAssegnamento.fase.columnName(), fase);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(usernameFunzionario))
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.username_funzionario.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.username_funzionario.columnName());
			parameters.put(StoricoAssegnamento.username_funzionario.columnName(), usernameFunzionario);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(denominazioneFunzionario))
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.denominazione_funzionario.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.denominazione_funzionario.columnName());
			parameters.put(StoricoAssegnamento.denominazione_funzionario.columnName(), denominazioneFunzionario);
			separator = " and ";
		}
		if(dataOperazione != null)
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.data_operazione.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.data_operazione.columnName());
			parameters.put(StoricoAssegnamento.data_operazione.columnName(), dataOperazione);
			separator = " and ";
		}
		if(tipoOperazione != null)
		{
			sql.append(separator)
			   .append(StoricoAssegnamento.tipo_operazione.getCompleteName())
			   .append(" = :")
			   .append(StoricoAssegnamento.tipo_operazione.columnName());
			parameters.put(StoricoAssegnamento.tipo_operazione.columnName(), tipoOperazione.name());
			separator = " and ";
		}
	}
	
}