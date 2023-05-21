package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.List;

public class RequestAllegato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1969346010299425234L;
	private String  praticaId;
	/**
	 * in caso di documento riconoscimento allegato è il referente a cui fa capo,
	 * 
	 */
	private Long referenteId;
	/**
	 * avvalorata nel caso di documenti da uploadare diversi dal documento di riconoscimento di un referente. 
	 */
	private List<Integer> tipiContenuto;
	/**
	 * opzionale...avvalorata solo in caso di documentazione tecnica
	 */
	private String nomeContenuto;
	
	/**
	 * avvalorato nel caso di richiesta di cancellazione
	 */
	private String allegatoId;
	
	private Integer integrazioneId;
	
	/**
	 * indica che il file è firmato e quindi eventualmente vanno effettuati controlli
	 */
	private Boolean isSigned;
	
	/**
	 * se a true indica di settare il pratica.validazione_xxx  (allegati, istanza, scheda tecnica) a false
	 */
	private Boolean invalidaSezione;
	
	public String getPraticaId() {
		return praticaId;
	}
	public void setPraticaId(String praticaId) {
		this.praticaId = praticaId;
	}
	public Long getReferenteId() {
		return referenteId;
	}
	public void setReferenteId(Long referenteId) {
		this.referenteId = referenteId;
	}
	public List<Integer> getTipiContenuto() {
		return tipiContenuto;
	}
	public void setTipiContenuto(List<Integer> tipiContenuto) {
		this.tipiContenuto = tipiContenuto;
	}
	public String getNomeContenuto() {
		return nomeContenuto;
	}
	public void setNomeContenuto(String nomeContenuto) {
		this.nomeContenuto = nomeContenuto;
	}
	public String getAllegatoId() {
		return allegatoId;
	}
	public void setAllegatoId(String allegatoId) {
		this.allegatoId = allegatoId;
	}
	public Integer getIntegrazioneId()
	{
		return integrazioneId;
	}
	public void setIntegrazioneId(Integer integrazioneId)
	{
		this.integrazioneId = integrazioneId;
	}

	public Boolean getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	/**
	 * @return the invalidaSezione
	 */
	public Boolean getInvalidaSezione() {
		return invalidaSezione;
	}
	/**
	 * @param invalidaSezione the invalidaSezione to set
	 */
	public void setInvalidaSezione(Boolean invalidaSezione) {
		this.invalidaSezione = invalidaSezione;
	}
	
}
