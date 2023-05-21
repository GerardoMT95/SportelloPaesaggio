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
 * DTO for table presentazione_istanza.web_content
 */
public class WebContentDTO implements Serializable{

    private static final long serialVersionUID = 3135390188L;
    //COLUMN codice_contenuto
    private String codiceContenuto;
    //COLUMN tooltip
    private String tooltip;
    //COLUMN contenuto
    private String contenuto;

    public String getCodiceContenuto(){
        return this.codiceContenuto;
    }

    public void setCodiceContenuto(String codiceContenuto){
        this.codiceContenuto = codiceContenuto;
    }

    public String getTooltip(){
        return this.tooltip;
    }

    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }

    public String getContenuto(){
        return this.contenuto;
    }

    public void setContenuto(String contenuto){
        this.contenuto = contenuto;
    }


}
