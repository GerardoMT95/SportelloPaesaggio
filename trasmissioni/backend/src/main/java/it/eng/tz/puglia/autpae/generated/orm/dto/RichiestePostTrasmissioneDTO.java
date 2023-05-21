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
 * DTO for table autpae.richieste_post_trasmissione
 */
public class RichiestePostTrasmissioneDTO implements Serializable{

    private static final long serialVersionUID = 1542220828L;
    //COLUMN id
    private long id;
    //COLUMN id_fascicolo
    private long idFascicolo;
    //COLUMN motivazione
    private String motivazione;
    //COLUMN stato
    private String stato;
    //COLUMN tipo
    private String tipo;
    //COLUMN date_created
    private Timestamp dateCreated;
    //COLUMN user_created
    private String userCreated;
    //COLUMN date_updated
    private Timestamp dateUpdated;
    //COLUMN user_updated
    private String userUpdated;
    //COLUMN id_corrispondenza
    private Long idCorrispondenza;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(long idFascicolo){
        this.idFascicolo = idFascicolo;
    }

    public String getMotivazione(){
        return this.motivazione;
    }

    public void setMotivazione(String motivazione){
        this.motivazione = motivazione;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public Timestamp getDateCreated(){
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated){
        this.dateCreated = dateCreated;
    }

    public String getUserCreated(){
        return this.userCreated;
    }

    public void setUserCreated(String userCreated){
        this.userCreated = userCreated;
    }

    public Timestamp getDateUpdated(){
        return this.dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated){
        this.dateUpdated = dateUpdated;
    }

    public String getUserUpdated(){
        return this.userUpdated;
    }

    public void setUserUpdated(String userUpdated){
        this.userUpdated = userUpdated;
    }

    public Long getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(Long idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }


}
