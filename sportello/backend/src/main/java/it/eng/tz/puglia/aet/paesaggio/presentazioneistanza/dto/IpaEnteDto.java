package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Ipa Ente
 * @author Antonio La Gatta
 */
public class IpaEnteDto implements Serializable {

	/**
	 * @author Antonio La Gatta
	 */
	private static final long serialVersionUID = 1995620252674514394L;
	
	@JsonIgnore
	private Long id;
	private String nome;
	private String codiceFiscale;
	private String codiceIpa;
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
	 * @return the codiceIpa
	 */
	public String getCodiceIpa() {
		return this.codiceIpa;
	}
	/**
	 * @param codiceIpa the codiceIpa to set
	 */
	public void setCodiceIpa(final String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}
	
}
