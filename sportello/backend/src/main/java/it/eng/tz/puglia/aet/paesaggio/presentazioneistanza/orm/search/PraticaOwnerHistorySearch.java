package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica_owner_history
 */
public class PraticaOwnerHistorySearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1183943435L;
    //COLUMN id
    private String id;
    //COLUMN username
    private String username;
    //COLUMN codice_segreto_utilizzato
    private String codiceSegretoUtilizzato;
    //COLUMN create_date
    private String createDate;
    //COLUMN cmis_id_delega
    private String cmisIdDelega;
    //COLUMN file_name_delega
    private String fileNameDelega;
    //COLUMN cmis_id_sollevamento_incarico
    private String cmisIdSollevamentoIncarico;
    //COLUMN file_name_sollevamento_incarico
    private String fileNameSollevamentoIncarico;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
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

    public String getCreateDate(){
        return this.createDate;
    }

    public void setCreateDate(String createDate){
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
