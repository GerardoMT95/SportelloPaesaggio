package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;

public class JasperProcedureEdilizieDto {
	
	private Integer tipoIntervento;
	private Boolean primaCheckbox;
	private Boolean secondaCheckbox;
	private String motivazione;
	private String detagglio;
	private Date pressoData;
	private Date espressoData;
	
	public JasperProcedureEdilizieDto() {
		super();
	}
	
	public Integer getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(Integer tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	
	public Boolean getPrimaCheckbox() {
		return primaCheckbox;
	}
	public void setPrimaCheckbox(Boolean primaCheckbox) {
		this.primaCheckbox = primaCheckbox;
	}

	public Boolean getSecondaCheckbox() {
		return secondaCheckbox;
	}

	public void setSecondaCheckbox(Boolean secondaCheckbox) {
		this.secondaCheckbox = secondaCheckbox;
	}

	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public String getDetagglio() {
		return detagglio;
	}
	public void setDetagglio(String detagglio) {
		this.detagglio = detagglio;
	}
	public Date getPressoData() {
		return pressoData;
	}
	public void setPressoData(Date pressoData) {
		this.pressoData = pressoData;
	}
	public Date getEspressoData() {
		return espressoData;
	}
	public void setEspressoData(Date espressoData) {
		this.espressoData = espressoData;
	}
	
}
