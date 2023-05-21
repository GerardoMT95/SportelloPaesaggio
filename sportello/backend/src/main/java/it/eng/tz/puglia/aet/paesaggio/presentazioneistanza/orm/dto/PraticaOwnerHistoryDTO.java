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
 * DTO for table presentazione_istanza.pratica_owner_history
 */
public class PraticaOwnerHistoryDTO implements Serializable{

    private static final long serialVersionUID = 9233615857L;
    //COLUMN id
    private UUID id;
    //COLUMN username
    private String username;
    //COLUMN codice_segreto_utilizzato
    private String codiceSegretoUtilizzato;
    //COLUMN create_date
    private Timestamp createDate;
    //COLUMN cmis_id_delega
    private String cmisIdDelega;
    //COLUMN file_name_delega
    private String fileNameDelega;
    //COLUMN cmis_id_sollevamento_incarico
    private String cmisIdSollevamentoIncarico;
    //COLUMN file_name_sollevamento_incarico
    private String fileNameSollevamentoIncarico;

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getCodiceSegretoUtilizzato(){
        return this.codiceSegretoUtilizzato;
    }

    public void setCodiceSegretoUtilizzato(String codiceSegretoUtilizzato){
        this.codiceSegretoUtilizzato = codiceSegretoUtilizzato;
    }

    public Timestamp getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate){
        this.createDate = createDate;
    }

    public String getCmisIdDelega(){
        return this.cmisIdDelega;
    }

    public void setCmisIdDelega(String cmisIdDelega){
        this.cmisIdDelega = cmisIdDelega;
    }

    public String getFileNameDelega(){
        return this.fileNameDelega;
    }

    public void setFileNameDelega(String fileNameDelega){
        this.fileNameDelega = fileNameDelega;
    }

    public String getCmisIdSollevamentoIncarico(){
        return this.cmisIdSollevamentoIncarico;
    }

    public void setCmisIdSollevamentoIncarico(String cmisIdSollevamentoIncarico){
        this.cmisIdSollevamentoIncarico = cmisIdSollevamentoIncarico;
    }

    public String getFileNameSollevamentoIncarico(){
        return this.fileNameSollevamentoIncarico;
    }

    public void setFileNameSollevamentoIncarico(String fileNameSollevamentoIncarico){
        this.fileNameSollevamentoIncarico = fileNameSollevamentoIncarico;
    }


}
