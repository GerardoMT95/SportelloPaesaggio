package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.pareri_e_atti_assenso
 */
public class PareriEAttiAssensoDTO implements Serializable{

    private static final long serialVersionUID = 6247517393L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN id
    private long id;
    //COLUMN tipologia_atto
    private String tipologiaAtto;
    //COLUMN autorita_rilascio
    private String autoritaRilascio;
    //COLUMN protocollo
    private String protocollo;
    //COLUMN data_rilascio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataRilascio;
    //COLUMN flag_atto_parere
    private String flagAttoParere;
    //COLUMN intestatario_atto
    private String intestatarioAtto;
    
    
    public PareriEAttiAssensoDTO() { }
    
    
    public PareriEAttiAssensoDTO(TipologiaDettaglioDto tipologiaDettaglio, String flagAP, UUID praticaId, long id) 
    {
    	this.id = id;
    	this.praticaId = praticaId;
    	this.flagAttoParere = flagAP;
    	this.tipologiaAtto = tipologiaDettaglio.getTipologia();
    	this.autoritaRilascio  = tipologiaDettaglio.getRialisciatoDa();
    	this.protocollo = tipologiaDettaglio.getNoProtocollo();
    	this.dataRilascio  = tipologiaDettaglio.getDataRilascio();
    	this.intestatarioAtto  = tipologiaDettaglio.getIntestinario();
    }

    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTipologiaAtto(){
        return this.tipologiaAtto;
    }

    public void setTipologiaAtto(String tipologiaAtto){
        this.tipologiaAtto = tipologiaAtto;
    }

    public String getAutoritaRilascio(){
        return this.autoritaRilascio;
    }

    public void setAutoritaRilascio(String autoritaRilascio){
        this.autoritaRilascio = autoritaRilascio;
    }

    public String getProtocollo(){
        return this.protocollo;
    }

    public void setProtocollo(String protocollo){
        this.protocollo = protocollo;
    }

    public Date getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public String getFlagAttoParere(){
        return this.flagAttoParere;
    }

    public void setFlagAttoParere(String flagAttoParere){
        this.flagAttoParere = flagAttoParere;
    }

    public String getIntestatarioAtto(){
        return this.intestatarioAtto;
    }

    public void setIntestatarioAtto(String intestatarioAtto){
        this.intestatarioAtto = intestatarioAtto;
    }


}
