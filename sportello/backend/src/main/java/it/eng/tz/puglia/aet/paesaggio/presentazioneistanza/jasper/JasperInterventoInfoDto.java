package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;

public class JasperInterventoInfoDto {
	
	private List<ConfigOptionValue> caratterizzazioneIntervento;
	private List<JasperQualificazioneInterventoDto> qualificazioneIntervento;
	private int tipoProcedimento;
	private String oggettoIntervento;
	private String descrizioneIntervento;
	private String tipologiaDiIntervento;
	private String tipologiaDiInterventoCon;
	private String tipologiaDiInterventoArticoli;
	private Date   tipologiaDiInterventoDataApprovazione;
	private String durata;
	
	
	public JasperInterventoInfoDto() { }
	
	
	public List<ConfigOptionValue> getCaratterizzazioneIntervento() {
		return caratterizzazioneIntervento;
	}
	public void setCaratterizzazioneIntervento(List<ConfigOptionValue> caratterizzazioneIntervento) {
		this.caratterizzazioneIntervento = caratterizzazioneIntervento;
	}
	public List<JasperQualificazioneInterventoDto> getQualificazioneIntervento() {
		return qualificazioneIntervento;
	}
	public void setQualificazioneIntervento(List<JasperQualificazioneInterventoDto> qualificazioneIntervento) {
		this.qualificazioneIntervento = qualificazioneIntervento;
	}
	public int getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(int tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getTipologiaDiInterventoCon() {
		return tipologiaDiInterventoCon;
	}
	public void setTipologiaDiInterventoCon(String tipologiaDiInterventoCon) {
		this.tipologiaDiInterventoCon = tipologiaDiInterventoCon;
	}
	public String getTipologiaDiInterventoArticoli() {
		return tipologiaDiInterventoArticoli;
	}
	public void setTipologiaDiInterventoArticoli(String tipologiaDiInterventoArticoli) {
		this.tipologiaDiInterventoArticoli = tipologiaDiInterventoArticoli;
	}
	public Date getTipologiaDiInterventoDataApprovazione() {
		return tipologiaDiInterventoDataApprovazione;
	}
	public void setTipologiaDiInterventoDataApprovazione(Date tipologiaDiInterventoDataApprovazione) {
		this.tipologiaDiInterventoDataApprovazione = tipologiaDiInterventoDataApprovazione;
	}
	public String getOggettoIntervento() {
		return oggettoIntervento;
	}
	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}
	public String getDescrizioneIntervento() {
		return descrizioneIntervento;
	}
	public void setDescrizioneIntervento(String descrizioneIntervento) {
		this.descrizioneIntervento = descrizioneIntervento;
	}
	public String getTipologiaDiIntervento() {
		return tipologiaDiIntervento;
	}
	public void setTipologiaDiIntervento(String tipologiaDiIntervento) {
		this.tipologiaDiIntervento = tipologiaDiIntervento;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	
	@Override
	public String toString() {
		return "JasperInterventoInfoDto [caratterizzazioneIntervento=" + caratterizzazioneIntervento
				+ ", qualificazioneIntervento=" + qualificazioneIntervento + ", tipoProcedimento=" + tipoProcedimento
				+ ", oggettoIntervento=" + oggettoIntervento + ", descrizioneIntervento=" + descrizioneIntervento
				+ ", tipologiaDiIntervento=" + tipologiaDiIntervento + ", tipologiaDiInterventoCon="
				+ tipologiaDiInterventoCon + ", tipologiaDiInterventoArticoli=" + tipologiaDiInterventoArticoli
				+ ", tipologiaDiInterventoDataApprovazione=" + tipologiaDiInterventoDataApprovazione + ", durata=" + durata + "]";
	}
	
}