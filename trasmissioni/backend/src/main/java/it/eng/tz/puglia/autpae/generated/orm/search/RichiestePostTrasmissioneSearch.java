package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table autpae.richieste_post_trasmissione
 */
public class RichiestePostTrasmissioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 548273114L;
    //COLUMN id
    private String id;
    //COLUMN id_fascicolo
    private String idFascicolo;
    //COLUMN motivazione
    private String motivazione;
    //COLUMN stato
    private String stato;
    //COLUMN tipo
    private String tipo;
    //COLUMN date_created
    private String dateCreated;
    //COLUMN user_created
    private String userCreated;
    //COLUMN date_updated
    private String dateUpdated;
    //COLUMN user_updated
    private String userUpdated;
    //COLUMN id_corrispondenza
    private String idCorrispondenza;

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

    public String getMotivazione(){
        return this.motivazione;
    }

    public void setMotivazione(String motivazione){
        this.motivazione = motivazione;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
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

    public String getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(String idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }


}
