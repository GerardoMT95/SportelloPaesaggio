package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.referenti_documento
 */
public class ReferentiDocumentoDTO implements Serializable{

    private static final long serialVersionUID = 6334860580L;
    //COLUMN referente_id
    @JsonIgnore
    private long referenteId;
    //COLUMN id_tipo
    @NotNull(message="Il tipo di documento non può essere nullo")
    private Integer idTipo;
    //COLUMN numero
    @NotNull(message="Il campo 'Numero' non può essere nullo")
	@NotBlank(message="Il campo 'Numero' non può essere nullo")
    private String numero;
    //COLUMN data_rilascio
    @NotNull(message="Il campo 'Data rilascio' non può essere nullo")
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataRilascio;
    //COLUMN ente_rilascio
    @NotNull(message="Il campo 'Ente rilascio' non può essere nullo")
	@NotBlank(message="Il campo 'Ente rilascio' non può essere nullo")
    private String enteRilascio;
    //COLUMN data_scadenza
    @NotNull(message="Il campo 'Data scadenza' non può essere nullo")
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataScadenza;
    //COLUMN allegato_id
    @JsonIgnore
    private UUID idAllegato;
    
    private AllegatiDTO docAllegato; //metadati del documento di riconoscimento
    
    
    public AllegatiDTO getDocAllegato() {
		return docAllegato;
	}
	public void setDocAllegato(AllegatiDTO docAllegato) {
		this.docAllegato = docAllegato;
	}
	public ReferentiDocumentoDTO() {}
    public ReferentiDocumentoDTO(long referenteId) {
		super();
		this.referenteId = referenteId;
	}

	public long getReferenteId(){
        return this.referenteId;
    }

    public void setReferenteId(long referenteId){
        this.referenteId = referenteId;
    }

    public Integer getIdTipo(){
        return this.idTipo;
    }

    public void setIdTipo(Integer idTipo){
        this.idTipo = idTipo;
    }

    public String getNumero(){
        return this.numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public Date getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public String getEnteRilascio(){
        return this.enteRilascio;
    }

    public void setEnteRilascio(String enteRilascio){
        this.enteRilascio = enteRilascio;
    }

    public Date getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza){
        this.dataScadenza = dataScadenza;
    }

	public UUID getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(UUID idAllegato) {
		this.idAllegato = idAllegato;
	}


}
