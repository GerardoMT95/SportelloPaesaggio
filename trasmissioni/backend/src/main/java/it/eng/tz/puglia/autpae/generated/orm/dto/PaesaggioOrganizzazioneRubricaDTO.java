package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table common.paesaggio_organizzazione_rubrica
 */
public class PaesaggioOrganizzazioneRubricaDTO implements Serializable{

    private static final long serialVersionUID = 2914120174L;
    //COLUMN id
    private long id;
    //COLUMN paesaggio_organizzazione_id
    private int paesaggioOrganizzazioneId;
    //COLUMN codice_applicazione
    private String codiceApplicazione;
    //COLUMN nome
    private String nome;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN data_creazione
    private Timestamp dataCreazione;
    //COLUMN data_modifica
    private Timestamp dataModifica;
    //COLUMN username_creazione
    private String usernameCreazione;
    //COLUMN username_modifica
    private String usernameModifica;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(int paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public String getCodiceApplicazione(){
        return this.codiceApplicazione;
    }

    public void setCodiceApplicazione(String codiceApplicazione){
        this.codiceApplicazione = codiceApplicazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public Timestamp getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public Timestamp getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(Timestamp dataModifica){
        this.dataModifica = dataModifica;
    }

    public String getUsernameCreazione(){
        return this.usernameCreazione;
    }

    public void setUsernameCreazione(String usernameCreazione){
        this.usernameCreazione = usernameCreazione;
    }

    public String getUsernameModifica(){
        return this.usernameModifica;
    }

    public void setUsernameModifica(String usernameModifica){
        this.usernameModifica = usernameModifica;
    }


}
