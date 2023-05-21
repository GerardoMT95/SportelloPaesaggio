package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.util.ArrayList;
import java.util.List;

public class JasperRicercaFascicoliDTO {

	List<JasperFascicoloDTO> fascicoloDtoList;
	String dipartimentoRegione;
	String sezioneRegione;
	String indirizzoRegione;
	String pecRegione;
	String telRegione;
	String sezionaleProtocolloRegione;
	String dirigenteRegione;
	
	
	public JasperRicercaFascicoliDTO() {
		this.fascicoloDtoList = new ArrayList<>();
	}
	
	
	public List<JasperFascicoloDTO> getFascicoloDtoList() {
		return fascicoloDtoList;
	}
	public void setFascicoloDtoList(List<JasperFascicoloDTO> fascicoloDtoList) {
		this.fascicoloDtoList = fascicoloDtoList;
	}
	public String getDipartimentoRegione() {
		return dipartimentoRegione;
	}
	public void setDipartimentoRegione(String dipartimentoRegione) {
		this.dipartimentoRegione = dipartimentoRegione;
	}
	public String getSezioneRegione() {
		return sezioneRegione;
	}
	public void setSezioneRegione(String sezioneRegione) {
		this.sezioneRegione = sezioneRegione;
	}
	public String getIndirizzoRegione() {
		return indirizzoRegione;
	}
	public void setIndirizzoRegione(String indirizzoRegione) {
		this.indirizzoRegione = indirizzoRegione;
	}
	public String getPecRegione() {
		return pecRegione;
	}
	public void setPecRegione(String pecRegione) {
		this.pecRegione = pecRegione;
	}
	public String getTelRegione() {
		return telRegione;
	}
	public void setTelRegione(String telRegione) {
		this.telRegione = telRegione;
	}
	public String getSezionaleProtocolloRegione() {
		return sezionaleProtocolloRegione;
	}
	public void setSezionaleProtocolloRegione(String sezionaleProtocolloRegione) {
		this.sezionaleProtocolloRegione = sezionaleProtocolloRegione;
	}
	public String getDirigenteRegione() {
		return dirigenteRegione;
	}
	public void setDirigenteRegione(String dirigenteRegione) {
		this.dirigenteRegione = dirigenteRegione;
	}
	
}