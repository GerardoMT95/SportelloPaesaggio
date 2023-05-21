package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tipo_procedimento
 */
public class TipoProcedimentoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 8902979831L;
    //COLUMN id
    private String id;
    //COLUMN codice
    private String codice;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN invia_email
    private String inviaEmail;
    //COLUMN sanatoria
    private String sanatoria;
    //COLUMN abilitato_presentazione
    private String abilitatoPresentazione;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getInviaEmail(){
        return this.inviaEmail;
    }

    public void setInviaEmail(String inviaEmail){
        this.inviaEmail = inviaEmail;
    }

    public String getSanatoria(){
        return this.sanatoria;
    }

    public void setSanatoria(String sanatoria){
        this.sanatoria = sanatoria;
    }

    public String getAbilitatoPresentazione(){
        return this.abilitatoPresentazione;
    }

    public void setAbilitatoPresentazione(String abilitatoPresentazione){
        this.abilitatoPresentazione = abilitatoPresentazione;
    }


}
