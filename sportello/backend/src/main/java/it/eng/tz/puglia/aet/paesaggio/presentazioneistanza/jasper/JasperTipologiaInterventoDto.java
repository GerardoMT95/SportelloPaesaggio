package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;

public class JasperTipologiaInterventoDto {
	
	private String tipologiaDiIntervento;
	private String inParticolareAgliArtt;
	private Date dataApprovazione;
	private String con;
	
	public JasperTipologiaInterventoDto() {
		super();
	}
	
	public String getTipologiaDiIntervento() {
		return tipologiaDiIntervento;
	}
	public void setTipologiaDiIntervento(String tipologiaDiIntervento) {
		this.tipologiaDiIntervento = tipologiaDiIntervento;
	}
	public String getInParticolareAgliArtt() {
		return inParticolareAgliArtt;
	}
	public void setInParticolareAgliArtt(String inParticolareAgliArtt) {
		this.inParticolareAgliArtt = inParticolareAgliArtt;
	}
	public Date getDataApprovazione() {
		return dataApprovazione;
	}
	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	
}
