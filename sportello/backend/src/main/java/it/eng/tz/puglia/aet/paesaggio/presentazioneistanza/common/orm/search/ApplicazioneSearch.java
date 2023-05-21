package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.applicazione
 */
public class ApplicazioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9614328275L;
    //COLUMN id
    private String id;
    //COLUMN nome
    private String nome;
    //COLUMN codice
    private String codice;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }


}
