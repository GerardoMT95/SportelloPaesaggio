package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.tipo_documento_identita
 */
public class TipoDocumentoIdentitaDTO implements Serializable{

    private static final long serialVersionUID = 2305848029L;
    //COLUMN id
    private int id;
    //COLUMN nome
    private String nome;
    //COLUMN order
    private short order;

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

    public short getOrder(){
        return this.order;
    }

    public void setOrder(short order){
        this.order = order;
    }


}
