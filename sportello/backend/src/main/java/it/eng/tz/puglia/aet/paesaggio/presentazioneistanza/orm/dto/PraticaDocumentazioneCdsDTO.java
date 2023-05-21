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
 * DTO for table presentazione_istanza.pratica_documentazione_cds
 */
public class PraticaDocumentazioneCdsDTO implements Serializable{

    private static final long serialVersionUID = 7240441636L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN id_tipo
    private int idTipo;
    //COLUMN id_documento_cds
    private long idDocumentoCds;
    //COLUMN user_create
    private String userCreate;
    //COLUMN date_create
    private Timestamp dateCreate;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }

    public int getIdTipo(){
        return this.idTipo;
    }

    public void setIdTipo(int idTipo){
        this.idTipo = idTipo;
    }

    public long getIdDocumentoCds(){
        return this.idDocumentoCds;
    }

    public void setIdDocumentoCds(long idDocumentoCds){
        this.idDocumentoCds = idDocumentoCds;
    }

    public String getUserCreate(){
        return this.userCreate;
    }

    public void setUserCreate(String userCreate){
        this.userCreate = userCreate;
    }

    public Timestamp getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate){
        this.dateCreate = dateCreate;
    }


}
