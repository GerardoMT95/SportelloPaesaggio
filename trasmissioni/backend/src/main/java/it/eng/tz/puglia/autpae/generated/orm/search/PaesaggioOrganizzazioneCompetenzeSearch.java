package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.paesaggio_organizzazione_competenze
 */
public class PaesaggioOrganizzazioneCompetenzeSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7861199583L;
    //COLUMN id
    private String id;
    //COLUMN paesaggio_organizzazione_id
    private String paesaggioOrganizzazioneId;
    //COLUMN ente_id
    private String enteId;
    //COLUMN data_inizio_delega
    private String dataInizioDelega;
    //COLUMN data_fine_delega
    private String dataFineDelega;
    //COLUMN codice_civilia
    private String codiceCivilia;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(String paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public String getEnteId(){
        return this.enteId;
    }

    public void setEnteId(String enteId){
        this.enteId = enteId;
    }

    public String getDataInizioDelega(){
        return this.dataInizioDelega;
    }

    public void setDataInizioDelega(String dataInizioDelega){
        this.dataInizioDelega = dataInizioDelega;
    }

    public String getDataFineDelega(){
        return this.dataFineDelega;
    }

    public void setDataFineDelega(String dataFineDelega){
        this.dataFineDelega = dataFineDelega;
    }

    public String getCodiceCivilia(){
        return this.codiceCivilia;
    }

    public void setCodiceCivilia(String codiceCivilia){
        this.codiceCivilia = codiceCivilia;
    }


}
