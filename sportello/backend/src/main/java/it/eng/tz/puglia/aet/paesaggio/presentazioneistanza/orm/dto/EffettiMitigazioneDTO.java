package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.EffettiMitigazioneDto;

/**
 * DTO for table presentazione_istanza.effetti_mitigazione
 */
public class EffettiMitigazioneDTO implements Serializable{

    private static final long serialVersionUID = 3488174480L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN descr_stato_attuale
    private String descrStatoAttuale;
    //COLUMN effetti_realiz_opera
    private String effettiRealizOpera;
    //COLUMN mitigazione_imp_interv
    private String mitigazioneImpInterv;
    //COLUMN indicaz_contenuti_percettivi
    private String indicazContenutiPercettivi;

    
    public EffettiMitigazioneDTO() { }

    
    public EffettiMitigazioneDTO(EffettiMitigazioneDto dto) { 
    	this.praticaId = dto.getIdPratica();
    	this.descrStatoAttuale = dto.getDescrizione();
    	this.effettiRealizOpera = dto.getEffeti();
    	this.mitigazioneImpInterv = dto.getMisure();
    	this.indicazContenutiPercettivi = dto.getContenutiPercettivi();
    }
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getDescrStatoAttuale(){
        return this.descrStatoAttuale;
    }

    public void setDescrStatoAttuale(String descrStatoAttuale){
        this.descrStatoAttuale = descrStatoAttuale;
    }

    public String getEffettiRealizOpera(){
        return this.effettiRealizOpera;
    }

    public void setEffettiRealizOpera(String effettiRealizOpera){
        this.effettiRealizOpera = effettiRealizOpera;
    }

    public String getMitigazioneImpInterv(){
        return this.mitigazioneImpInterv;
    }

    public void setMitigazioneImpInterv(String mitigazioneImpInterv){
        this.mitigazioneImpInterv = mitigazioneImpInterv;
    }

    public String getIndicazContenutiPercettivi(){
        return this.indicazContenutiPercettivi;
    }

    public void setIndicazContenutiPercettivi(String indicazContenutiPercettivi){
        this.indicazContenutiPercettivi = indicazContenutiPercettivi;
    }


}
