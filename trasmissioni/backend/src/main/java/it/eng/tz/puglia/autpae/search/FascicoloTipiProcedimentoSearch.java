package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table autpae.fascicolo_tipi_procedimento
 */
public class FascicoloTipiProcedimentoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1551300597L;
    //COLUMN id
    private String id;
    //COLUMN id_fascicolo
    private String idFascicolo;
    //COLUMN codice_tipo_procedimento
    private String codiceTipoProcedimento;
    //COLUMN inizio_validita
    private String inizioValidita;
    //COLUMN fine_validita
    private String fineValidita;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdFascicolo(){
        return this.idFascicolo;
    }

    public void setIdFascicolo(String idFascicolo){
        this.idFascicolo = idFascicolo;
    }

    public String getCodiceTipoProcedimento(){
        return this.codiceTipoProcedimento;
    }

    public void setCodiceTipoProcedimento(String codiceTipoProcedimento){
        this.codiceTipoProcedimento = codiceTipoProcedimento;
    }

    public String getInizioValidita(){
        return this.inizioValidita;
    }

    public void setInizioValidita(String inizioValidita){
        this.inizioValidita = inizioValidita;
    }

    public String getFineValidita(){
        return this.fineValidita;
    }

    public void setFineValidita(String fineValidita){
        this.fineValidita = fineValidita;
    }


}
