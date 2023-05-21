package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DestinazioneUrbanisticaDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.destinazione_urbanistica_intervento
 */
public class DestinazioneUrbanisticaInterventoDTO implements Serializable{

    private static final long serialVersionUID = 2496911238L;
    
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN comune_id
    private long comuneId;
    //COLUMN id
    private int id;
    //COLUMN strum_urb_approvato
    private String strumUrbApprovato;
    //COLUMN strum_urb_approvato_data
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date strumUrbApprovatoData;
    //COLUMN strum_urb_approvato_atto
    private String strumUrbApprovatoAtto;
    //COLUMN destin_area_str_vig
    private String destinAreaStrVig;
    //COLUMN strum_approv_ult_tutele
    private String strumApprovUltTutele;
    //COLUMN strum_urb_adottato
    private String strumUrbAdottato;
    //COLUMN strum_urb_adottato_data
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date strumUrbAdottatoData;
    //COLUMN strum_urb_adottato_atto
    private String strumUrbAdottatoAtto;
    //COLUMN destin_area_str_adott
    private String destinAreaStrAdott;
    //COLUMN strum_adott_ult_tutele
    private String strumAdottUltTutele;
    //COLUMN conforme_discipl_urb_vigente
    private String conformeDisciplUrbVigente;
    //COLUMN check_presa_visione
    private String checkPresaVisione;
    //COLUMN id_strum_urban_art38
    private Long idStrumUrbanArt38;
    //COLUMN id_strum_urban_art100
    private Long idStrumUrbanArt100;

    
    public DestinazioneUrbanisticaInterventoDTO() { }
    
    public DestinazioneUrbanisticaInterventoDTO(DestinazioneUrbanisticaDto dto, int id, Long idStrumUrbanArt38, Long idStrumUrbanArt100) { 
    	this.praticaId = dto.getIdPratica();
    	this.comuneId  = dto.getComuneId();
    	this.id		   = id;
    	
			 if (dto.getStrumentoUrbanistico()==null) this.strumUrbApprovato = null;
		else if (dto.getStrumentoUrbanistico()==1) 	  this.strumUrbApprovato = "PUG";
		else if (dto.getStrumentoUrbanistico()==2) 	  this.strumUrbApprovato = "PRG";
		else if (dto.getStrumentoUrbanistico()==3) 	  this.strumUrbApprovato = "Pdf";
		else										  this.strumUrbApprovato = null;
		this.strumUrbApprovatoData = dto.getApprovatoData();
		this.strumUrbApprovatoAtto = dto.getApprovatoCon();
		this.destinAreaStrVig 	   = dto.getDestinazioneUrbanistica();
		this.strumApprovUltTutele  = dto.getUlterioriTutele();
		if (dto.getConfermaCoerenza()==null) 	   	  this.checkPresaVisione = null;
		else if (dto.getConfermaCoerenza()==true)  	  this.checkPresaVisione = "S";
		else if (dto.getConfermaCoerenza()==false) 	  this.checkPresaVisione = "N";
		else									   	  this.checkPresaVisione = null;
		
			 if (dto.getStrumentoInAdozione()==null)  this.strumUrbAdottato = null;
		else if (dto.getStrumentoInAdozione()==1) 	  this.strumUrbAdottato = "NO";
		else if (dto.getStrumentoInAdozione()==2) 	  this.strumUrbAdottato = "PUG";
		else if (dto.getStrumentoInAdozione()==3) 	  this.strumUrbAdottato = "VAR";
		else										  this.strumUrbAdottato = null;
		this.strumUrbAdottatoData = dto.getAdottatoData();
		this.strumUrbAdottatoAtto = dto.getAdottatoCon();
		this.destinAreaStrAdott   = dto.getDestinazioneUrbanisticaAdottato();
		this.strumAdottUltTutele  = dto.getUlterioriTuteleAdottato();
		
		if (dto.getConformitaStrumentoUrbanistico()==null)		 this.conformeDisciplUrbVigente = null;
		else if (dto.getConformitaStrumentoUrbanistico()==true ) this.conformeDisciplUrbVigente = "S";
		else if (dto.getConformitaStrumentoUrbanistico()==false) this.conformeDisciplUrbVigente = "N";
		else													 this.conformeDisciplUrbVigente = null;
		
		this.idStrumUrbanArt38  = idStrumUrbanArt38;
		this.idStrumUrbanArt100 = idStrumUrbanArt100;
	}
    
    
    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public long getComuneId(){
        return this.comuneId;
    }

