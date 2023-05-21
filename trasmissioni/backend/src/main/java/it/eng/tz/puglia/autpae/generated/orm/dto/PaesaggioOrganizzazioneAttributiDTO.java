package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table common.paesaggio_organizzazione_attributi
 */
public class PaesaggioOrganizzazioneAttributiDTO implements Serializable{

    private static final long serialVersionUID = 111937434L;
    //COLUMN id
    private int id;
    //COLUMN applicazione_id
    private int applicazioneId;
    //COLUMN paesaggio_organizzazione_id
    private int paesaggioOrganizzazioneId;
    //COLUMN data_creazione
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataCreazione;
    //COLUMN data_termine
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataTermine;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getApplicazioneId(){
        return this.applicazioneId;
    }

    public void setApplicazioneId(int applicazioneId){
        this.applicazioneId = applicazioneId;
    }

    public int getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(int paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public Date getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public Date getDataTermine(){
        return this.dataTermine;
    }

    public void setDataTermine(Date dataTermine){
        this.dataTermine = dataTermine;
    }


}
