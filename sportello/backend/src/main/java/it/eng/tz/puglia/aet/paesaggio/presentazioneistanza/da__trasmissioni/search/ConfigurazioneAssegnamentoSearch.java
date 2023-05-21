package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.ConfigurazioneAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table configurazione_assegnamento
 */
public class ConfigurazioneAssegnamentoSearch extends Trasmissioni_WhereClauseGenerator<ConfigurazioneAssegnamento> {

	private static final long serialVersionUID = 6693642738L;
	
	private Integer idOrganizzazione;
	private String fase;
	private Boolean rup;
	private TipoAssegnamento criterioAssegnamento;
	
	
	public ConfigurazioneAssegnamentoSearch() { }
	
	
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
	public Boolean getRup() {
		return rup;
	}
	public void setRup(Boolean rup) {
		this.rup = rup;
	}
	public TipoAssegnamento getCriterioAssegnamento() {
		return criterioAssegnamento;
	}
	public void setCriterioAssegnamento(TipoAssegnamento criterioAssegnamento) {
		this.criterioAssegnamento = criterioAssegnamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((criterioAssegnamento == null) ? 0 : criterioAssegnamento.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idOrganizzazione == null) ? 0 : idOrganizzazione.hashCode());
		result = prime * result + ((rup == null) ? 0 : rup.hashCode());
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
		ConfigurazioneAssegnamentoSearch other = (ConfigurazioneAssegnamentoSearch) obj;
		if (criterioAssegnamento != other.criterioAssegnamento)
			return false;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "ConfigurazioneAssegnamentoSearch [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", criterioAssegnamento=" + criterioAssegnamento + "]";
	}

	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(idOrganizzazione != null)
		{
			sql.append(separator)
			   .append(ConfigurazioneAssegnamento.id_organizzazione.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneAssegnamento.id_organizzazione.columnName());
			parameters.put(ConfigurazioneAssegnamento.id_organizzazione.columnName(), idOrganizzazione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(fase))
		{
			sql.append(separator)
			   .append(ConfigurazioneAssegnamento.fase.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneAssegnamento.fase.columnName());
			parameters.put(ConfigurazioneAssegnamento.fase.columnName(), fase);
			separator = " and ";
		}
		if(rup != null)
		{
			sql.append(separator)
			   .append(ConfigurazioneAssegnamento.rup.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneAssegnamento.rup.columnName());
			parameters.put(ConfigurazioneAssegnamento.rup.columnName(), rup);
			separator = " and ";
		}
		if(criterioAssegnamento != null)
		{
			sql.append(separator)
			   .append(ConfigurazioneAssegnamento.criterio_assegnamento.getCompleteName())
			   .append(" = :")
			   .append(ConfigurazioneAssegnamento.criterio_assegnamento.columnName());
			parameters.put(ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), criterioAssegnamento.name());
			separator = " and ";
		}
	}
	
}