    public void setComuneId(long comuneId){
        this.comuneId = comuneId;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getStrumUrbApprovato(){
        return this.strumUrbApprovato;
    }

    public void setStrumUrbApprovato(String strumUrbApprovato){
        this.strumUrbApprovato = strumUrbApprovato;
    }

    public Date getStrumUrbApprovatoData(){
        return this.strumUrbApprovatoData;
    }

    public void setStrumUrbApprovatoData(Date strumUrbApprovatoData){
        this.strumUrbApprovatoData = strumUrbApprovatoData;
    }

    public String getStrumUrbApprovatoAtto(){
        return this.strumUrbApprovatoAtto;
    }

    public void setStrumUrbApprovatoAtto(String strumUrbApprovatoAtto){
        this.strumUrbApprovatoAtto = strumUrbApprovatoAtto;
    }

    public String getDestinAreaStrVig(){
        return this.destinAreaStrVig;
    }

    public void setDestinAreaStrVig(String destinAreaStrVig){
        this.destinAreaStrVig = destinAreaStrVig;
    }

    public String getStrumApprovUltTutele(){
        return this.strumApprovUltTutele;
    }

    public void setStrumApprovUltTutele(String strumApprovUltTutele){
        this.strumApprovUltTutele = strumApprovUltTutele;
    }

    public String getStrumUrbAdottato(){
        return this.strumUrbAdottato;
    }

    public void setStrumUrbAdottato(String strumUrbAdottato){
        this.strumUrbAdottato = strumUrbAdottato;
    }

    public Date getStrumUrbAdottatoData(){
        return this.strumUrbAdottatoData;
    }

    public void setStrumUrbAdottatoData(Date strumUrbAdottatoData){
        this.strumUrbAdottatoData = strumUrbAdottatoData;
    }

    public String getStrumUrbAdottatoAtto(){
        return this.strumUrbAdottatoAtto;
    }

    public void setStrumUrbAdottatoAtto(String strumUrbAdottatoAtto){
        this.strumUrbAdottatoAtto = strumUrbAdottatoAtto;
    }

    public String getDestinAreaStrAdott(){
        return this.destinAreaStrAdott;
    }

    public void setDestinAreaStrAdott(String destinAreaStrAdott){
        this.destinAreaStrAdott = destinAreaStrAdott;
    }

    public String getStrumAdottUltTutele(){
        return this.strumAdottUltTutele;
    }

    public void setStrumAdottUltTutele(String strumAdottUltTutele){
        this.strumAdottUltTutele = strumAdottUltTutele;
    }

    public String getConformeDisciplUrbVigente(){
        return this.conformeDisciplUrbVigente;
    }

    public void setConformeDisciplUrbVigente(String conformeDisciplUrbVigente){
        this.conformeDisciplUrbVigente = conformeDisciplUrbVigente;
    }

    public String getCheckPresaVisione(){
        return this.checkPresaVisione;
    }

    public void setCheckPresaVisione(String checkPresaVisione){
        this.checkPresaVisione = checkPresaVisione;
    }

    public Long getIdStrumUrbanArt38(){
        return this.idStrumUrbanArt38;
    }

    public void setIdStrumUrbanArt38(Long idStrumUrbanArt38){
        this.idStrumUrbanArt38 = idStrumUrbanArt38;
    }

    public Long getIdStrumUrbanArt100(){
        return this.idStrumUrbanArt100;
    }

    public void setIdStrumUrbanArt100(Long idStrumUrbanArt100){
        this.idStrumUrbanArt100 = idStrumUrbanArt100;
    }

}