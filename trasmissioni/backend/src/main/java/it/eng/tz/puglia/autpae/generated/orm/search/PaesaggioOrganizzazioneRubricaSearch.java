package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.paesaggio_organizzazione_rubrica
 */
public class PaesaggioOrganizzazioneRubricaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6857690153L;
    //COLUMN id
    private String id;
    //COLUMN paesaggio_organizzazione_id
    private String paesaggioOrganizzazioneId;
    //COLUMN codice_applicazione
    private String codiceApplicazione;
    //COLUMN nome
    private String nome;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN data_creazione
    private String dataCreazione;
    //COLUMN data_modifica
    private String dataModifica;
    //COLUMN username_creazione
    private String usernameCreazione;
    //COLUMN username_modifica
    private String usernameModifica;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(String paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public String getCodiceApplicazione(){
        return this.codiceApplicazione;
    }

    public void setCodiceApplicazione(String codiceApplicazione){
        this.codiceApplicazione = codiceApplicazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public String getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(String dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public String getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(String dataModifica){
        this.dataModifica = dataModifica;
    }

    public String getUsernameCreazione(){
        return this.usernameCreazione;
    }

    public void setUsernameCreazione(String usernameCreazione){
        this.usernameCreazione = usernameCreazione;
    }

    public String getUsernameModifica(){
        return this.usernameModifica;
    }

    public void setUsernameModifica(String usernameModifica){
        this.usernameModifica = usernameModifica;
    }


}
