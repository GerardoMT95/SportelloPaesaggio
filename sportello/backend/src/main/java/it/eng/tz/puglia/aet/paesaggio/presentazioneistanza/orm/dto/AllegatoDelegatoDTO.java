package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table presentazione_istanza.allegato_delegato
 */
public class AllegatoDelegatoDTO implements Serializable{

    private static final long serialVersionUID = 6363701759L;
    //COLUMN id_allegato
    private UUID idAllegato;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN indice_delegato
    private int indiceDelegato;

    public UUID getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(UUID idAllegato){
        this.idAllegato = idAllegato;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }

    public int getIndiceDelegato(){
        return this.indiceDelegato;
    }

    public void setIndiceDelegato(int indiceDelegato){
        this.indiceDelegato = indiceDelegato;
    }


}
