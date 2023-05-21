package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.InquadramentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Inquadramento;

/**
 * DTO for table presentazione_istanza.inquadramento
 */
public class InquadramentoDTO implements Serializable{

    private static final long serialVersionUID = 1689512200L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN inq_dest_uso_interv
    private String inqDestUsoInterv;
    //COLUMN inq_dest_uso_interv_altro
    private String inqDestUsoIntervAltro;
    //COLUMN inq_dest_uso_suolo
    private String inqDestUsoSuolo;
    //COLUMN inq_dest_uso_suolo_altro
    private String inqDestUsoSuoloAltro;
    //COLUMN inq_contesto_paesag
    private String inqContestoPaesag;
    //COLUMN inq_contesto_paesag_altro
    private String inqContestoPaesagAltro;
    //COLUMN inq_morfologia_paesag
    private String inqMorfologiaPaesag;
    //COLUMN inq_morfologia_paesag_altro
    private String inqMorfologiaPaesagAltro;

    
    public InquadramentoDTO() { }
    
    
    public InquadramentoDTO(InquadramentoDto dto) { 
    	
    	this.praticaId = dto.getIdPratica();
    	
    		 if (dto.getDestinazioneUso()==null) this.inqDestUsoInterv=null;
    	else if (dto.getDestinazioneUso()==1) 	 this.inqDestUsoInterv=Inquadramento.RESIDENZIALE.name();
    	else if (dto.getDestinazioneUso()==2) 	 this.inqDestUsoInterv=Inquadramento.RICETTIVA_TURISTICA.name();
    	else if (dto.getDestinazioneUso()==3) 	 this.inqDestUsoInterv=Inquadramento.INDUSTRIALE_ARTIGIANALE.name();
    	else if (dto.getDestinazioneUso()==4) 	 this.inqDestUsoInterv=Inquadramento.AGRICOLO.name();
    	else if (dto.getDestinazioneUso()==5) 	 this.inqDestUsoInterv=Inquadramento.COMMERCIALE_DIREZIONALE.name();
    	else if (dto.getDestinazioneUso()==6) 	 this.inqDestUsoInterv=Inquadramento.ALTRO.name();
    	else 								  	 this.inqDestUsoInterv=null;
    		 
    		 if (dto.getContestoPaesaggInterv()==null) this.inqContestoPaesag=null;
    	else if (dto.getContestoPaesaggInterv()==1)    this.inqContestoPaesag=Inquadramento.CENTRO_E_NUCLEO_STORICO.name();
    	else if (dto.getContestoPaesaggInterv()==2)    this.inqContestoPaesag=Inquadramento.AREA_URBANA.name();
    	else if (dto.getContestoPaesaggInterv()==3)    this.inqContestoPaesag=Inquadramento.AREA_PERIURBANA.name();
    	else if (dto.getContestoPaesaggInterv()==4)    this.inqContestoPaesag=Inquadramento.AREA_AGRICOLA.name();
    	else if (dto.getContestoPaesaggInterv()==5)    this.inqContestoPaesag=Inquadramento.INSEDIAMENTO_RURALE.name();
    	else if (dto.getContestoPaesaggInterv()==6)    this.inqContestoPaesag=Inquadramento.AREA_NATURALE.name();
    	else if (dto.getContestoPaesaggInterv()==7)    this.inqContestoPaesag=Inquadramento.AREA_BOSCHIVA.name();
    	else if (dto.getContestoPaesaggInterv()==8)    this.inqContestoPaesag=Inquadramento.AMBITO_FLUVIALE.name();
    	else if (dto.getContestoPaesaggInterv()==9)    this.inqContestoPaesag=Inquadramento.AMBITO_LACUSTRE.name();
    	else if (dto.getContestoPaesaggInterv()==10)   this.inqContestoPaesag=Inquadramento.ALTRO.name();
    	else										   this.inqContestoPaesag=null;
    	
    		 if (dto.getMorfologiaConPaesag()==null) this.inqMorfologiaPaesag=null;
    	else if (dto.getMorfologiaConPaesag()==1) 	 this.inqMorfologiaPaesag=Inquadramento.COSTA.name();
    	else if (dto.getMorfologiaConPaesag()==2) 	 this.inqMorfologiaPaesag=Inquadramento.CRINALE.name();
    	else if (dto.getMorfologiaConPaesag()==3) 	 this.inqMorfologiaPaesag=Inquadramento.PIANURA.name();
    	else if (dto.getMorfologiaConPaesag()==4) 	 this.inqMorfologiaPaesag=Inquadramento.VERSANTE.name();
    	else if (dto.getMorfologiaConPaesag()==5) 	 this.inqMorfologiaPaesag=Inquadramento.ALTOPIANO_PROMONTORIO.name();
    	else if (dto.getMorfologiaConPaesag()==6) 	 this.inqMorfologiaPaesag=Inquadramento.PIANA_VALLIVA.name();
    	else if (dto.getMorfologiaConPaesag()==7) 	 this.inqMorfologiaPaesag=Inquadramento.ALTRO.name();
    	else 									  	 this.inqMorfologiaPaesag=null;
    		 
    	this.inqDestUsoIntervAltro    = dto.getDestinazioneUsoSpecifica();
    	this.inqContestoPaesagAltro   = dto.getContestoPaesaggIntervSpecifica();
    	this.inqMorfologiaPaesagAltro = dto.getMorfologiaConPaesagSpecifica();
    }
    
    
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getInqDestUsoInterv(){
        return this.inqDestUsoInterv;
    }

    public void setInqDestUsoInterv(String inqDestUsoInterv){
        this.inqDestUsoInterv = inqDestUsoInterv;
    }

    public String getInqDestUsoIntervAltro(){
        return this.inqDestUsoIntervAltro;
    }

    public void setInqDestUsoIntervAltro(String inqDestUsoIntervAltro){
        this.inqDestUsoIntervAltro = inqDestUsoIntervAltro;
    }

    public String getInqDestUsoSuolo(){
        return this.inqDestUsoSuolo;
    }

    public void setInqDestUsoSuolo(String inqDestUsoSuolo){
        this.inqDestUsoSuolo = inqDestUsoSuolo;
    }

    public String getInqDestUsoSuoloAltro(){
        return this.inqDestUsoSuoloAltro;
    }

    public void setInqDestUsoSuoloAltro(String inqDestUsoSuoloAltro){
        this.inqDestUsoSuoloAltro = inqDestUsoSuoloAltro;
    }

    public String getInqContestoPaesag(){
        return this.inqContestoPaesag;
    }

    public void setInqContestoPaesag(String inqContestoPaesag){
        this.inqContestoPaesag = inqContestoPaesag;
    }

    public String getInqContestoPaesagAltro(){
        return this.inqContestoPaesagAltro;
    }

    public void setInqContestoPaesagAltro(String inqContestoPaesagAltro){
        this.inqContestoPaesagAltro = inqContestoPaesagAltro;
    }

    public String getInqMorfologiaPaesag(){
        return this.inqMorfologiaPaesag;
    }

    public void setInqMorfologiaPaesag(String inqMorfologiaPaesag){
        this.inqMorfologiaPaesag = inqMorfologiaPaesag;
    }

    public String getInqMorfologiaPaesagAltro(){
        return this.inqMorfologiaPaesagAltro;
    }

    public void setInqMorfologiaPaesagAltro(String inqMorfologiaPaesagAltro){
        this.inqMorfologiaPaesagAltro = inqMorfologiaPaesagAltro;
    }


}
