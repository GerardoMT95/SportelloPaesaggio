package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for table autpae.annotazioni_interne
 */
public class AnnotazioniInterneDTO implements Serializable{

    private static final long serialVersionUID = 2735137339L;
    //COLUMN id
    private long id;
    //COLUMN id_fascicolo
    private long idFascicolo;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN esito_controllo
    private String esitoControllo;
    //COLUMN note
    private String note;
    //COLUMN date_created
    private Timestamp dateCreated;
    //COLUMN user_created
    private String userCreated;
    //COLUMN date_updated
    private Timestamp dateUpdated;
    //COLUMN user_updated
    private String userUpdated;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(long idFascicolo){
        this.idFascicolo = idFascicolo;
    }

    public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
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

    public Timestamp getDateCreated(){
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated){
        this.dateCreated = dateCreated;
    }

    public String getUserCreated(){
        return this.userCreated;
    }

    public void setUserCreated(String userCreated){
        this.userCreated = userCreated;
    }

    public Timestamp getDateUpdated(){
        return this.dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated){
        this.dateUpdated = dateUpdated;
    }

    public String getUserUpdated(){
        return this.userUpdated;
    }

    public void setUserUpdated(String userUpdated){
        this.userUpdated = userUpdated;
    }


}
