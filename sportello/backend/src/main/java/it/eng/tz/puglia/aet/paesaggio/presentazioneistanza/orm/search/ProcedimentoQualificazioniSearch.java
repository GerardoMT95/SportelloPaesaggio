package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.procedimento_qualificazioni
 */
public class ProcedimentoQualificazioniSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9615950663L;
    //COLUMN id_tipo_procedimento
    private String idTipoProcedimento;
    //COLUMN codice_tipi_e_qualificazioni
    private String codiceTipiEQualificazioni;

    public String getIdTipoProcedimento(){
        return this.idTipoProcedimento;
    }

    public void setIdTipoProcedimento(String idTipoProcedimento){
        this.idTipoProcedimento = idTipoProcedimento;
    }

    public String getCodiceTipiEQualificazioni(){
        return this.codiceTipiEQualificazioni;
    }

    public void setCodiceTipiEQualificazioni(String codiceTipiEQualificazioni){
        this.codiceTipiEQualificazioni = codiceTipiEQualificazioni;
    }


}
