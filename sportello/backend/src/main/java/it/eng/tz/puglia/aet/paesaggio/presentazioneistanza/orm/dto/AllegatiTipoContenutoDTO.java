package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for table presentazione_istanza.allegati_tipo_contenuto
 */
public class AllegatiTipoContenutoDTO implements Serializable{

    private static final long serialVersionUID = 2167560916L;
    //COLUMN allegati_id
    private UUID allegatiId;
    //COLUMN tipo_contenuto_id
    private int tipoContenutoId;
    
    public AllegatiTipoContenutoDTO()
    {
    	super();
    }
    public AllegatiTipoContenutoDTO(UUID allegatoID, int tipoContenuto)
    {
    	super();
    	this.allegatiId = allegatoID;
    	this.tipoContenutoId = tipoContenuto;
    }

    public UUID getAllegatiId(){
        return this.allegatiId;
    }

    public void setAllegatiId(UUID allegatiId){
        this.allegatiId = allegatiId;
    }

    public int getTipoContenutoId(){
        return this.tipoContenutoId;
    }

    public void setTipoContenutoId(int tipoContenutoId){
        this.tipoContenutoId = tipoContenutoId;
    }


}
