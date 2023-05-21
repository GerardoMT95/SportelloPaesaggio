package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.procedimento_contenuto
 */
public class ProcedimentoContenutoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9558535056L;
    //COLUMN tipo_procedimento
    private String tipoProcedimento;
    //COLUMN tipo_contenuto_id
    private String tipoContenutoId;

    public String getTipoProcedimento(){
        return this.tipoProcedimento;
    }

    public void setTipoProcedimento(String tipoProcedimento){
        this.tipoProcedimento = tipoProcedimento;
    }

    public String getTipoContenutoId(){
        return this.tipoContenutoId;
    }

    public void setTipoContenutoId(String tipoContenutoId){
        this.tipoContenutoId = tipoContenutoId;
    }


}
