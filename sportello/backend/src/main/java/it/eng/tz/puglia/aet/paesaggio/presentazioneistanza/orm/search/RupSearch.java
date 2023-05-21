package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.rup
 */
public class RupSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1359634621L;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN username
    private String username;
    //COLUMN denominazione
    private String denominazione;
    //COLUMN data_scadenza
    private String dataScadenza;

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
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

    public String getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(String dataScadenza){
        this.dataScadenza = dataScadenza;
    }


}
