package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.disclaimer_pratica
 */
public class DisclaimerPraticaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 280838322L;
    //COLUMN id
    private String id;
    //COLUMN disclaimer_id
    private String disclaimerId;
    //COLUMN flag
    private String flag;
    //COLUMN pratica_id
    private String praticaId;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDisclaimerId(){
        return this.disclaimerId;
    }

    public void setDisclaimerId(String disclaimerId){
        this.disclaimerId = disclaimerId;
    }

    public String getFlag(){
        return this.flag;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }


}
