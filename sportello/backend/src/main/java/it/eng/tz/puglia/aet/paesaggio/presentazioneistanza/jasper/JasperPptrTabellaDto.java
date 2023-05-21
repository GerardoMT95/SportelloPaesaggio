package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperPptrTabellaDto {
	
	private String titolo;
	private String codice; // mi serve solo per lo smistamento alla creazione del report
	private List<JasperPptrContenutoTabellaDto> contenuto;
	
	public JasperPptrTabellaDto() {
		super();
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}
	public List<JasperPptrContenutoTabellaDto> getContenuto() {
		return contenuto;
	}
	public void setContenuto(List<JasperPptrContenutoTabellaDto> contenuto) {
		this.contenuto = contenuto;
	}
	
}
