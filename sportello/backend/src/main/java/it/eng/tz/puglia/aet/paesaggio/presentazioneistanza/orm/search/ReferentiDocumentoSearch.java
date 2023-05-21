package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.referenti_documento
 */
public class ReferentiDocumentoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5564193953L;
    //COLUMN referente_id
    private String referenteId;
    //COLUMN id_tipo
    private String idTipo;
    //COLUMN numero
    private String numero;
    //COLUMN data_rilascio
    private String dataRilascio;
    //COLUMN ente_rilascio
    private String enteRilascio;
    //COLUMN data_scadenza
    private String dataScadenza;
    //COLUMN 
    private String idAllegato;
    
    
    public String getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(String idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getReferenteId(){
        return this.referenteId;
    }

    public void setReferenteId(String referenteId){
        this.referenteId = referenteId;
    }

    public String getIdTipo(){
        return this.idTipo;
    }

    public void setIdTipo(String idTipo){
        this.idTipo = idTipo;
    }

    public String getNumero(){
        return this.numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(String dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public String getEnteRilascio(){
        return this.enteRilascio;
    }

    public void setEnteRilascio(String enteRilascio){
        this.enteRilascio = enteRilascio;
    }

    public String getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(String dataScadenza){
        this.dataScadenza = dataScadenza;
    }

    

}
