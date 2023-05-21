package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Campionamento;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;

/**
 * Search for table public.campionamento
 */
public class CampionamentoSearch extends WhereClauseGenerator<Campionamento> {

	private static final long serialVersionUID = 725546479139366618L;
	
	private Long id;
	private Boolean attivo;
	private Short intervalloCamp;
	private CampionamentoSuccessivo tipoSuccessivo;
	private Short percentuale;
//	private String notificaCamp;		    // da abilitare (e implementare una search sensata) SOLO se necessario
//	private String notificaVer;				// da abilitare (e implementare una search sensata) SOLO se necessario
	private Short  intervalloVer;
	private Boolean esitoPubb;
	private Boolean customized;				// TODO: credo si possa riuscire ad eliminarlo
	private Date dataInizioDa;
	private Date dataInizioA;
	private Date dataCampionaturaDa;
	private Date dataCampionaturaA;	
	
	
	public CampionamentoSearch() { }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Short getIntervalloCamp() {
		return intervalloCamp;
	}

	public void setIntervalloCamp(Short intervalloCamp) {
		this.intervalloCamp = intervalloCamp;
	}

	public CampionamentoSuccessivo getTipoSuccessivo() {
		return tipoSuccessivo;
	}

	public void setTipoSuccessivo(CampionamentoSuccessivo tipoSuccessivo) {
		this.tipoSuccessivo = tipoSuccessivo;
	}

	public Short getPercentuale() {
		return percentuale;
	}

	public void setPercentuale(Short percentuale) {
		this.percentuale = percentuale;
	}

//	public String getNotificaCamp() {
//		return notificaCamp;
//	}
//
//	public void setNotificaCamp(String notificaCamp) {
//		this.notificaCamp = notificaCamp;
//	}
//
//	public String getNotificaVer() {
//		return notificaVer;
//	}
//
//	public void setNotificaVer(String notificaVer) {
//		this.notificaVer = notificaVer;
//	}

	public Short getIntervalloVer() {
		return intervalloVer;
	}

	public void setIntervalloVer(Short intervalloVer) {
		this.intervalloVer = intervalloVer;
	}

	public Boolean getEsitoPubb() {
		return esitoPubb;
	}

	public void setEsitoPubb(Boolean esitoPubb) {
		this.esitoPubb = esitoPubb;
	}

	public Boolean getCustomized() {
		return customized;
	}

	public void setCustomized(Boolean customized) {
		this.customized = customized;
	}

	public Date getDataInizioDa() {
		return dataInizioDa;
	}

	public void setDataInizioDa(Date dataInizioDa) {
		this.dataInizioDa = dataInizioDa;
	}

	public Date getDataInizioA() {
		return dataInizioA;
	}

	public void setDataInizioA(Date dataInizioA) {
		this.dataInizioA = dataInizioA;
	}

	public Date getDataCampionaturaDa() {
		return dataCampionaturaDa;
	}

	public void setDataCampionaturaDa(Date dataCampionaturaDa) {
		this.dataCampionaturaDa = dataCampionaturaDa;
	}

	public Date getDataCampionaturaA() {
		return dataCampionaturaA;
	}

