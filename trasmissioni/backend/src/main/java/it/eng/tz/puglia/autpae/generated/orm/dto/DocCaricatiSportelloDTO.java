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
 * DTO for table pareri.doc_caricati_sportello
 */
public class DocCaricatiSportelloDTO implements Serializable{

    private static final long serialVersionUID = 6956882750L;
    //COLUMN id
    private long id;
    //COLUMN id_doc_caricato
    private UUID idDocCaricato;
    //COLUMN date_insert
    private Timestamp dateInsert;
    //COLUMN user_insert
    private String userInsert;
    //COLUMN deleted
    private Boolean deleted;
    //COLUMN id_fascicolo
    private long idFascicolo;
    //COLUMN date_updated
    private Timestamp dateUpdated;
    //COLUMN user_updated
    private String userUpdated;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public UUID getIdDocCaricato(){
        return this.idDocCaricato;
    }

    public void setIdDocCaricato(UUID idDocCaricato){
        this.idDocCaricato = idDocCaricato;
    }

    public Timestamp getDateInsert(){
        return this.dateInsert;
    }

    public void setDateInsert(Timestamp dateInsert){
        this.dateInsert = dateInsert;
    }

    public String getUserInsert(){
        return this.userInsert;
    }

    public void setUserInsert(String userInsert){
        this.userInsert = userInsert;
    }

    public Boolean getDeleted(){
        return this.deleted;
    }

    public void setDeleted(Boolean deleted){
        this.deleted = deleted;
    }

    public long getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(long idFascicolo){
        this.idFascicolo = idFascicolo;
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


}
