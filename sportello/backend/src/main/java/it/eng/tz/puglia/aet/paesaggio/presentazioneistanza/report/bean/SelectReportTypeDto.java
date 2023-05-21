package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;

public class SelectReportTypeDto implements Serializable {

	private static final long serialVersionUID = 7391843267515170935L;
	private PlainStringValueLabel labelValue;
	private Map<String, List<String>> formati;
	
	public PlainStringValueLabel getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(final PlainStringValueLabel labelValue) {
		this.labelValue = labelValue;
	}
	public Map<String, List<String>> getFormati() {
		return formati;
	}
	public void setFormati(final Map<String, List<String>> formati) {
		this.formati = formati;
	}
}
