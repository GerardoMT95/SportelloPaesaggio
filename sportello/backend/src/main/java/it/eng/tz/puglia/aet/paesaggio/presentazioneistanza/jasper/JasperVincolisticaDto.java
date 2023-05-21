package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperVincolisticaDto {
	
	private Boolean sottopostoATutela;
    private List<StringWrapper> specificaVincolo;
    private String altriVincolo;
    
	public JasperVincolisticaDto() {
		super();
	}
	
	public Boolean getSottopostoATutela() {
		return sottopostoATutela;
	}
	public void setSottopostoATutela(Boolean sottopostoATutela) {
		this.sottopostoATutela = sottopostoATutela;
	}
	public List<StringWrapper> getSpecificaVincolo() {
		return specificaVincolo;
	}
	public void setSpecificaVincolo(List<StringWrapper> specificaVincolo) {
		this.specificaVincolo = specificaVincolo;
	}
	public String getAltriVincolo() {
		return altriVincolo;
	}
	public void setAltriVincolo(String altriVincolo) {
		this.altriVincolo = altriVincolo;
	}

}
