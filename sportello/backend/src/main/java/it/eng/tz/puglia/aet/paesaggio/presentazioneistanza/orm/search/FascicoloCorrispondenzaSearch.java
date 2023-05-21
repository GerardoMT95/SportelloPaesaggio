package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.fascicolo_corrispondenza
 */
public class FascicoloCorrispondenzaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6947701621L;
    //COLUMN id_corrispondenza
    private String idCorrispondenza;
    //COLUMN id_pratica
    private String idPratica;

    public String getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(String idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }


}
