package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.LegittimitaDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.leggittimita
 */
public class LeggittimitaDTO implements Serializable{

    private static final long serialVersionUID = 5187416448L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN leg_urb_tit_edilizio
    private String legUrbTitEdilizio;
    //COLUMN leg_urb_privo_specifica
    private String legUrbPrivoSpecifica;
    //COLUMN leg_paesag_immobile
    private String legPaesagImmobile;
    //COLUMN leg_pae_tipo_vincolo
    private String legPaeTipoVincolo;
    //COLUMN leg_pae_data_intervento
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date legPaeDataIntervento;
    //COLUMN leg_pae_data_vincolo
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date legPaeDataVincolo;

    
    public LeggittimitaDTO() { }
    
    public LeggittimitaDTO(LegittimitaDto leggittimita) {
    	this.praticaId = leggittimita.getIdPratica();
    	
    	if 		(leggittimita.getTipoLegittimitaUrbanistica()==null) this.legUrbTitEdilizio = null;
    	else if (leggittimita.getTipoLegittimitaUrbanistica()==1) 	 this.legUrbTitEdilizio = "PT";
    	else if (leggittimita.getTipoLegittimitaUrbanistica()==2) 	 this.legUrbTitEdilizio = "DT";
    	else 													  	 this.legUrbTitEdilizio = null;
    	
    	this.legUrbPrivoSpecifica = leggittimita.getLegittimitaUrbanstica();
    	
    		 if (leggittimita.getTipoLegittimitaPaesaggistica()==null) this.legPaesagImmobile = null;
    	else if (leggittimita.getTipoLegittimitaPaesaggistica()==1)    this.legPaesagImmobile = "PV";
    	else if (leggittimita.getTipoLegittimitaPaesaggistica()==2)    this.legPaesagImmobile = "AP";
    	else 														   this.legPaesagImmobile = null;
    	
    	if (leggittimita.getDettaglioLegittimitaPaesaggistica()!=null) {
	    	this.legPaeTipoVincolo = leggittimita.getDettaglioLegittimitaPaesaggistica().getTipologiaVincolo();
	    	this.legPaeDataIntervento = leggittimita.getDettaglioLegittimitaPaesaggistica().getDataIntervento();
	    	this.legPaeDataVincolo = leggittimita.getDettaglioLegittimitaPaesaggistica().getDataImposizioneVincolo();
    	}
    }
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getLegUrbTitEdilizio(){
        return this.legUrbTitEdilizio;
    }

    public void setLegUrbTitEdilizio(String legUrbTitEdilizio){
        this.legUrbTitEdilizio = legUrbTitEdilizio;
    }

    public String getLegUrbPrivoSpecifica(){
        return this.legUrbPrivoSpecifica;
    }

    public void setLegUrbPrivoSpecifica(String legUrbPrivoSpecifica){
        this.legUrbPrivoSpecifica = legUrbPrivoSpecifica;
    }

    public String getLegPaesagImmobile(){
        return this.legPaesagImmobile;
    }

    public void setLegPaesagImmobile(String legPaesagImmobile){
        this.legPaesagImmobile = legPaesagImmobile;
    }

    public String getLegPaeTipoVincolo(){
        return this.legPaeTipoVincolo;
    }

    public void setLegPaeTipoVincolo(String legPaeTipoVincolo){
        this.legPaeTipoVincolo = legPaeTipoVincolo;
    }

    public Date getLegPaeDataIntervento(){
        return this.legPaeDataIntervento;
    }

    public void setLegPaeDataIntervento(Date legPaeDataIntervento){
        this.legPaeDataIntervento = legPaeDataIntervento;
    }

    public Date getLegPaeDataVincolo(){
        return this.legPaeDataVincolo;
    }

    public void setLegPaeDataVincolo(Date legPaeDataVincolo){
        this.legPaeDataVincolo = legPaeDataVincolo;
    }


}
