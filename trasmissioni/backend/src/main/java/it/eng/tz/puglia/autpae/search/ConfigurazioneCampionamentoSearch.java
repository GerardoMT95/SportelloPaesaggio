package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCampionamento;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table public.configurazione_campionamento
 */
public class ConfigurazioneCampionamentoSearch extends WhereClauseGenerator<ConfigurazioneCampionamento> {

	private static final long serialVersionUID = 854634266887767564L;

//	private Long id;												// è inutile perchè la configurazione è unica
	private Boolean campionamentoAttivo;
	private Short intervalloCampionamento;
	private CampionamentoSuccessivo tipoCampionamentoSuccessivo;
	private Short percentualeIstanze;
//	private String giorniNotificaCampionamento;						// da abilitare (e implementare una search sensata) SOLO se necessario
//	private String giorniNotificaVerifica;							// da abilitare (e implementare una search sensata) SOLO se necessario
	private Short intervalloVerifica;
	private Boolean esitoPubblico;
	private Boolean applicaInCorso;
	
	
	public ConfigurazioneCampionamentoSearch() { }
	
	
	public Boolean getCampionamentoAttivo() {
		return campionamentoAttivo;
	}

	public void setCampionamentoAttivo(Boolean campionamentoAttivo) {
		this.campionamentoAttivo = campionamentoAttivo;
	}

	public Short getIntervalloCampionamento() {
		return intervalloCampionamento;
	}

	public void setIntervalloCampionamento(Short intervalloCampionamento) {
		this.intervalloCampionamento = intervalloCampionamento;
	}

	public CampionamentoSuccessivo getTipoCampionamentoSuccessivo() {
		return tipoCampionamentoSuccessivo;
	}

	public void setTipoCampionamentoSuccessivo(CampionamentoSuccessivo tipoCampionamentoSuccessivo) {
		this.tipoCampionamentoSuccessivo = tipoCampionamentoSuccessivo;
	}

	public Short getPercentualeIstanze() {
		return percentualeIstanze;
	}

	public void setPercentualeIstanze(Short percentualeIstanze) {
		this.percentualeIstanze = percentualeIstanze;
	}

//	public String getGiorniNotificaCampionamento() {
//		return giorniNotificaCampionamento;
//	}
//
//	public void setGiorniNotificaCampionamento(String giorniNotificaCampionamento) {
//		this.giorniNotificaCampionamento = giorniNotificaCampionamento;
//	}
//
//	public String getGiorniNotificaVerifica() {
//		return giorniNotificaVerifica;
//	}
//
//	public void setGiorniNotificaVerifica(String giorniNotificaVerifica) {
//		this.giorniNotificaVerifica = giorniNotificaVerifica;
//	}

	public Short getIntervalloVerifica() {
		return intervalloVerifica;
	}

	public void setIntervalloVerifica(Short intervalloVerifica) {
		this.intervalloVerifica = intervalloVerifica;
	}

	public Boolean getEsitoPubblico() {
		return esitoPubblico;
	}

	public void setEsitoPubblico(Boolean esitoPubblico) {
		this.esitoPubblico = esitoPubblico;
	}

	public Boolean getApplicaInCorso() {
		return applicaInCorso;
	}

	public void setApplicaInCorso(Boolean applicaInCorso) {
		this.applicaInCorso = applicaInCorso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipoCampionamentoSuccessivo == null) ? 0 : tipoCampionamentoSuccessivo.hashCode());
		result = prime * result + ((applicaInCorso == null) ? 0 : applicaInCorso.hashCode());
		result = prime * result + ((campionamentoAttivo == null) ? 0 : campionamentoAttivo.hashCode());
		result = prime * result + ((esitoPubblico == null) ? 0 : esitoPubblico.hashCode());
	//	result = prime * result + ((giorniNotificaCampionamento == null) ? 0 : giorniNotificaCampionamento.hashCode());
	//	result = prime * result + ((giorniNotificaVerifica == null) ? 0 : giorniNotificaVerifica.hashCode());
		result = prime * result + ((intervalloCampionamento == null) ? 0 : intervalloCampionamento.hashCode());
		result = prime * result + ((intervalloVerifica == null) ? 0 : intervalloVerifica.hashCode());
		result = prime * result + ((percentualeIstanze == null) ? 0 : percentualeIstanze.hashCode());
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
		ConfigurazioneCampionamentoSearch other = (ConfigurazioneCampionamentoSearch) obj;
		if (tipoCampionamentoSuccessivo != other.tipoCampionamentoSuccessivo)
			return false;
		if (applicaInCorso == null) {
			if (other.applicaInCorso != null)
				return false;
		} else if (!applicaInCorso.equals(other.applicaInCorso))
			return false;
		if (campionamentoAttivo == null) {
			if (other.campionamentoAttivo != null)
				return false;
		} else if (!campionamentoAttivo.equals(other.campionamentoAttivo))
			return false;
		if (esitoPubblico == null) {
			if (other.esitoPubblico != null)
				return false;
		} else if (!esitoPubblico.equals(other.esitoPubblico))
			return false;
