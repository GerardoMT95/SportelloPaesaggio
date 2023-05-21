package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.text.SimpleDateFormat;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.SchemaRicercaDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;

public class JasperFascicoloDTO {
	
	String codiceFascicolo;
	String descrizione;
	String tipologiaIntervento;
	String comune;
	String responsabileProcedimento;
	String numeroProvvedimento;
	String dataProvvedimento;
	String esito;
	String statoRegistrazione;
	String esitoVerifica;
	
	
	public JasperFascicoloDTO() { }
	
	public JasperFascicoloDTO(FascicoloPublicDto dto, SchemaRicercaDTO permessi) { 
		
		this.codiceFascicolo 		  = dto.getCodice()               !=null ? dto.getCodice() 							  : "";
		this.descrizione 			  = dto.getOggettoIntervento()	  !=null ? dto.getOggettoIntervento() 				  : "";
		this.tipologiaIntervento 	  = dto.getTipoProcedimento()     !=null ? dto.getTipoProcedimento().getTextValue()   : "";					
		this.responsabileProcedimento = dto.getRup()                  !=null ? dto.getRup()								  : "";
		this.numeroProvvedimento 	  = dto.getNumeroProvvedimento()  !=null ? dto.getNumeroProvvedimento() 			  : "";
		this.esito 					  = dto.getEsito()                !=null ? dto.getEsito().getTextValue()			  : "";
		this.statoRegistrazione 	  = dto.getStatoRegistrazione()   !=null ? dto.getStatoRegistrazione().getTextValue() : "";
		this.esitoVerifica 			  = dto.getEsitoVerifica()        !=null ? dto.getEsitoVerifica().getTextValue() 	  : "";
		this.comune 				  = dto.getUfficio()              !=null ? dto.getUfficio()							  : "";
		
		if (permessi.isRegistrazione()==false)
			this.statoRegistrazione = "-";
		if (permessi.isVerifica()==false)
			this.esitoVerifica = "-";
		
		String tempo = "";
		if (dto.getDataRilascioAutorizzazione() == null) {
			this.dataProvvedimento = "";
		} else {
			SimpleDateFormat REPORT_DATE_FORMAT_OUTPUT = new SimpleDateFormat("dd/MM/yyyy");
			tempo = REPORT_DATE_FORMAT_OUTPUT.format(dto.getDataRilascioAutorizzazione());
			this.dataProvvedimento = tempo;
		}
	}
	
	
	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}
	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}
	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}
	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public String getDataProvvedimento() {
		return dataProvvedimento;
	}
	public void setDataProvvedimento(String dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getStatoRegistrazione() {
		return statoRegistrazione;
	}
	public void setStatoRegistrazione(String statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}
	public String getEsitoVerifica() {
		return esitoVerifica;
	}
	public void setEsitoVerifica(String esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}
	
}