	public void setDataCampionaturaA(Date dataCampionaturaA) {
		this.dataCampionaturaA = dataCampionaturaA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attivo == null) ? 0 : attivo.hashCode());
		result = prime * result + ((customized == null) ? 0 : customized.hashCode());
		result = prime * result + ((dataCampionaturaA == null) ? 0 : dataCampionaturaA.hashCode());
		result = prime * result + ((dataCampionaturaDa == null) ? 0 : dataCampionaturaDa.hashCode());
		result = prime * result + ((dataInizioA == null) ? 0 : dataInizioA.hashCode());
		result = prime * result + ((dataInizioDa == null) ? 0 : dataInizioDa.hashCode());
		result = prime * result + ((esitoPubb == null) ? 0 : esitoPubb.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((intervalloCamp == null) ? 0 : intervalloCamp.hashCode());
		result = prime * result + ((intervalloVer == null) ? 0 : intervalloVer.hashCode());
	//	result = prime * result + ((notificaCamp == null) ? 0 : notificaCamp.hashCode());
	//	result = prime * result + ((notificaVer == null) ? 0 : notificaVer.hashCode());
		result = prime * result + ((percentuale == null) ? 0 : percentuale.hashCode());
		result = prime * result + ((tipoSuccessivo == null) ? 0 : tipoSuccessivo.hashCode());
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
		CampionamentoSearch other = (CampionamentoSearch) obj;
		if (attivo == null) {
			if (other.attivo != null)
				return false;
		} else if (!attivo.equals(other.attivo))
			return false;
		if (customized == null) {
			if (other.customized != null)
				return false;
		} else if (!customized.equals(other.customized))
			return false;
		if (dataCampionaturaA == null) {
			if (other.dataCampionaturaA != null)
				return false;
		} else if (!dataCampionaturaA.equals(other.dataCampionaturaA))
			return false;
		if (dataCampionaturaDa == null) {
			if (other.dataCampionaturaDa != null)
				return false;
		} else if (!dataCampionaturaDa.equals(other.dataCampionaturaDa))
			return false;
		if (dataInizioA == null) {
			if (other.dataInizioA != null)
				return false;
		} else if (!dataInizioA.equals(other.dataInizioA))
			return false;
		if (dataInizioDa == null) {
			if (other.dataInizioDa != null)
				return false;
		} else if (!dataInizioDa.equals(other.dataInizioDa))
			return false;
		if (esitoPubb == null) {
			if (other.esitoPubb != null)
				return false;
		} else if (!esitoPubb.equals(other.esitoPubb))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intervalloCamp == null) {
			if (other.intervalloCamp != null)
				return false;
		} else if (!intervalloCamp.equals(other.intervalloCamp))
			return false;
		if (intervalloVer == null) {
			if (other.intervalloVer != null)
				return false;
		} else if (!intervalloVer.equals(other.intervalloVer))
			return false;
