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
 * DTO for table presentazione_istanza.tariffa
 */
public class TariffaDTO implements Serializable{

    private static final long serialVersionUID = 1513461052L;
    //COLUMN id
    private long id;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN id_tipo_procedimento
    private int idTipoProcedimento;
    //COLUMN soglia_minima
    private double sogliaMinima;
    //COLUMN soglia_massima
    private Double sogliaMassima;
    //COLUMN delete_eccedente
    private boolean deleteEccedente;
    //COLUMN percentuale
    private double percentuale;
    //COLUMN cifra_da_aggiungere
    private double cifraDaAggiungere;
    //COLUMN percentuale_finale
    private double percentualeFinale;
    //COLUMN start_date
    private Timestamp startDate;
    //COLUMN end_date
    private Timestamp endDate;
    //COLUMN create_date
    private Timestamp createDate;
    //COLUMN create_user
    private String createUser;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public int getIdTipoProcedimento(){
        return this.idTipoProcedimento;
    }

    public void setIdTipoProcedimento(int idTipoProcedimento){
        this.idTipoProcedimento = idTipoProcedimento;
    }

    public double getSogliaMinima(){
        return this.sogliaMinima;
    }

    public void setSogliaMinima(double sogliaMinima){
        this.sogliaMinima = sogliaMinima;
    }

    public Double getSogliaMassima(){
        return this.sogliaMassima;
    }

    public void setSogliaMassima(Double sogliaMassima){
        this.sogliaMassima = sogliaMassima;
    }

    public boolean getDeleteEccedente(){
        return this.deleteEccedente;
    }

    public void setDeleteEccedente(boolean deleteEccedente){
        this.deleteEccedente = deleteEccedente;
    }

    public double getPercentuale(){
        return this.percentuale;
    }

    public void setPercentuale(double percentuale){
        this.percentuale = percentuale;
    }

    public double getCifraDaAggiungere(){
        return this.cifraDaAggiungere;
    }

    public void setCifraDaAggiungere(double cifraDaAggiungere){
        this.cifraDaAggiungere = cifraDaAggiungere;
    }

    public double getPercentualeFinale(){
        return this.percentualeFinale;
    }

    public void setPercentualeFinale(double percentualeFinale){
        this.percentualeFinale = percentualeFinale;
    }

    public Timestamp getStartDate(){
        return this.startDate;
    }

    public void setStartDate(Timestamp startDate){
        this.startDate = startDate;
    }

    public Timestamp getEndDate(){
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate){
        this.endDate = endDate;
    }

    public Timestamp getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate){
        this.createDate = createDate;
    }

    public String getCreateUser(){
        return this.createUser;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }


}
