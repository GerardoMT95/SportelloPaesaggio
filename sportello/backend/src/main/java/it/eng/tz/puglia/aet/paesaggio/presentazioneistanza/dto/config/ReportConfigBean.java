package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config;

import java.io.Serializable;

public class ReportConfigBean implements Serializable {

	private static final long serialVersionUID = -12026797722635866L;
	private String reportMaxTime;
	private String reportMailOggetto;
	private String reportMailTesto;
	
	public String getReportMaxTime() {
		return reportMaxTime;
	}
	public void setReportMaxTime(String reportMaxTime) {
		this.reportMaxTime = reportMaxTime;
	}
	public String getReportMailOggetto() {
		return reportMailOggetto;
	}
	public void setReportMailOggetto(String reportMailOggetto) {
		this.reportMailOggetto = reportMailOggetto;
	}
	public String getReportMailTesto() {
		return reportMailTesto;
	}
	public void setReportMailTesto(String reportMailTesto) {
		this.reportMailTesto = reportMailTesto;
	}
	
	
	
}