//		if (notificaCamp == null) {
//			if (other.notificaCamp != null)
//				return false;
//		} else if (!notificaCamp.equals(other.notificaCamp))
//			return false;
//		if (notificaVer == null) {
//			if (other.notificaVer != null)
//				return false;
//		} else if (!notificaVer.equals(other.notificaVer))
//			return false;
		if (percentuale == null) {
			if (other.percentuale != null)
				return false;
		} else if (!percentuale.equals(other.percentuale))
			return false;
		if (tipoSuccessivo != other.tipoSuccessivo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CampionamentoSearch [id=" + id + ", attivo=" + attivo + ", intervalloCamp=" + intervalloCamp
				+ ", tipoSuccessivo=" + tipoSuccessivo + ", percentuale=" + percentuale 
		//		+ ", notificaCamp=" + notificaCamp + ", notificaVer=" + notificaVer 
				+ ", intervalloVer=" + intervalloVer + ", esitoPubb="
				+ esitoPubb + ", customized=" + customized + ", dataInizioDa=" + dataInizioDa + ", dataInizioA="
				+ dataInizioA + ", dataCampionaturaDa=" + dataCampionaturaDa + ", dataCampionaturaA=" + dataCampionaturaA + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Campionamento.id.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.id.columnName());
			parameters.put(Campionamento.id.columnName(), id);
			separator = " and ";
		}
		if(dataInizioDa != null && dataInizioA != null)	
		{
			sql.append(separator)
				.append(Campionamento.data_inizio.getCompleteName())
				.append(" BETWEEN :")
				.append(Campionamento.data_inizio.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Campionamento.data_inizio.columnName())
				.append("_A");
			parameters.put(Campionamento.data_inizio.columnName() + "_DA", dataInizioDa);
			parameters.put(Campionamento.data_inizio.columnName() + "_A" , DateUtil.endOfDay(dataInizioA));
			separator = " and ";
		}
		else if(dataInizioDa != null)	
		{
			sql.append(separator)
				.append(Campionamento.data_inizio.getCompleteName())
				.append(" >= :")
				.append(Campionamento.data_inizio.columnName());
			parameters.put(Campionamento.data_inizio.columnName(), dataInizioDa);
			separator = " and ";
		}
		else if(dataInizioA != null)
		{
			sql.append(separator)
				.append(Campionamento.data_inizio.getCompleteName())
				.append(" <= :")
				.append(Campionamento.data_inizio.columnName());
			parameters.put(Campionamento.data_inizio.columnName(), DateUtil.endOfDay(dataInizioA));
			separator = " and ";
		}
		if(dataCampionaturaDa != null && dataCampionaturaA != null)	
		{
			sql.append(separator)
				.append(Campionamento.data_campionatura.getCompleteName())
				.append(" BETWEEN :")
				.append(Campionamento.data_campionatura.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Campionamento.data_campionatura.columnName())
				.append("_A");
			parameters.put(Campionamento.data_campionatura.columnName() + "_DA", dataCampionaturaDa);
			parameters.put(Campionamento.data_campionatura.columnName() + "_A" , DateUtil.endOfDay(dataCampionaturaA));
			separator = " and ";
		}
		else if(dataCampionaturaDa != null)	
		{
			sql.append(separator)
				.append(Campionamento.data_campionatura.getCompleteName())
				.append(" >= :")
				.append(Campionamento.data_campionatura.columnName());
			parameters.put(Campionamento.data_campionatura.columnName(), dataCampionaturaDa);
			separator = " and ";
		}
		else if(dataCampionaturaA != null)
		{
			sql.append(separator)
				.append(Campionamento.data_campionatura.getCompleteName())
				.append(" <= :")
				.append(Campionamento.data_campionatura.columnName());
			parameters.put(Campionamento.data_campionatura.columnName(), DateUtil.endOfDay(dataCampionaturaA));
			separator = " and ";
		}
		if(percentuale != null)
		{
			sql.append(separator)
			   .append(Campionamento.percentuale.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.percentuale.columnName());
			parameters.put(Campionamento.percentuale.columnName(), percentuale);
			separator = " and ";
		}
		if(intervalloCamp != null)
		{
			sql.append(separator)
			   .append(Campionamento.intervallo_camp.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.intervallo_camp.columnName());
			parameters.put(Campionamento.intervallo_camp.columnName(), intervalloCamp);
			separator = " and ";
		}
		if(intervalloVer != null)
		{
			sql.append(separator)
			   .append(Campionamento.intervallo_ver.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.intervallo_ver.columnName());
			parameters.put(Campionamento.intervallo_ver.columnName(), intervalloVer);
			separator = " and ";
		}
		if(attivo != null)
		{
			sql.append(separator)
			   .append(Campionamento.attivo.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.attivo.columnName());
			parameters.put(Campionamento.attivo.columnName(), attivo);
			separator = " and ";
		}
		if(customized != null)
		{
			sql.append(separator)
			   .append(Campionamento.customized.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.customized.columnName());
			parameters.put(Campionamento.customized.columnName(), customized);
			separator = " and ";
		}
		if(esitoPubb != null)
		{
			sql.append(separator)
			   .append(Campionamento.esito_pubb.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.esito_pubb.columnName());
			parameters.put(Campionamento.esito_pubb.columnName(), esitoPubb);
			separator = " and ";
		}
		if(tipoSuccessivo != null)
		{
			sql.append(separator)
			   .append(Campionamento.tipo_successivo.getCompleteName())
			   .append(" = :")
			   .append(Campionamento.tipo_successivo.columnName());
			parameters.put(Campionamento.tipo_successivo.columnName(), tipoSuccessivo.name());
			separator = " and ";
		}
//		if(!StringUtil.isEmpty(notificaCamp))
//		{
//			sql.append(separator)
//			   .append(Campionamento.notifica_camp.getCompleteName())
//			   .append(" = :")
//			   .append(Campionamento.notifica_camp.columnName());
//			parameters.put(Campionamento.notifica_camp.columnName(), notificaCamp);
//			separator = " and ";
//		}
//		if(!StringUtil.isEmpty(notificaVer))
//		{
//			sql.append(separator)
//			   .append(Campionamento.notifica_ver.getCompleteName())
//			   .append(" = :")
//			   .append(Campionamento.notifica_ver.columnName());
//			parameters.put(Campionamento.notifica_ver.columnName(), notificaVer);
//			separator = " and ";
//		}
	}

}
