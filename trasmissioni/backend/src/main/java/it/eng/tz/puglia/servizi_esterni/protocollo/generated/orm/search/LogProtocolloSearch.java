package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table putt.log_protocollo
 */
public class LogProtocolloSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4593409326L;
    //COLUMN id
    private String id;
    //COLUMN request
    private String request;
    //COLUMN response
    private String response;
    //COLUMN protocollo
    private String protocollo;
    //COLUMN verso
    private String verso;
    //COLUMN data_protocollo
    private String dataProtocollo;
    //COLUMN data_esecuzione
    private String dataEsecuzione;
    //COLUMN id_allegato
    private String idAllegato;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
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

    public String getDataProtocollo(){
        return this.dataProtocollo;
    }

    public void setDataProtocollo(String dataProtocollo){
        this.dataProtocollo = dataProtocollo;
    }

    public String getDataEsecuzione(){
        return this.dataEsecuzione;
    }

    public void setDataEsecuzione(String dataEsecuzione){
        this.dataEsecuzione = dataEsecuzione;
    }

    public String getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(String idAllegato){
        this.idAllegato = idAllegato;
    }


}
