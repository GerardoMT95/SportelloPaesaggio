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
 * DTO for table presentazione_istanza.procedimento_contenuto
 */
public class ProcedimentoContenutoDTO implements Serializable{

    private static final long serialVersionUID = 8916092440L;
    //COLUMN tipo_procedimento
    private int tipoProcedimento;
    //COLUMN tipo_contenuto_id
    private int tipoContenutoId;

    public int getTipoProcedimento(){
        return this.tipoProcedimento;
    }

    public void setTipoProcedimento(int tipoProcedimento){
        this.tipoProcedimento = tipoProcedimento;
    }

    public int getTipoContenutoId(){
        return this.tipoContenutoId;
    }

    public void setTipoContenutoId(int tipoContenutoId){
        this.tipoContenutoId = tipoContenutoId;
    }


}
