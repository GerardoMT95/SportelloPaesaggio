package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table common.applicazione
 */
public class ApplicazioneDTO implements Serializable{

    private static final long serialVersionUID = 3035946721L;
    //COLUMN id
    private int id;
    //COLUMN nome
    private String nome;
    //COLUMN codice
    private String codice;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }


}
