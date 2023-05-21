package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * resulset dei documenti da inviare a diogene
 * @author Adriano Colaianni
 * @date 13 ott 2021
 */
public class AllegatoDiogeneInfoDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String contenuto;
	private String numeroProtocolloIn;
	private Date dataProtocolloIn;
	private String numeroProtocolloout;
	private Date dataProtocolloOut;
	private String codiceFascicolo;
	private Date dataTrasmissione;
	private String orgCreazione;
	private String types; //tipi_allegato separati da ,
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public String getNumeroProtocolloIn() {
		return numeroProtocolloIn;
	}
	public void setNumeroProtocolloIn(String numeroProtocolloIn) {
		this.numeroProtocolloIn = numeroProtocolloIn;
	}
	public Date getDataProtocolloIn() {
		return dataProtocolloIn;
	}
	public void setDataProtocolloIn(Date dataProtocolloIn) {
		this.dataProtocolloIn = dataProtocolloIn;
	}
	public String getNumeroProtocolloout() {
		return numeroProtocolloout;
	}
	public void setNumeroProtocolloout(String numeroProtocolloout) {
		this.numeroProtocolloout = numeroProtocolloout;
	}
	public Date getDataProtocolloOut() {
		return dataProtocolloOut;
	}
	public void setDataProtocolloOut(Date dataProtocolloOut) {
		this.dataProtocolloOut = dataProtocolloOut;
	}
	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}
	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}
	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}
	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	@Override
	public String toString() {
		return "AllegatoDiogeneInfoDTO [id=" + id + ", nome=" + nome + ", contenuto=" + contenuto
				+ ", numeroProtocolloIn=" + numeroProtocolloIn + ", dataProtocolloIn=" + dataProtocolloIn
				+ ", numeroProtocolloout=" + numeroProtocolloout + ", dataProtocolloOut=" + dataProtocolloOut
				+ ", codiceFascicolo=" + codiceFascicolo + ", dataTrasmissione=" + dataTrasmissione + ", types=" + types
				+ "]";
	}
	public String getOrgCreazione() {
		return orgCreazione;
	}
	public void setOrgCreazione(String orgCreazione) {
		this.orgCreazione = orgCreazione;
	}
	
		
}
