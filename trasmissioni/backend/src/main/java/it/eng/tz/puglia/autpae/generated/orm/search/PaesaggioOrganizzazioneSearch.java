package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.paesaggio_organizzazione
 */
public class PaesaggioOrganizzazioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5911385197L;
    //COLUMN id
    private String id;
    //COLUMN ente_id
    private String enteId;
    //COLUMN tipo_organizzazione
    private String tipoOrganizzazione;
    //COLUMN tipo_organizzazione_specifica
    private String tipoOrganizzazioneSpecifica;
    //COLUMN codice_civilia
    private String codiceCivilia;
    //COLUMN riferimento_organizzazione
    private String riferimentoOrganizzazione;
    //COLUMN data_creazione
    private String dataCreazione;
    //COLUMN data_termine
    private String dataTermine;
    //COLUMN denominazione
    private String denominazione;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getEnteId(){
        return this.enteId;
    }

    public void setEnteId(String enteId){
        this.enteId = enteId;
    }

    public String getTipoOrganizzazione(){
        return this.tipoOrganizzazione;
    }

    public void setTipoOrganizzazione(String tipoOrganizzazione){
        this.tipoOrganizzazione = tipoOrganizzazione;
    }

    public String getTipoOrganizzazioneSpecifica(){
        return this.tipoOrganizzazioneSpecifica;
    }

    public void setTipoOrganizzazioneSpecifica(String tipoOrganizzazioneSpecifica){
        this.tipoOrganizzazioneSpecifica = tipoOrganizzazioneSpecifica;
    }

    public String getCodiceCivilia(){
        return this.codiceCivilia;
    }

    public void setCodiceCivilia(String codiceCivilia){
        this.codiceCivilia = codiceCivilia;
    }

    public String getRiferimentoOrganizzazione(){
        return this.riferimentoOrganizzazione;
    }

    public void setRiferimentoOrganizzazione(String riferimentoOrganizzazione){
        this.riferimentoOrganizzazione = riferimentoOrganizzazione;
    }

    public String getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(String dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public String getDataTermine(){
        return this.dataTermine;
    }

    public void setDataTermine(String dataTermine){
        this.dataTermine = dataTermine;
    }

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }


}
