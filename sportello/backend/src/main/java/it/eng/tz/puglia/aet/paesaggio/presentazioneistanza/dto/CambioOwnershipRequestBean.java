package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
/**
 * Bean di request per cambio ownership
 * @author Antonio La Gatta
 * @date 6 lug 2021
 */
public class CambioOwnershipRequestBean implements Serializable{

	
	/**
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 */
	private static final long serialVersionUID = -3132160018744273197L;
	
	
	private String codicePratica;
	private String codiceFiscaleProponente;
	private String codiceSegreto;
	/**
	 * @return the codicePratica
	 */
	public String getCodicePratica() {
		return codicePratica;
	}
	/**
	 * @param codicePratica the codicePratica to set
	 */
	public void setCodicePratica(final String codicePratica) {
		this.codicePratica = codicePratica;
	}
	/**
	 * @return the codiceFiscaleProponente
	 */
	public String getCodiceFiscaleProponente() {
		return codiceFiscaleProponente;
	}
	/**
	 * @param codiceFiscaleProponente the codiceFiscaleProponente to set
	 */
	public void setCodiceFiscaleProponente(final String codiceFiscaleProponente) {
		this.codiceFiscaleProponente = codiceFiscaleProponente;
	}
	/**
	 * @return the codiceSegreto
	 */
	public String getCodiceSegreto() {
		return codiceSegreto;
	}
	/**
	 * @param codiceSegreto the codiceSegreto to set
	 */
	public void setCodiceSegreto(final String codiceSegreto) {
		this.codiceSegreto = codiceSegreto;
	}
}
