package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.dto.PermessiCampionamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;

public class ConfigurazioneCampionamentoDTO implements Serializable {

	private static final long serialVersionUID = 1962776350364450328L;
	
//	protected Long id;								// è inutile perchè la configurazione è unica
	protected boolean campionamentoAttivo;
	protected short intervalloCampionamento;
	protected CampionamentoSuccessivo tipoCampionamentoSuccessivo;
	protected short percentualeIstanze;
	protected String giorniNotificaCampionamento;
	protected String giorniNotificaVerifica;
	protected Short intervalloVerifica;
	protected boolean esitoPubblico;
	protected boolean applicaInCorso;
	
	
	public ConfigurazioneCampionamentoDTO() { }
	
	
	public ConfigurazioneCampionamentoDTO(PermessiCampionamentoDTO permessiCampionamento) {

		this.campionamentoAttivo=permessiCampionamento.isCampionamentoAttivo();
		this.intervalloCampionamento=permessiCampionamento.getIntervalloCampionamento();
		this.tipoCampionamentoSuccessivo=permessiCampionamento.getTipoCampionamentoSuccessivo();
		this.percentualeIstanze=permessiCampionamento.getPercentualeIstanze();
		this.giorniNotificaCampionamento=CampionamentoService.shortArrayToString(permessiCampionamento.getShortGiorniNotificaCampionamento());
		this.giorniNotificaVerifica     =CampionamentoService.shortArrayToString(permessiCampionamento.getShortGiorniNotificaVerifica());
		this.intervalloVerifica=permessiCampionamento.getIntervalloVerifica();
		this.esitoPubblico=permessiCampionamento.isEsitoPubblico();
		this.applicaInCorso=permessiCampionamento.isApplicaInCorso();
		
	}


	public boolean isCampionamentoAttivo() {
		return campionamentoAttivo;
	}
	public void setCampionamentoAttivo(boolean campionamentoAttivo) {
		this.campionamentoAttivo = campionamentoAttivo;
	}
	public short getIntervalloCampionamento() {
		return intervalloCampionamento;
	}
	public Short getIntervalloVerifica() {
		return intervalloVerifica;
	}
	public void setIntervalloVerifica(Short intervalloVerifica) {
		this.intervalloVerifica = intervalloVerifica;
	}
	public void setIntervalloCampionamento(short intervalloCampionamento) {
		this.intervalloCampionamento = intervalloCampionamento;
	}
	public CampionamentoSuccessivo getTipoCampionamentoSuccessivo() {
		return tipoCampionamentoSuccessivo;
	}
	public void setTipoCampionamentoSuccessivo(CampionamentoSuccessivo tipoCampionamentoSuccessivo) {
		this.tipoCampionamentoSuccessivo = tipoCampionamentoSuccessivo;
	}
	public short getPercentualeIstanze() {
		return percentualeIstanze;
	}
	public void setPercentualeIstanze(short percentualeIstanze) {
		this.percentualeIstanze = percentualeIstanze;
	}
	public String getGiorniNotificaCampionamento() {
		return giorniNotificaCampionamento;
	}
	public void setGiorniNotificaCampionamento(String giorniNotificaCampionamento) {
		this.giorniNotificaCampionamento = giorniNotificaCampionamento;
	}
	public String getGiorniNotificaVerifica() {
		return giorniNotificaVerifica;
	}
	public void setGiorniNotificaVerifica(String giorniNotificaVerifica) {
		this.giorniNotificaVerifica = giorniNotificaVerifica;
	}
	public boolean isEsitoPubblico() {
		return esitoPubblico;
	}
	public void setEsitoPubblico(boolean esitoPubblico) {
		this.esitoPubblico = esitoPubblico;
	}
	public boolean isApplicaInCorso() {
		return applicaInCorso;
	}
	public void setApplicaInCorso(boolean applicaInCorso) {
		this.applicaInCorso = applicaInCorso;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (applicaInCorso ? 1231 : 1237);
		result = prime * result + (campionamentoAttivo ? 1231 : 1237);
		result = prime * result + (esitoPubblico ? 1231 : 1237);
		result = prime * result + ((giorniNotificaCampionamento == null) ? 0 : giorniNotificaCampionamento.hashCode());
		result = prime * result + ((giorniNotificaVerifica == null) ? 0 : giorniNotificaVerifica.hashCode());
		result = prime * result + intervalloCampionamento;
		result = prime * result + ((intervalloVerifica == null) ? 0 : intervalloVerifica.hashCode());
		result = prime * result + percentualeIstanze;
		result = prime * result + ((tipoCampionamentoSuccessivo == null) ? 0 : tipoCampionamentoSuccessivo.hashCode());
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
		ConfigurazioneCampionamentoDTO other = (ConfigurazioneCampionamentoDTO) obj;
		if (applicaInCorso != other.applicaInCorso)
			return false;
		if (campionamentoAttivo != other.campionamentoAttivo)
			return false;
		if (esitoPubblico != other.esitoPubblico)
			return false;
		if (giorniNotificaCampionamento == null) {
			if (other.giorniNotificaCampionamento != null)
				return false;
		} else if (!giorniNotificaCampionamento.equals(other.giorniNotificaCampionamento))
			return false;
		if (giorniNotificaVerifica == null) {
			if (other.giorniNotificaVerifica != null)
				return false;
		} else if (!giorniNotificaVerifica.equals(other.giorniNotificaVerifica))
			return false;
		if (intervalloCampionamento != other.intervalloCampionamento)
			return false;
		if (intervalloVerifica == null) {
			if (other.intervalloVerifica != null)
				return false;
		} else if (!intervalloVerifica.equals(other.intervalloVerifica))
			return false;
		if (percentualeIstanze != other.percentualeIstanze)
			return false;
		if (tipoCampionamentoSuccessivo != other.tipoCampionamentoSuccessivo)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ConfigurazioneCampionamentoDTO [campionamentoAttivo=" + campionamentoAttivo
				+ ", intervalloCampionamento=" + intervalloCampionamento + ", tipoCampionamentoSuccessivo="
				+ tipoCampionamentoSuccessivo + ", percentualeIstanze=" + percentualeIstanze
				+ ", giorniNotificaCampionamento=" + giorniNotificaCampionamento + ", giorniNotificaVerifica="
				+ giorniNotificaVerifica + ", intervalloVerifica=" + intervalloVerifica + ", esitoPubblico="
				+ esitoPubblico + ", applicaInCorso=" + applicaInCorso + "]";
	}
	
}
