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
 * DTO for table presentazione_istanza.pagamenti_mypay
 */
public class PagamentiMypayDTO implements Serializable{

	public static enum StatoPagamento{
		OK,INCORSO,KO
	}
	
    private static final long serialVersionUID = 1996427257L;
    //COLUMN iud
    private String iud;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN ente_id
    private String enteId;
    //COLUMN causale
    private String causale;
    //COLUMN cod_ipa_ente
    private String codIpaEnte;
    //COLUMN cf_pagatore
    private String cfPagatore;
    //COLUMN email_pagatore
    private String emailPagatore;
    //COLUMN ret_url
    private String retUrl;
    //COLUMN sogg_paga
    private String soggPaga;
    //COLUMN tipologia
    private String tipologia;
    //COLUMN tipo_riscossione
    private String tipoRiscossione;
    //COLUMN stato
    private String stato;
    //COLUMN data_inserimento
    @JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
    private Date dataInserimento;
    //COLUMN data_modifica
    @JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
    private Date dataModifica;
    //COLUMN importo
    private BigDecimal importo;
    //COLUMN idsession
    private String idsession;
    //COLUMN esito
    private String esito;
    //COLUMN error
    private String error;
    //COLUMN url_to_pay  url dove effettuare il pagamento
    private String urlToPay;
    //COLUMN cfg_endpoint_mypay
    private String cfgEndpointMyPay;
    //COLUMN cfg_password_mypay criptata
    private String cfgPasswordMyPay;

    public String getCfgEndpointMyPay() {
		return cfgEndpointMyPay;
	}

	public void setCfgEndpointMyPay(String cfgEndpointMyPay) {
		this.cfgEndpointMyPay = cfgEndpointMyPay;
	}

	public String getCfgPasswordMyPay() {
		return cfgPasswordMyPay;
	}

	public void setCfgPasswordMyPay(String cfgPasswordMyPay) {
		this.cfgPasswordMyPay = cfgPasswordMyPay;
	}

	public String getIud(){
        return this.iud;
    }

    public void setIud(String iud){
        this.iud = iud;
    }

    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getEnteId(){
        return this.enteId;
    }

    public void setEnteId(String enteId){
        this.enteId = enteId;
    }

    public String getCausale(){
        return this.causale;
    }

    public void setCausale(String causale){
        this.causale = causale;
    }

    public String getCodIpaEnte(){
        return this.codIpaEnte;
    }

    public void setCodIpaEnte(String codIpaEnte){
        this.codIpaEnte = codIpaEnte;
    }

    public String getCfPagatore(){
        return this.cfPagatore;
    }

    public void setCfPagatore(String cfPagatore){
        this.cfPagatore = cfPagatore;
    }

    public String getEmailPagatore(){
        return this.emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore){
        this.emailPagatore = emailPagatore;
    }

    public String getRetUrl(){
        return this.retUrl;
    }

    public void setRetUrl(String retUrl){
        this.retUrl = retUrl;
    }

    public String getSoggPaga(){
        return this.soggPaga;
    }

    public void setSoggPaga(String soggPaga){
        this.soggPaga = soggPaga;
    }

    public String getTipologia(){
        return this.tipologia;
    }

    public void setTipologia(String tipologia){
        this.tipologia = tipologia;
    }

    public String getTipoRiscossione(){
        return this.tipoRiscossione;
    }

    public void setTipoRiscossione(String tipoRiscossione){
        this.tipoRiscossione = tipoRiscossione;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public Date getDataInserimento(){
        return this.dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento){
        this.dataInserimento = dataInserimento;
    }

    public Date getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(Date dataModifica){
        this.dataModifica = dataModifica;
    }

    public BigDecimal getImporto(){
        return this.importo;
    }

    public void setImporto(BigDecimal importo){
        this.importo = importo;
    }

    public String getIdsession(){
        return this.idsession;
    }

    public void setIdsession(String idsession){
        this.idsession = idsession;
    }

    public String getEsito(){
        return this.esito;
    }

    public void setEsito(String esito){
        this.esito = esito;
    }

    public String getError(){
        return this.error;
    }

    public void setError(String error){
        this.error = error;
    }

	public String getUrlToPay() {
		return urlToPay;
	}

	public void setUrlToPay(String urlToPay) {
		this.urlToPay = urlToPay;
	}


}
