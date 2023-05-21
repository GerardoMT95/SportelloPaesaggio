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
 * DTO for table presentazione_istanza.leggittimita_titoli
 */
public class LeggittimitaTitoliDTO implements Serializable{

    private static final long serialVersionUID = 9736715853L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN id
    private long id;
    //COLUMN leg_tit_denominazione
    private String legTitDenominazione;
    //COLUMN leg_tit_rilasciato_da
    private String legTitRilasciatoDa;
    //COLUMN leg_tit_num_protocollo
    private String legTitNumProtocollo;
    //COLUMN leg_tit_data_rilascio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date legTitDataRilascio;
    //COLUMN leg_tit_intestatario
    private String legTitIntestatario;
    
    
    public LeggittimitaTitoliDTO() { }
    
    public LeggittimitaTitoliDTO(TipologiaDettaglioDto tipologiaDettaglio, UUID praticaId, long id) 
    {
    	this.id = id;
    	this.praticaId = praticaId;
    	this.legTitDenominazione = tipologiaDettaglio.getTipologia();
    	this.legTitRilasciatoDa  = tipologiaDettaglio.getRialisciatoDa();
    	this.legTitNumProtocollo = tipologiaDettaglio.getNoProtocollo();
    	this.legTitDataRilascio  = tipologiaDettaglio.getDataRilascio();
    	this.legTitIntestatario  = tipologiaDettaglio.getIntestinario();
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

    public String getLegTitDenominazione(){
        return this.legTitDenominazione;
    }

    public void setLegTitDenominazione(String legTitDenominazione){
        this.legTitDenominazione = legTitDenominazione;
    }

    public String getLegTitRilasciatoDa(){
        return this.legTitRilasciatoDa;
    }

    public void setLegTitRilasciatoDa(String legTitRilasciatoDa){
        this.legTitRilasciatoDa = legTitRilasciatoDa;
    }

    public String getLegTitNumProtocollo(){
        return this.legTitNumProtocollo;
    }

    public void setLegTitNumProtocollo(String legTitNumProtocollo){
        this.legTitNumProtocollo = legTitNumProtocollo;
    }

    public Date getLegTitDataRilascio(){
        return this.legTitDataRilascio;
    }

    public void setLegTitDataRilascio(Date legTitDataRilascio){
        this.legTitDataRilascio = legTitDataRilascio;
    }

    public String getLegTitIntestatario(){
        return this.legTitIntestatario;
    }

    public void setLegTitIntestatario(String legTitIntestatario){
        this.legTitIntestatario = legTitIntestatario;
    }


}
