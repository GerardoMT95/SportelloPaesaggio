package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.template_doc
 */
public class TemplateDocSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7562494547L;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN nome
    private String nome;
    //COLUMN descrizione
    private String descrizione;

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }


}
