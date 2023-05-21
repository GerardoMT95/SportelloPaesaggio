package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.ruolo_referente
 */
public class RuoloReferenteSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5147520391L;
    //COLUMN id
    private String id;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN titolarita
    private String titolarita;
    //COLUMN altra_titolarita
    private String altraTitolarita;
    //COLUMN attiva_specifica
    private String attivaSpecifica;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getTitolarita(){
        return this.titolarita;
    }

    public void setTitolarita(String titolarita){
        this.titolarita = titolarita;
    }

    public String getAltraTitolarita(){
        return this.altraTitolarita;
    }

    public void setAltraTitolarita(String altraTitolarita){
        this.altraTitolarita = altraTitolarita;
    }

    public String getAttivaSpecifica(){
        return this.attivaSpecifica;
    }

    public void setAttivaSpecifica(String attivaSpecifica){
        this.attivaSpecifica = attivaSpecifica;
    }


}
