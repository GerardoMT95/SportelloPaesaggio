package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedimentoContenziosoDto;

/**
 * DTO for table presentazione_istanza.procedimenti_contenzioso
 */
public class ProcedimentiContenziosoDTO implements Serializable{

    private static final long serialVersionUID = 49456110L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN flag_contenzioso_in_atto
    private String flagContenziosoInAtto;
    //COLUMN descrizione
    private String descrizione;

    public ProcedimentiContenziosoDTO() { }

    public ProcedimentiContenziosoDTO(ProcedimentoContenziosoDto dto) { 
    	this.praticaId = dto.getIdPratica();
    		 if (dto.getContenzisoAtto()==null ) this.flagContenziosoInAtto = null;
    	else if (dto.getContenzisoAtto()==true ) this.flagContenziosoInAtto = "S";
    	else if (dto.getContenzisoAtto()==false) this.flagContenziosoInAtto = "N";
    	else								 	 this.flagContenziosoInAtto = null;
    	this.descrizione = dto.getDescrizione();
    }
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getFlagContenziosoInAtto(){
        return this.flagContenziosoInAtto;
    }

    public void setFlagContenziosoInAtto(String flagContenziosoInAtto){
        this.flagContenziosoInAtto = flagContenziosoInAtto;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }


}
