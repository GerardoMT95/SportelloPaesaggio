package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.VincolisticaDto;

/**
 * DTO for table presentazione_istanza.vinc_arch
 */
public class VincArchDTO implements Serializable{

    private static final long serialVersionUID = 5900111526L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN vinc_arc_no_tutela
    private String vincArcNoTutela;
    //COLUMN vinc_arc_monum_diretto
    private String vincArcMonumDiretto;
    //COLUMN vinc_arc_monum_indiretto
    private String vincArcMonumIndiretto;
    //COLUMN vinc_arc_archeo_diretto
    private String vincArcArcheoDiretto;
    //COLUMN vinc_arc_archeo_indiretto
    private String vincArcArcheoIndiretto;
    //COLUMN altri_vincoli
    private String altriVincoli;


    public VincArchDTO() { }
    
    public VincArchDTO(VincolisticaDto dto) { 
    	this.praticaId = dto.getIdPratica();
    	
    	this.altriVincoli = dto.getAltriVincolo();
    	
			 if (dto.getSottopostoATutela()==null)	this.vincArcNoTutela = null;
		else if (dto.getSottopostoATutela()==true)  this.vincArcNoTutela = "S";
		else if (dto.getSottopostoATutela()==false) this.vincArcNoTutela = "N";
		else										this.vincArcNoTutela = null;
			 
		if (dto.getSpecificaVincolo()!=null && !dto.getSpecificaVincolo().isEmpty()) {
										this.vincArcMonumDiretto    = "N";
										this.vincArcMonumIndiretto  = "N";
										this.vincArcArcheoDiretto   = "N";
										this.vincArcArcheoIndiretto = "N";
			dto.getSpecificaVincolo().forEach(elem->{
				if (elem.getValue()==1) this.vincArcMonumDiretto    = "S";
				if (elem.getValue()==2) this.vincArcMonumIndiretto  = "S";
				if (elem.getValue()==3) this.vincArcArcheoDiretto   = "S";
				if (elem.getValue()==4) this.vincArcArcheoIndiretto = "S";
			});
		}
    }
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getVincArcNoTutela(){
        return this.vincArcNoTutela;
    }

    public void setVincArcNoTutela(String vincArcNoTutela){
        this.vincArcNoTutela = vincArcNoTutela;
    }

    public String getVincArcMonumDiretto(){
        return this.vincArcMonumDiretto;
    }

    public void setVincArcMonumDiretto(String vincArcMonumDiretto){
        this.vincArcMonumDiretto = vincArcMonumDiretto;
    }

    public String getVincArcMonumIndiretto(){
        return this.vincArcMonumIndiretto;
    }

    public void setVincArcMonumIndiretto(String vincArcMonumIndiretto){
        this.vincArcMonumIndiretto = vincArcMonumIndiretto;
    }

    public String getVincArcArcheoDiretto(){
        return this.vincArcArcheoDiretto;
    }

    public void setVincArcArcheoDiretto(String vincArcArcheoDiretto){
        this.vincArcArcheoDiretto = vincArcArcheoDiretto;
    }

    public String getVincArcArcheoIndiretto(){
        return this.vincArcArcheoIndiretto;
    }

    public void setVincArcArcheoIndiretto(String vincArcArcheoIndiretto){
        this.vincArcArcheoIndiretto = vincArcArcheoIndiretto;
    }

    public String getAltriVincoli(){
        return this.altriVincoli;
    }

    public void setAltriVincoli(String altriVincoli){
        this.altriVincoli = altriVincoli;
    }


}
