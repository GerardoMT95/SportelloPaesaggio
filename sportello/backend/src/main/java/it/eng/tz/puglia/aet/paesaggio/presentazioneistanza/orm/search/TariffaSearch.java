package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tariffa
 */
public class TariffaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3821514599L;
    //COLUMN id
    private String id;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN id_tipo_procedimento
    private String idTipoProcedimento;
    //COLUMN soglia_minima
    private String sogliaMinima;
    //COLUMN soglia_massima
    private String sogliaMassima;
    //COLUMN delete_eccedente
    private String deleteEccedente;
    //COLUMN percentuale
    private String percentuale;
    //COLUMN cifra_da_aggiungere
    private String cifraDaAggiungere;
    //COLUMN percentuale_finale
    private String percentualeFinale;
    //COLUMN start_date
    private String startDate;
    //COLUMN end_date
    private String endDate;
    //COLUMN create_date
    private String createDate;
    //COLUMN create_user
    private String createUser;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getIdTipoProcedimento(){
        return this.idTipoProcedimento;
    }

    public void setIdTipoProcedimento(String idTipoProcedimento){
        this.idTipoProcedimento = idTipoProcedimento;
    }

    public String getSogliaMinima(){
        return this.sogliaMinima;
    }

    public void setSogliaMinima(String sogliaMinima){
        this.sogliaMinima = sogliaMinima;
    }

    public String getSogliaMassima(){
        return this.sogliaMassima;
    }

    public void setSogliaMassima(String sogliaMassima){
        this.sogliaMassima = sogliaMassima;
    }

    public String getDeleteEccedente(){
        return this.deleteEccedente;
    }

    public void setDeleteEccedente(String deleteEccedente){
        this.deleteEccedente = deleteEccedente;
    }

    public String getPercentuale(){
        return this.percentuale;
    }

    public void setPercentuale(String percentuale){
        this.percentuale = percentuale;
    }

    public String getCifraDaAggiungere(){
        return this.cifraDaAggiungere;
    }

    public void setCifraDaAggiungere(String cifraDaAggiungere){
        this.cifraDaAggiungere = cifraDaAggiungere;
    }

    public String getPercentualeFinale(){
        return this.percentualeFinale;
    }

    public void setPercentualeFinale(String percentualeFinale){
        this.percentualeFinale = percentualeFinale;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
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


}
