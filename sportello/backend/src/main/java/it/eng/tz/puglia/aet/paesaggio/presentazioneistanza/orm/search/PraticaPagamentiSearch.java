package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica_pagamenti
 */
public class PraticaPagamentiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 8500316321L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN id_tariffa
    private String idTariffa;
    //COLUMN importo_progetto
    private String importoProgetto;
    //COLUMN data_scadenza
    private String dataScadenza;
    //COLUMN importo_pagamento
    private String importoPagamento;
    //COLUMN iud
    private String iud;
    //COLUMN iuv
    private String iuv;
    //COLUMN url_pdf
    private String urlPdf;
    //COLUMN url_mypay
    private String urlMypay;
    //COLUMN deleted
    private String deleted;
    //COLUMN pagato
    private String pagato;
    //COLUMN create_date
    private String createDate;
    //COLUMN create_user
    private String createUser;
    //COLUMN update_date
    private String updateDate;
    //COLUMN update_user
    private String updateUser;
    //COLUMN causale
    private String causale;
    //COLUMN ricevuta
    private String ricevuta;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getIdTariffa(){
        return this.idTariffa;
    }

    public void setIdTariffa(String idTariffa){
        this.idTariffa = idTariffa;
    }

    public String getImportoProgetto(){
        return this.importoProgetto;
    }

    public void setImportoProgetto(String importoProgetto){
        this.importoProgetto = importoProgetto;
    }

    public String getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(String dataScadenza){
        this.dataScadenza = dataScadenza;
    }

    public String getImportoPagamento(){
        return this.importoPagamento;
    }

    public void setImportoPagamento(String importoPagamento){
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

    public String getDeleted(){
        return this.deleted;
    }

    public void setDeleted(String deleted){
        this.deleted = deleted;
    }

    public String getPagato(){
        return this.pagato;
    }

    public void setPagato(String pagato){
        this.pagato = pagato;
    }

    public String getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }

    public String getCreateUser(){
        return this.createUser;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public String getUpdateDate(){
        return this.updateDate;
    }

    public void setUpdateDate(String updateDate){
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
