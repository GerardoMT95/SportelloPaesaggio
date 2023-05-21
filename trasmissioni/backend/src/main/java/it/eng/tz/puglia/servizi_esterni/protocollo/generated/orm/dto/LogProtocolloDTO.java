package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.dto;

import java.io.Serializable;
import java.sql.*;

/**
 * DTO for table putt.log_protocollo
 */
public class LogProtocolloDTO implements Serializable{

    private static final long serialVersionUID = 6162630689L;
    //COLUMN id
    private long id;
    //COLUMN request
    private String request;
    //COLUMN response
    private String response;
    //COLUMN protocollo
    private String protocollo;
    //COLUMN verso
    private String verso;
    //COLUMN data_protocollo
    private Timestamp dataProtocollo;
    //COLUMN data_esecuzione
    private Timestamp dataEsecuzione;
    //COLUMN id_allegato
    private Long idAllegato;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getRequest(){
        return this.request;
    }

    public void setRequest(String request){
        this.request = request;
    }

    public String getResponse(){
        return this.response;
    }

    public void setResponse(String response){
        this.response = response;
    }

    public String getProtocollo(){
        return this.protocollo;
    }

    public void setProtocollo(String protocollo){
        this.protocollo = protocollo;
    }

    public String getVerso(){
        return this.verso;
    }

    public void setVerso(String verso){
        this.verso = verso;
    }

    public Timestamp getDataProtocollo(){
        return this.dataProtocollo;
    }

    public void setDataProtocollo(Timestamp dataProtocollo){
        this.dataProtocollo = dataProtocollo;
    }

    public Timestamp getDataEsecuzione(){
        return this.dataEsecuzione;
    }

    public void setDataEsecuzione(Timestamp dataEsecuzione){
        this.dataEsecuzione = dataEsecuzione;
    }

    public Long getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(Long idAllegato){
        this.idAllegato = idAllegato;
    }


}
