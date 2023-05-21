package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.procedimento_qualificazioni
 */
public class ProcedimentoQualificazioniDTO implements Serializable{

    private static final long serialVersionUID = 7967824015L;
    //COLUMN id_tipo_procedimento
    private int idTipoProcedimento;
    //COLUMN codice_tipi_e_qualificazioni
    private String codiceTipiEQualificazioni;

    public int getIdTipoProcedimento(){
        return this.idTipoProcedimento;
    }

    public void setIdTipoProcedimento(int idTipoProcedimento){
        this.idTipoProcedimento = idTipoProcedimento;
    }

    public String getCodiceTipiEQualificazioni(){
        return this.codiceTipiEQualificazioni;
    }

    public void setCodiceTipiEQualificazioni(String codiceTipiEQualificazioni){
        this.codiceTipiEQualificazioni = codiceTipiEQualificazioni;
    }


}
