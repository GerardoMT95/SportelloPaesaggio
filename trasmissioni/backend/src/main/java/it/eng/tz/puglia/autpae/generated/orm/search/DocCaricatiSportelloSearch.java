package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table pareri.doc_caricati_sportello
 */
public class DocCaricatiSportelloSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 8826040527L;
    //COLUMN id
    private String id;
    //COLUMN id_doc_caricato
    private String idDocCaricato;
    //COLUMN date_insert
    private String dateInsert;
    //COLUMN user_insert
    private String userInsert;
    //COLUMN deleted
    private String deleted;
    //COLUMN id_fascicolo
    private String idFascicolo;
    //COLUMN date_updated
    private String dateUpdated;
    //COLUMN user_updated
    private String userUpdated;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdDocCaricato(){
        return this.idDocCaricato;
    }

    public void setIdDocCaricato(String idDocCaricato){
        this.idDocCaricato = idDocCaricato;
    }

    public String getDateInsert(){
        return this.dateInsert;
    }

    public void setDateInsert(String dateInsert){
        this.dateInsert = dateInsert;
    }

    public String getUserInsert(){
        return this.userInsert;
    }

    public void setUserInsert(String userInsert){
        this.userInsert = userInsert;
    }

    public String getDeleted(){
        return this.deleted;
    }

    public void setDeleted(String deleted){
        this.deleted = deleted;
    }

    public String getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(String idFascicolo){
        this.idFascicolo = idFascicolo;
    }

    public String getDateUpdated(){
        return this.dateUpdated;
    }

    public void setDateUpdated(String dateUpdated){
        this.dateUpdated = dateUpdated;
    }

    public String getUserUpdated(){
        return this.userUpdated;
    }

    public void setUserUpdated(String userUpdated){
        this.userUpdated = userUpdated;
    }


}
