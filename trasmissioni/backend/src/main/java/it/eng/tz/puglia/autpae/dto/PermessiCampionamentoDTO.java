package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;

public class PermessiCampionamentoDTO extends ConfigurazioneCampionamentoDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	List<ConfigurazionePermessiDTO> permessi;
	ArrayList<Short> shortGiorniNotificaCampionamento;
	ArrayList<Short> shortGiorniNotificaVerifica;

	
	public PermessiCampionamentoDTO() {
		this.permessi = new ArrayList<ConfigurazionePermessiDTO>();
		this.shortGiorniNotificaCampionamento = new ArrayList<Short>();
		this.shortGiorniNotificaVerifica = new ArrayList<Short>();
	}

	public PermessiCampionamentoDTO(List<ConfigurazionePermessiDTO> permessi) {
		this.permessi = permessi;
	}
	
	public PermessiCampionamentoDTO(ConfigurazioneCampionamentoDTO configCampionamento) {
		this.campionamentoAttivo=configCampionamento.isCampionamentoAttivo();
		this.intervalloCampionamento=configCampionamento.getIntervalloCampionamento();
		this.tipoCampionamentoSuccessivo=configCampionamento.getTipoCampionamentoSuccessivo();
		this.percentualeIstanze=configCampionamento.getPercentualeIstanze();
		this.esitoPubblico=configCampionamento.isEsitoPubblico();
		this.applicaInCorso=configCampionamento.isApplicaInCorso();
		this.     giorniNotificaCampionamento=null;
		this.shortGiorniNotificaCampionamento=CampionamentoService.stringToShortArray(configCampionamento.getGiorniNotificaCampionamento());
		this.     giorniNotificaVerifica     =null;
		this.shortGiorniNotificaVerifica     =CampionamentoService.stringToShortArray(configCampionamento.getGiorniNotificaVerifica());
		this.intervalloVerifica=configCampionamento.getIntervalloVerifica();
		this.permessi = new ArrayList<ConfigurazionePermessiDTO>();
	}
	

	public List<ConfigurazionePermessiDTO> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<ConfigurazionePermessiDTO> permessi) {
		this.permessi = permessi;
	}

	public ArrayList<Short> getShortGiorniNotificaCampionamento() {
		return shortGiorniNotificaCampionamento;
	}

	public void setShortGiorniNotificaCampionamento(ArrayList<Short> shortGiorniNotificaCampionamento) {
		this.shortGiorniNotificaCampionamento = shortGiorniNotificaCampionamento;
	}

	public ArrayList<Short> getShortGiorniNotificaVerifica() {
		return shortGiorniNotificaVerifica;
	}

	public void setShortGiorniNotificaVerifica(ArrayList<Short> shortGiorniNotificaVerifica) {
		this.shortGiorniNotificaVerifica = shortGiorniNotificaVerifica;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((permessi == null) ? 0 : permessi.hashCode());
		result = prime * result
				+ ((shortGiorniNotificaCampionamento == null) ? 0 : shortGiorniNotificaCampionamento.hashCode());
		result = prime * result + ((shortGiorniNotificaVerifica == null) ? 0 : shortGiorniNotificaVerifica.hashCode());
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
		PermessiCampionamentoDTO other = (PermessiCampionamentoDTO) obj;
		if (permessi == null) {
			if (other.permessi != null)
				return false;
		} else if (!permessi.equals(other.permessi))
			return false;
		if (shortGiorniNotificaCampionamento == null) {
			if (other.shortGiorniNotificaCampionamento != null)
				return false;
		} else if (!shortGiorniNotificaCampionamento.equals(other.shortGiorniNotificaCampionamento))
			return false;
		if (shortGiorniNotificaVerifica == null) {
			if (other.shortGiorniNotificaVerifica != null)
				return false;
		} else if (!shortGiorniNotificaVerifica.equals(other.shortGiorniNotificaVerifica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PermessiCampionamentoDTO [permessi=" + permessi + ", shortGiorniNotificaCampionamento="
				+ shortGiorniNotificaCampionamento + ", shortGiorniNotificaVerifica=" + shortGiorniNotificaVerifica
				+ "]";
	}
	
}
