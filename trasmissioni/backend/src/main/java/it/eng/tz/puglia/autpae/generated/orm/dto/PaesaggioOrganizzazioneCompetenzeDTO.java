package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table common.paesaggio_organizzazione_competenze
 */
public class PaesaggioOrganizzazioneCompetenzeDTO implements Serializable{

    private static final long serialVersionUID = 1847722636L;
    //COLUMN id
    private Integer id;
    //COLUMN paesaggio_organizzazione_id
    private Integer paesaggioOrganizzazioneId;
    //COLUMN ente_id
    private Integer enteId;
    //COLUMN data_inizio_delega
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataInizioDelega;
    //COLUMN data_fine_delega
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataFineDelega;
    //COLUMN codice_civilia
    private String codiceCivilia;

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(Integer paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public Integer getEnteId(){
        return this.enteId;
    }

    public void setEnteId(Integer enteId){
        this.enteId = enteId;
    }

    public Date getDataInizioDelega(){
        return this.dataInizioDelega;
    }

    public void setDataInizioDelega(Date dataInizioDelega){
        this.dataInizioDelega = dataInizioDelega;
    }

    public Date getDataFineDelega(){
        return this.dataFineDelega;
    }

    public void setDataFineDelega(Date dataFineDelega){
        this.dataFineDelega = dataFineDelega;
    }

    public String getCodiceCivilia(){
        return this.codiceCivilia;
    }

    public void setCodiceCivilia(String codiceCivilia){
        this.codiceCivilia = codiceCivilia;
    }

	@Override
	public String toString() {
		return "PaesaggioOrganizzazioneCompetenzeDTO [id=" + id + ", paesaggioOrganizzazioneId="
				+ paesaggioOrganizzazioneId + ", enteId=" + enteId + ", dataInizioDelega=" + dataInizioDelega
				+ ", dataFineDelega=" + dataFineDelega + ", codiceCivilia=" + codiceCivilia + "]";
	}

    

}
