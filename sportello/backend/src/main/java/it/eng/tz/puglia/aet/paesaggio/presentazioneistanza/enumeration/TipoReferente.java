package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

import java.util.List;

public enum TipoReferente {
	/**
	 * Tecnico Redattore
	 */
	TR("TR"),
	/**
	 * //Soggetto Dichiarante
	 */
	SD("SD"),
	/**
	 * Altro Titolare
	 */
	AT("AT");  
	
	private String value;
	
	private TipoReferente(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
	/**
	 *
	 * @author acolaianni
	 *
	 * @return lista dei tipi referente ammessi come input sui disclaimer
	 */
	public static List<String> tipiReferenteDisclaimer() {
		return List.of(TipoReferente.SD.name(),TipoReferente.TR.name());
	}
  
}
