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
 * DTO for table common.utente
 */
public class UtenteDTO implements Serializable{

    private static final long serialVersionUID = 6986056324L;
    //COLUMN id
    private int id;
    //COLUMN username
    private String username;
    //COLUMN nome
    private String nome;
    //COLUMN cognome
    private String cognome;
    //COLUMN cf
    private String cf;
    //COLUMN data_richiesta_registrazione
    private Timestamp dataRichiestaRegistrazione;
    //COLUMN data_conferma_registrazione
    private Timestamp dataConfermaRegistrazione;
    //COLUMN id_stato_registrazione
    private Integer idStatoRegistrazione;
    //COLUMN note
    private String note;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCognome(){
        return this.cognome;
    }

    public void setCognome(String cognome){
        this.cognome = cognome;
    }

    public String getCf(){
        return this.cf;
    }

    public void setCf(String cf){
        this.cf = cf;
    }

    public Timestamp getDataRichiestaRegistrazione(){
        return this.dataRichiestaRegistrazione;
    }

    public void setDataRichiestaRegistrazione(Timestamp dataRichiestaRegistrazione){
        this.dataRichiestaRegistrazione = dataRichiestaRegistrazione;
    }

    public Timestamp getDataConfermaRegistrazione(){
        return this.dataConfermaRegistrazione;
    }

    public void setDataConfermaRegistrazione(Timestamp dataConfermaRegistrazione){
        this.dataConfermaRegistrazione = dataConfermaRegistrazione;
    }

    public Integer getIdStatoRegistrazione(){
        return this.idStatoRegistrazione;
    }

    public void setIdStatoRegistrazione(Integer idStatoRegistrazione){
        this.idStatoRegistrazione = idStatoRegistrazione;
    }

    public String getNote(){
        return this.note;
    }

    public void setNote(String note){
        this.note = note;
    }


}
