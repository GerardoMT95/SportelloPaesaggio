package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.dichiarazione
 */
public class DichiarazioneDTO implements Serializable{

    private static final long serialVersionUID = 1095729363L;
    
    //COLUMN id
    private long id;
    //COLUMN testo
    private String testo;
    //COLUMN label_cb
    private String labelCb;
    //COLUMN data_inizio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataInizio;
    //COLUMN data_fine
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataFine;
    private int idProcedimento;

    
    public DichiarazioneDTO() { }
    
    
    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTesto(){
        return this.testo;
    }

    public void setTesto(String testo){
        this.testo = testo;
    }

    public String getLabelCb(){
        return this.labelCb;
    }

    public void setLabelCb(String labelCb){
        this.labelCb = labelCb;
    }

    public Date getDataInizio(){
        return this.dataInizio;
    }

    public void setDataInizio(Date dataInizio){
        this.dataInizio = dataInizio;
    }

    public Date getDataFine(){
        return this.dataFine;
    }

    public void setDataFine(Date dataFine){
        this.dataFine = dataFine;
    }


	public int getIdProcedimento() {
		return idProcedimento;
	}


	public void setIdProcedimento(int idProcedimento) {
		this.idProcedimento = idProcedimento;
	}


}
