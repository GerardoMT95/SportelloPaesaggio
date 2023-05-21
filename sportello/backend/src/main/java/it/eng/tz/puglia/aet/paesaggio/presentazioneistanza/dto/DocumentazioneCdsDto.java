package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DocumentazioneCdsDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 */
	private static final long serialVersionUID = 7682273554896502054L;
	
	
	private Long id;
	private Long idConferenza;
	private String riferimentoConferenza;
	private String statoConferenza;
	private String nome;
	private String categoria;
	private String tipo;
	private String protocollo;
	private String dataProtocollo;
	private java.util.Date dataCreazione;
	private String idTipoDocumento;
	private String tipoDocumento;
	@JsonIgnore
	private String cmisId;
	@JsonIgnore
	private UUID idTipoCaricaDocumento;
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
	 * @return the categoria
	 */
	public String getCategoria() {
		return this.categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(final String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return this.tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the protocollo
	 */
	public String getProtocollo() {
		return this.protocollo;
	}
	/**
	 * @param protocollo the protocollo to set
	 */
	public void setProtocollo(final String protocollo) {
		this.protocollo = protocollo;
	}
	/**
	 * @return the dataProtocollo
	 */
	public String getDataProtocollo() {
		return this.dataProtocollo;
	}
	/**
	 * @param dataProtocollo the dataProtocollo to set
	 */
	public void setDataProtocollo(final String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
	/**
	 * @return the idTipoDocumento
	 */
	public String getIdTipoDocumento() {
		return this.idTipoDocumento;
	}
	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(final String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(final String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the cmisId
	 */
	public String getCmisId() {
		return this.cmisId;
	}
	/**
	 * @param cmisId the cmisId to set
	 */
	public void setCmisId(final String cmisId) {
		this.cmisId = cmisId;
	}
	/**
	 * @return the dataCreazione
	 */
	public java.util.Date getDataCreazione() {
		return this.dataCreazione;
	}
	/**
	 * @param dataCreazione the dataCreazione to set
	 */
	public void setDataCreazione(final java.util.Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	/**
	 * @return the idTipoCaricaDocumento
	 */
	public UUID getIdTipoCaricaDocumento() {
		return this.idTipoCaricaDocumento;
	}
	/**
	 * @param idTipoCaricaDocumento the idTipoCaricaDocumento to set
	 */
	public void setIdTipoCaricaDocumento(final UUID idTipoCaricaDocumento) {
		this.idTipoCaricaDocumento = idTipoCaricaDocumento;
	}
	/**
	 * @return the idConferenza
	 */
	public Long getIdConferenza() {
		return this.idConferenza;
	}
	/**
	 * @param idConferenza the idConferenza to set
	 */
	public void setIdConferenza(final Long idConferenza) {
		this.idConferenza = idConferenza;
	}
	/**
	 * @return the statoConferenza
	 */
	public String getStatoConferenza() {
		return this.statoConferenza;
	}
	/**
	 * @param statoConferenza the statoConferenza to set
	 */
	public void setStatoConferenza(final String statoConferenza) {
		this.statoConferenza = statoConferenza;
	}
	/**
	 * @return the riferimentoConferenza
	 */
	public String getRiferimentoConferenza() {
		return this.riferimentoConferenza;
	}
	/**
	 * @param riferimentoConferenza the riferimentoConferenza to set
	 */
	public void setRiferimentoConferenza(final String riferimentoConferenza) {
		this.riferimentoConferenza = riferimentoConferenza;
	}
}
