package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table presentazione_istanza.rup
 */
public class RupDTO implements Serializable{

    private static final long serialVersionUID = 66702222L;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN username
    private String username;
    //COLUMN denominazione
    private String denominazione;
    //COLUMN data_scadenza
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataScadenza;

    public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }

    public Date getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza){
        this.dataScadenza = dataScadenza;
    }


}
