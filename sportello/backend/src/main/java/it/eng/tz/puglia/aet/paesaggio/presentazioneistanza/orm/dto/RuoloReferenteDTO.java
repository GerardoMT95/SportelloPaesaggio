package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.ruolo_referente
 */
public class RuoloReferenteDTO implements Serializable{

    private static final long serialVersionUID = 6318969485L;
    //COLUMN id
    private int id;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN titolarita
    private Boolean titolarita;
    //COLUMN altra_titolarita
    private Boolean altraTitolarita;
    //COLUMN attiva_specifica
    private Boolean attivaSpecifica;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public Boolean getTitolarita(){
        return this.titolarita;
    }

    public void setTitolarita(Boolean titolarita){
        this.titolarita = titolarita;
    }

    public Boolean getAltraTitolarita(){
        return this.altraTitolarita;
    }

    public void setAltraTitolarita(Boolean altraTitolarita){
        this.altraTitolarita = altraTitolarita;
    }

    public Boolean getAttivaSpecifica(){
        return this.attivaSpecifica;
    }

    public void setAttivaSpecifica(Boolean attivaSpecifica){
        this.attivaSpecifica = attivaSpecifica;
    }


}
