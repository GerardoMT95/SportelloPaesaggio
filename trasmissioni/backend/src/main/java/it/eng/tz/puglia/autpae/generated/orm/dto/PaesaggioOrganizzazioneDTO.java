package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table common.paesaggio_organizzazione
 */
public class PaesaggioOrganizzazioneDTO implements Serializable{

    private static final long serialVersionUID = 6313789730L;
    //COLUMN id
    private int id;
    //COLUMN ente_id
    private Integer enteId;
    //COLUMN tipo_organizzazione
    private String tipoOrganizzazione;
    //COLUMN tipo_organizzazione_specifica
    private String tipoOrganizzazioneSpecifica;
    //COLUMN codice_civilia
    private String codiceCivilia;
    //COLUMN riferimento_organizzazione
    private Integer riferimentoOrganizzazione;
    //COLUMN data_creazione
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataCreazione;
    //COLUMN data_termine
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataTermine;
    //COLUMN denominazione
    private String denominazione;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Integer getEnteId(){
        return this.enteId;
    }

    public void setEnteId(Integer enteId){
        this.enteId = enteId;
    }

    public String getTipoOrganizzazione(){
        return this.tipoOrganizzazione;
    }

    public void setTipoOrganizzazione(String tipoOrganizzazione){
        this.tipoOrganizzazione = tipoOrganizzazione;
    }

    public String getTipoOrganizzazioneSpecifica(){
        return this.tipoOrganizzazioneSpecifica;
    }

    public void setTipoOrganizzazioneSpecifica(String tipoOrganizzazioneSpecifica){
        this.tipoOrganizzazioneSpecifica = tipoOrganizzazioneSpecifica;
    }

    public String getCodiceCivilia(){
        return this.codiceCivilia;
    }

    public void setCodiceCivilia(String codiceCivilia){
        this.codiceCivilia = codiceCivilia;
    }

    public Integer getRiferimentoOrganizzazione(){
        return this.riferimentoOrganizzazione;
    }

    public void setRiferimentoOrganizzazione(Integer riferimentoOrganizzazione){
        this.riferimentoOrganizzazione = riferimentoOrganizzazione;
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

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }


}
