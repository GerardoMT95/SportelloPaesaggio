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
 * DTO for table presentazione_istanza.pratica_pagamenti
 */
public class PraticaPagamentiDTO implements Serializable{

    private static final long serialVersionUID = 1365893192L;
    //COLUMN id
    private long id;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN id_tariffa
    private long idTariffa;
    //COLUMN importo_progetto
    private double importoProgetto;
    //COLUMN data_scadenza
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataScadenza;
    //COLUMN importo_pagamento
    private double importoPagamento;
    //COLUMN iud
    private String iud;
    //COLUMN iuv
    private String iuv;
    //COLUMN url_pdf
    private String urlPdf;
    //COLUMN url_mypay
    private String urlMypay;
    //COLUMN deleted
    private boolean deleted;
    //COLUMN pagato
    private boolean pagato;
    //COLUMN create_date
    private Timestamp createDate;
    //COLUMN create_user
    private String createUser;
    //COLUMN update_date
    private Timestamp updateDate;
    //COLUMN update_user
    private String updateUser;
    //COLUMN causale
    private String causale;
    //COLUMN ricevuta
    private String ricevuta;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }

    public long getIdTariffa(){
        return this.idTariffa;
    }

    public void setIdTariffa(long idTariffa){
        this.idTariffa = idTariffa;
    }

    public double getImportoProgetto(){
        return this.importoProgetto;
    }

    public void setImportoProgetto(double importoProgetto){
        this.importoProgetto = importoProgetto;
    }

    public Date getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza){
        this.dataScadenza = dataScadenza;
    }

    public double getImportoPagamento(){
        return this.importoPagamento;
    }

    public void setImportoPagamento(double importoPagamento){
        this.importoPagamento = importoPagamento;
    }

    public String getIud(){
        return this.iud;
    }

    public void setIud(String iud){
        this.iud = iud;
    }

    public String getIuv(){
        return this.iuv;
    }

    public void setIuv(String iuv){
        this.iuv = iuv;
    }

    public String getUrlPdf(){
        return this.urlPdf;
    }

    public void setUrlPdf(String urlPdf){
        this.urlPdf = urlPdf;
    }

    public String getUrlMypay(){
        return this.urlMypay;
    }

    public void setUrlMypay(String urlMypay){
        this.urlMypay = urlMypay;
    }

    public boolean getDeleted(){
        return this.deleted;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getPagato(){
        return this.pagato;
    }

    public void setPagato(boolean pagato){
        this.pagato = pagato;
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

    public Timestamp getUpdateDate(){
        return this.updateDate;
    }

    public void setUpdateDate(Timestamp updateDate){
        this.updateDate = updateDate;
    }

    public String getUpdateUser(){
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    }

    public String getCausale(){
        return this.causale;
    }

    public void setCausale(String causale){
        this.causale = causale;
    }

    public String getRicevuta(){
        return this.ricevuta;
    }

    public void setRicevuta(String ricevuta){
        this.ricevuta = ricevuta;
    }


}
