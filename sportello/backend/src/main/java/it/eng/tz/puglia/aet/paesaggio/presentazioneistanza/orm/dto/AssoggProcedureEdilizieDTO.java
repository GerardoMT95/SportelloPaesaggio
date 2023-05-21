package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedureEdilizieDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.assogg_procedure_edilizie
 */
public class AssoggProcedureEdilizieDTO implements Serializable{

    private static final long serialVersionUID = 8343728769L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN flag_assoggettato
    private String flagAssoggettato;
    //COLUMN no_assogg_specificare
    private String noAssoggSpecificare;
    //COLUMN assogg_flag_pratica_in_corso
    private String assoggFlagPraticaInCorso;
    //COLUMN assogg_ente_pratica_in_corso
    private String assoggEntePraticaInCorso;
    //COLUMN assogg_datapr_pratica_in_corso
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date assoggDataprPraticaInCorso;
    //COLUMN assogg_flag_parere_urb_espr
    private String assoggFlagParereUrbEspr;
    //COLUMN assogg_data_approv_pratica
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date assoggDataApprovPratica;

    
    public AssoggProcedureEdilizieDTO() { }
    
    public AssoggProcedureEdilizieDTO(ProcedureEdilizieDto dto) { 
    	
    	this.praticaId = dto.getIdPratica();
    	
			 if (dto.getTipoIntervento()==null) this.flagAssoggettato = null;
		else if (dto.getTipoIntervento()==1) 	this.flagAssoggettato = "N";
		else if (dto.getTipoIntervento()==2) 	this.flagAssoggettato = "S";
		else 									this.flagAssoggettato = null;
			 
		if (dto.getTipoStato()!=null && !dto.getTipoStato().isEmpty()) {
			this.assoggFlagPraticaInCorso="N";
			this.assoggFlagParereUrbEspr ="N";
			dto.getTipoStato().forEach(elem->{
				if (elem.getValue()==1) this.assoggFlagPraticaInCorso = "S";
				if (elem.getValue()==2) this.assoggFlagParereUrbEspr  = "S";
			});
		}
		
		this.noAssoggSpecificare  		= dto.getMotivazione();
		this.assoggEntePraticaInCorso   = dto.getDetagglio();
		this.assoggDataprPraticaInCorso = dto.getPressoData();
		this.assoggDataApprovPratica 	= dto.getEspressoData();
    }
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getFlagAssoggettato(){
        return this.flagAssoggettato;
    }

    public void setFlagAssoggettato(String flagAssoggettato){
        this.flagAssoggettato = flagAssoggettato;
    }

    public String getNoAssoggSpecificare(){
        return this.noAssoggSpecificare;
    }

    public void setNoAssoggSpecificare(String noAssoggSpecificare){
        this.noAssoggSpecificare = noAssoggSpecificare;
    }

    public String getAssoggFlagPraticaInCorso(){
        return this.assoggFlagPraticaInCorso;
    }

    public void setAssoggFlagPraticaInCorso(String assoggFlagPraticaInCorso){
        this.assoggFlagPraticaInCorso = assoggFlagPraticaInCorso;
    }

    public String getAssoggEntePraticaInCorso(){
        return this.assoggEntePraticaInCorso;
    }

    public void setAssoggEntePraticaInCorso(String assoggEntePraticaInCorso){
        this.assoggEntePraticaInCorso = assoggEntePraticaInCorso;
    }

    public Date getAssoggDataprPraticaInCorso(){
        return this.assoggDataprPraticaInCorso;
    }

    public void setAssoggDataprPraticaInCorso(Date assoggDataprPraticaInCorso){
        this.assoggDataprPraticaInCorso = assoggDataprPraticaInCorso;
    }

    public String getAssoggFlagParereUrbEspr(){
        return this.assoggFlagParereUrbEspr;
    }

    public void setAssoggFlagParereUrbEspr(String assoggFlagParereUrbEspr){
        this.assoggFlagParereUrbEspr = assoggFlagParereUrbEspr;
    }

    public Date getAssoggDataApprovPratica(){
        return this.assoggDataApprovPratica;
    }

    public void setAssoggDataApprovPratica(Date assoggDataApprovPratica){
        this.assoggDataApprovPratica = assoggDataApprovPratica;
    }

}