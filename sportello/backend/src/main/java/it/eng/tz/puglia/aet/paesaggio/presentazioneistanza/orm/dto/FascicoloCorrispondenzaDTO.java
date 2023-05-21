package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for table presentazione_istanza.fascicolo_corrispondenza
 */
public class FascicoloCorrispondenzaDTO implements Serializable{

    private static final long serialVersionUID = 2892643320L;
    //COLUMN id_corrispondenza
    private long idCorrispondenza;
    //COLUMN id_pratica
    private UUID idPratica;

    public long getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(long idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }


}
