package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table autpae.annotazioni_interne
 */
public class AnnotazioniInterneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7729332679L;
    //COLUMN id
    private String id;
    //COLUMN id_fascicolo
    private String idFascicolo;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN esito_controllo
    private String esitoControllo;
    //COLUMN note
    private String note;
    //COLUMN date_created
    private String dateCreated;
    //COLUMN user_created
    private String userCreated;
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

    public String getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(String idFascicolo){
        this.idFascicolo = idFascicolo;
    }

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getEsitoControllo(){
        return this.esitoControllo;
    }

    public void setEsitoControllo(String esitoControllo){
        this.esitoControllo = esitoControllo;
    }

    public String getNote(){
        return this.note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getDateCreated(){
        return this.dateCreated;
    }

    public void setDateCreated(String dateCreated){
        this.dateCreated = dateCreated;
    }

    public String getUserCreated(){
        return this.userCreated;
    }

    public void setUserCreated(String userCreated){
        this.userCreated = userCreated;
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
