package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica_documentazione_cds
 */
public class PraticaDocumentazioneCdsSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4520168956L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN id_tipo
    private String idTipo;
    //COLUMN id_documento_cds
    private String idDocumentoCds;
    //COLUMN user_create
    private String userCreate;
    //COLUMN date_create
    private String dateCreate;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getIdTipo(){
        return this.idTipo;
    }

    public void setIdTipo(String idTipo){
        this.idTipo = idTipo;
    }

    public String getIdDocumentoCds(){
        return this.idDocumentoCds;
    }

    public void setIdDocumentoCds(String idDocumentoCds){
        this.idDocumentoCds = idDocumentoCds;
    }

    public String getUserCreate(){
        return this.userCreate;
    }

    public void setUserCreate(String userCreate){
        this.userCreate = userCreate;
    }

    public String getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(String dateCreate){
        this.dateCreate = dateCreate;
    }


}
