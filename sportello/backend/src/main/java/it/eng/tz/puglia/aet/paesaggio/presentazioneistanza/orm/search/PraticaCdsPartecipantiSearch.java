package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica_cds_partecipanti
 */
public class PraticaCdsPartecipantiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 652773912L;
    //COLUMN id
    private String id;
    //COLUMN username
    private String username;
    //COLUMN denominazione
    private String denominazione;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }


}
