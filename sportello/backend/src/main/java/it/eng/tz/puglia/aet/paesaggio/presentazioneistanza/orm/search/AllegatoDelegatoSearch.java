package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.allegato_delegato
 */
public class AllegatoDelegatoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7934522825L;
    //COLUMN id_allegato
    private String idAllegato;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN indice_delegato
    private String indiceDelegato;

    public String getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(String idAllegato){
        this.idAllegato = idAllegato;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getIndiceDelegato(){
        return this.indiceDelegato;
    }

    public void setIndiceDelegato(String indiceDelegato){
        this.indiceDelegato = indiceDelegato;
    }


}
