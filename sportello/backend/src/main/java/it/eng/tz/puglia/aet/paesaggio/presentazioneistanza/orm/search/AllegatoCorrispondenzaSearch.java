package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.allegato_corrispondenza
 */
public class AllegatoCorrispondenzaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2979083958L;
    //COLUMN id_allegato
    private String idAllegato;
    //COLUMN id_corrispondenza
    private String idCorrispondenza;

    public String getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(String idAllegato){
        this.idAllegato = idAllegato;
    }

    public String getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(String idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }


}
