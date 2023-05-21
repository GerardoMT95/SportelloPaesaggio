package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pagamenti_mypay
 */
public class PagamentiMypaySearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5157380341L;
    //COLUMN iud
    private String iud;
    //COLUMN pratica_id
    private String praticaId;
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
    private String dataInserimento;
    //COLUMN data_modifica
    private String dataModifica;
    //COLUMN importo
    private String importo;
    //COLUMN idsession
    private String idsession;
    //COLUMN esito
    private String esito;
    //COLUMN error
    private String error;

    public String getIud(){
        return this.iud;
    }

    public void setIud(String iud){
        this.iud = iud;
    }

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
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

    public String getDataInserimento(){
        return this.dataInserimento;
    }

    public void setDataInserimento(String dataInserimento){
        this.dataInserimento = dataInserimento;
    }

    public String getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(String dataModifica){
        this.dataModifica = dataModifica;
    }

    public String getImporto(){
        return this.importo;
    }

    public void setImporto(String importo){
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


}
