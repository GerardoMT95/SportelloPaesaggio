package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

/**
 * Dtro per recupero ente
 * @author Antonio La Gatta
 * @date 18 mar 2022
 */
public class EnteDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 */
	private static final long serialVersionUID = 876250333377315314L;
	
	private String codice;
	private String codiceFiscale;
	private String nome;
	private String pec;
	private String tipoEnte;
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
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}
	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(final String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return this.nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(final String nome) {
		this.nome = nome;
	}
	/**
	 * @return the pec
	 */
	public String getPec() {
		return this.pec;
	}
	/**
	 * @param pec the pec to set
	 */
	public void setPec(final String pec) {
		this.pec = pec;
	}
	/**
	 * @return the tipoEnte
	 */
	public String getTipoEnte() {
		return this.tipoEnte;
	}
	/**
	 * @param tipoEnte the tipoEnte to set
	 */
	public void setTipoEnte(final String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}
	
}