//		if (giorniNotificaCampionamento == null) {
//			if (other.giorniNotificaCampionamento != null)
//				return false;
//		} else if (!giorniNotificaCampionamento.equals(other.giorniNotificaCampionamento))
//			return false;
//		if (giorniNotificaVerifica == null) {
//			if (other.giorniNotificaVerifica != null)
//				return false;
//		} else if (!giorniNotificaVerifica.equals(other.giorniNotificaVerifica))
//			return false;
		if (intervalloCampionamento == null) {
			if (other.intervalloCampionamento != null)
				return false;
		} else if (!intervalloCampionamento.equals(other.intervalloCampionamento))
			return false;
		if (intervalloVerifica == null) {
			if (other.intervalloVerifica != null)
				return false;
		} else if (!intervalloVerifica.equals(other.intervalloVerifica))
			return false;
		if (percentualeIstanze == null) {
			if (other.percentualeIstanze != null)
				return false;
		} else if (!percentualeIstanze.equals(other.percentualeIstanze))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConfigurazioneCampionamentoSearch [campionamentoAttivo=" + campionamentoAttivo
				+ ", intervalloCampionamento=" + intervalloCampionamento + ", tipoCampionamentoSuccessivo="
				+ tipoCampionamentoSuccessivo + ", percentualeIstanze=" + percentualeIstanze
		//		+ ", giorniNotificaCampionamento=" + giorniNotificaCampionamento + ", giorniNotificaVerifica="+ giorniNotificaVerifica 
				+ ", intervalloVerifica=" + intervalloVerifica + ", esitoPubblico="
				+ esitoPubblico + ", applicaInCorso=" + applicaInCorso + "]";
	}

	@Override
	protected void generateWhereCriteria() {
		String separatore = " where ";
		if(campionamentoAttivo != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.campionamento_attivo.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.campionamento_attivo.columnName());
			parameters.put(ConfigurazioneCampionamento.campionamento_attivo.columnName(), campionamentoAttivo);
			separatore = " and ";
		}
		if(intervalloCampionamento != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.intervallo_campionamento.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.intervallo_campionamento.columnName());
			parameters.put(ConfigurazioneCampionamento.intervallo_campionamento.columnName(), intervalloCampionamento);
			separatore = " and ";
		}
		if(intervalloVerifica != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.intervallo_verifica.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.intervallo_verifica.columnName());
			parameters.put(ConfigurazioneCampionamento.intervallo_verifica.columnName(), intervalloVerifica);
			separatore = " and ";
		}
		if(tipoCampionamentoSuccessivo != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.tipo_campionamento_successivo.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName());
			parameters.put(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName(), tipoCampionamentoSuccessivo.name());
			separatore = " and ";
		}
		if(percentualeIstanze != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.percentuale_istanze.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.percentuale_istanze.columnName());
			parameters.put(ConfigurazioneCampionamento.percentuale_istanze.columnName(), percentualeIstanze);
			separatore = " and ";
		}
		if(esitoPubblico != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.esito_pubblico.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.esito_pubblico.columnName());
			parameters.put(ConfigurazioneCampionamento.esito_pubblico.columnName(), esitoPubblico);
			separatore = " and ";
		}
		if(applicaInCorso != null)
		{
			sql.append(separatore)
			.append(ConfigurazioneCampionamento.applica_in_corso.getCompleteName())
			.append(" = :")
			.append(ConfigurazioneCampionamento.applica_in_corso.columnName());
			parameters.put(ConfigurazioneCampionamento.applica_in_corso.columnName(), applicaInCorso);
			separatore = " and ";
		}
//		if(!StringUtil.isEmpty(giorniNotificaCampionamento))
//		{
//			sql.append(separatore)
//			.append(ConfigurazioneCampionamento.giorni_notifica_campionamento.getCompleteName())
//			.append(" = :")
//			.append(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName());
//			parameters.put(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName(), giorniNotificaCampionamento);
//			separatore = " and ";
//		}
//		if(!StringUtil.isEmpty(giorniNotificaVerifica))
//		{
//			sql.append(separatore)
//			.append(ConfigurazioneCampionamento.giorni_notifica_verifica.getCompleteName())
//			.append(" = :")
//			.append(ConfigurazioneCampionamento.giorni_notifica_verifica.columnName());
//			parameters.put(ConfigurazioneCampionamento.giorni_notifica_verifica.columnName(), giorniNotificaVerifica);
//			separatore = " and ";
//		}
	}

}
