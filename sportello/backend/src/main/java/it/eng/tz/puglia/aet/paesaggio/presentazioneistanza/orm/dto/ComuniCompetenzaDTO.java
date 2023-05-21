package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO for table presentazione_istanza.comuni_competenza
 */
public class ComuniCompetenzaDTO implements Serializable{

    private static final long serialVersionUID = 832737091L;
    //COLUMN pratica_id
    @JsonIgnore
    private UUID praticaId;
    //COLUMN ente_id
    private int enteId;
    //COLUMN data_inserimento
    private Timestamp dataInserimento;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN cod_cat
    private String codCat;
    //COLUMN cod_istat
    private String codIstat;
    //COLUMN vincolo_art_38
    private String  vincoloArt38;
    //COLUMN vincolo_art_100
    private String vincoloArt100;
    //COLUMN notifica
    private Boolean notifica;
    
    
    
    public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getCodCat() {
		return codCat;
	}


	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}


	public String getCodIstat() {
		return codIstat;
	}


	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}


	public ComuniCompetenzaDTO() {
    	super();
    }
    
    
    public ComuniCompetenzaDTO(UUID praticaId, int enteId) {
		super();
		this.praticaId = praticaId;
		this.enteId = enteId;
		this.dataInserimento=new Timestamp(System.currentTimeMillis());
	}

	public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public int getEnteId(){
        return this.enteId;
    }

    public void setEnteId(int enteId){
        this.enteId = enteId;
    }

    public Timestamp getDataInserimento(){
        return this.dataInserimento;
    }

    public void setDataInserimento(Timestamp dataInserimento){
        this.dataInserimento = dataInserimento;
    }


	public String getVincoloArt38() {
		return vincoloArt38;
	}


	public void setVincoloArt38(String vincoloArt38) {
		this.vincoloArt38 = vincoloArt38;
	}


	public String getVincoloArt100() {
		return vincoloArt100;
	}


	public void setVincoloArt100(String vincoloArt100) {
		this.vincoloArt100 = vincoloArt100;
	}


	public Boolean getNotifica() {
		return notifica;
	}


	public void setNotifica(Boolean notifica) {
		this.notifica = notifica;
	}


}
