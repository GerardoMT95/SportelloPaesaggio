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
 * DTO for table presentazione_istanza.configurazione_casella_postale
 */
public class ConfigurazioneCasellaPostaleDTO implements Serializable{

    private static final long serialVersionUID = 1641895391L;
    //COLUMN email
    private String email;
    //COLUMN configurazione
    private String configurazione;

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getConfigurazione(){
        return this.configurazione;
    }

    public void setConfigurazione(String configurazione){
        this.configurazione = configurazione;
    }


}
