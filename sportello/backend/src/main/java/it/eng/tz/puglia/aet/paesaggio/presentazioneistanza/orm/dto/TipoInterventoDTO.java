package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipoInterventoDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.tipo_intervento
 */
public class TipoInterventoDTO implements Serializable{

    private static final long serialVersionUID = 1065950233L;
    //COLUMN codice
    private String codice;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN artt
    private String artt;
    //COLUMN data_approvazione
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataApprovazione;
    //COLUMN con
    private String con;
    
    public TipoInterventoDTO()
    {
    	super();
    }
    public TipoInterventoDTO(TipoInterventoDto feDto, UUID idPratica)
    {
    	this.codice = feDto.getTipologiaDiIntervento();
    	this.idPratica = idPratica;
    	this.artt = feDto.getInParticolareAgliArtt();
    	this.dataApprovazione = feDto.getDataApprovazione();
    	this.con = feDto.getCon();
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }

    public String getArtt(){
        return this.artt;
    }

    public void setArtt(String artt){
        this.artt = artt;
    }

    public Date getDataApprovazione(){
        return this.dataApprovazione;
    }

    public void setDataApprovazione(Date dataApprovazione){
        this.dataApprovazione = dataApprovazione;
    }

    public String getCon(){
        return this.con;
    }

    public void setCon(String con){
        this.con = con;
    }


}
