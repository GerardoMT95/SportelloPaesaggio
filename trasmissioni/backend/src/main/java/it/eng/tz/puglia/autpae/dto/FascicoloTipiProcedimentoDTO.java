package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table autpae.fascicolo_tipi_procedimento
 */
public class FascicoloTipiProcedimentoDTO implements Serializable{

    private static final long serialVersionUID = 3778816222L;
    //COLUMN id
    private long id;
    //COLUMN id_fascicolo
    private long idFascicolo;
    //COLUMN codice_tipo_procedimento
    private String codiceTipoProcedimento;
    //COLUMN inizio_validita
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date inizioValidita;
    //COLUMN fine_validita
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date fineValidita;
    

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

    public String getCodiceTipoProcedimento(){
        return this.codiceTipoProcedimento;
    }

    public void setCodiceTipoProcedimento(String codiceTipoProcedimento){
        this.codiceTipoProcedimento = codiceTipoProcedimento;
    }

    public Date getInizioValidita(){
        return this.inizioValidita;
    }

    public void setInizioValidita(Date inizioValidita){
        this.inizioValidita = inizioValidita;
    }

    public Date getFineValidita(){
        return this.fineValidita;
    }

    public void setFineValidita(Date fineValidita){
        this.fineValidita = fineValidita;
    }


}
