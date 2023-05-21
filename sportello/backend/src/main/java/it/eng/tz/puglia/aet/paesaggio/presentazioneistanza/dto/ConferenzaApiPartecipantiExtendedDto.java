package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.cds.bean.ConferenzaApiPartecipantiDto;

/**
 * Bean per documentazione
 * @author Antonio La Gatta
 * @date 24 nov 2021
 */
public class ConferenzaApiPartecipantiExtendedDto extends ConferenzaApiPartecipantiDto{
	
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 */
	private static final long serialVersionUID = -7791418237240971037L;
	
	@JsonIgnore
	private String codice;
	@JsonIgnore
	private String codiceTipo;
	/**
	 * @return the codice
	 */
	public String getCodice() {
		return this.codice;
	}
	/**
	 * @param codice the codice to set
	 */
	public void setCodice(final String codice) {
		this.codice = codice;
	}
	/**
	 * @return the codiceTipo
	 */
	public String getCodiceTipo() {
		return this.codiceTipo;
	}
	/**
	 * @param codiceTipo the codiceTipo to set
	 */
	public void setCodiceTipo(final String codiceTipo) {
		this.codiceTipo = codiceTipo;
	}
	
}
