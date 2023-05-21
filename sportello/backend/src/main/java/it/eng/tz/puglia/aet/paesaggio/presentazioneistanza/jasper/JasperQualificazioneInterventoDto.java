package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperQualificazioneInterventoDto {

	private String text;
	private List<StringWrapper> subtext;
	
	public JasperQualificazioneInterventoDto() {
		super();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<StringWrapper> getSubtext() {
		return subtext;
	}
	public void setSubtext(List<StringWrapper> subtext) {
		this.subtext = subtext;
	}
	
}
