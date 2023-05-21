package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.allegati_tipo_contenuto
 */
public class AllegatiTipoContenutoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2209869711L;
    //COLUMN allegati_id
    private String allegatiId;
    //COLUMN tipo_contenuto_id
    private String tipoContenutoId;

    public String getAllegatiId(){
        return this.allegatiId;
    }

    public void setAllegatiId(String allegatiId){
        this.allegatiId = allegatiId;
    }

    public String getTipoContenutoId(){
        return this.tipoContenutoId;
    }

    public void setTipoContenutoId(String tipoContenutoId){
        this.tipoContenutoId = tipoContenutoId;
    }


}
