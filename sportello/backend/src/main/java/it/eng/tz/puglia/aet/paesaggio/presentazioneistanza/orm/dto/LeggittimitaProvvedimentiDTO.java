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
 * DTO for table presentazione_istanza.leggittimita_provvedimenti
 */
public class LeggittimitaProvvedimentiDTO implements Serializable{

    private static final long serialVersionUID = 418341926L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN id
    private long id;
    //COLUMN leg_provv_denominazione
    private String legProvvDenominazione;
    //COLUMN leg_provv_rilasciato_da
    private String legProvvRilasciatoDa;
    //COLUMN leg_provv_num_protocollo
    private String legProvvNumProtocollo;
    //COLUMN leg_provv_data_rilascio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date legProvvDataRilascio;
    //COLUMN leg_provv_intestatario
    private String legProvvIntestatario;

    
    public LeggittimitaProvvedimentiDTO() { }
    
    public LeggittimitaProvvedimentiDTO(TipologiaDettaglioDto tipologiaDettaglio, UUID praticaId, long id) 
    {
    	this.id = id;
    	this.praticaId = praticaId;
    	this.legProvvDenominazione = tipologiaDettaglio.getTipologia();
    	this.legProvvRilasciatoDa  = tipologiaDettaglio.getRialisciatoDa();
    	this.legProvvNumProtocollo = tipologiaDettaglio.getNoProtocollo();
    	this.legProvvDataRilascio  = tipologiaDettaglio.getDataRilascio();
    	this.legProvvIntestatario  = tipologiaDettaglio.getIntestinario();
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

    public String getLegProvvDenominazione(){
        return this.legProvvDenominazione;
    }

    public void setLegProvvDenominazione(String legProvvDenominazione){
        this.legProvvDenominazione = legProvvDenominazione;
    }

    public String getLegProvvRilasciatoDa(){
        return this.legProvvRilasciatoDa;
    }

    public void setLegProvvRilasciatoDa(String legProvvRilasciatoDa){
        this.legProvvRilasciatoDa = legProvvRilasciatoDa;
    }

    public String getLegProvvNumProtocollo(){
        return this.legProvvNumProtocollo;
    }

    public void setLegProvvNumProtocollo(String legProvvNumProtocollo){
        this.legProvvNumProtocollo = legProvvNumProtocollo;
    }

    public Date getLegProvvDataRilascio(){
        return this.legProvvDataRilascio;
    }

    public void setLegProvvDataRilascio(Date legProvvDataRilascio){
        this.legProvvDataRilascio = legProvvDataRilascio;
    }

    public String getLegProvvIntestatario(){
        return this.legProvvIntestatario;
    }

    public void setLegProvvIntestatario(String legProvvIntestatario){
        this.legProvvIntestatario = legProvvIntestatario;
    }


}
