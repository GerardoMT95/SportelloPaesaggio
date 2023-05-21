package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.configurazione_casella_postale
 */
public class ConfigurazioneCasellaPostaleSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9797555892L;
    //COLUMN email
    private String email;
    //COLUMN configurazione
    private String configurazione;

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getConfigurazione(){
        return this.configurazione;
    }

    public void setConfigurazione(String configurazione){
        this.configurazione = configurazione;
    }


}
