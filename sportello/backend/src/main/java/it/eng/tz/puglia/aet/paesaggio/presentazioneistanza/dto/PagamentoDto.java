package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaPagamentiDTO;
import it.eng.tz.puglia.util.json.DateDeserializer;
import it.eng.tz.puglia.util.json.DateSerializer;

/**
 * Dto per i dati del pagamento mod3 sulla pratica
 * @author Antonio La Gatta
 * @date 14 giu 2021
 */
public class PagamentoDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 14 giu 2021
	 */
	private static final long serialVersionUID = -935800650241533671L;


	private double importoProgetto;
	private double importoPagamento;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private java.util.Date dataScadenza;
	private String urlPdf;
	private String urlMyPay;
	private boolean pagato;
	private String causale;
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private String iuv;
	@JsonIgnore
	private String iud;
	@JsonIgnore
	private String ricevuta;
	/**
	 * @return the importoProgetto
	 */
	public double getImportoProgetto() {
		return importoProgetto;
	}
	/**
	 * @param importoProgetto the importoProgetto to set
	 */
	public void setImportoProgetto(final double importoProgetto) {
		this.importoProgetto = importoProgetto;
	}
	/**
	 * @return the importoPagamento
	 */
	public double getImportoPagamento() {
		return importoPagamento;
	}
	/**
	 * @param importoPagamento the importoPagamento to set
	 */
	public void setImportoPagamento(final double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	/**
	 * @return the urlPdf
	 */
	public String getUrlPdf() {
		return urlPdf;
	}
	/**
	 * @param urlPdf the urlPdf to set
	 */
	public void setUrlPdf(final String urlPdf) {
		this.urlPdf = urlPdf;
	}
	/**
	 * @return the urlMyPay
	 */
	public String getUrlMyPay() {
		return urlMyPay;
	}
	/**
	 * @param urlMyPay the urlMyPay to set
	 */
	public void setUrlMyPay(final String urlMyPay) {
		this.urlMyPay = urlMyPay;
	}
	/**
	 * @return the pagato
	 */
	public boolean isPagato() {
		return pagato;
	}
	/**
	 * @param pagato the pagato to set
	 */
	public void setPagato(final boolean pagato) {
		this.pagato = pagato;
	}
	/**
	 * @return the dataScadenza
	 */
	public java.util.Date getDataScadenza() {
		return dataScadenza;
	}
	/**
	 * @param dataScadenza the dataScadenza to set
	 */
	public void setDataScadenza(final java.util.Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	/**
	 * @return the iuv
	 */
	public String getIuv() {
		return iuv;
	}
	/**
	 * @param iuv the iuv to set
	 */
	public void setIuv(final String iuv) {
		this.iuv = iuv;
	}
	/**
	 * @return the iud
	 */
	public String getIud() {
		return iud;
	}
	/**
	 * @param iud the iud to set
	 */
	public void setIud(final String iud) {
		this.iud = iud;
	}
	/**
	 * @return the causale
	 */
	public String getCausale() {
		return causale;
	}
	/**
	 * @param causale the causale to set
	 */
	public void setCausale(final String causale) {
		this.causale = causale;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}
	/**
	 * @return the ricevuta
	 */
	public String getRicevuta() {
		return ricevuta;
	}
	/**
	 * @param ricevuta the ricevuta to set
	 */
	public void setRicevuta(final String ricevuta) {
		this.ricevuta = ricevuta;
	}
	
	
	public static PagamentoDto buildFromPraticaPagamento(PraticaPagamentiDTO pratPag) {
		PagamentoDto pagDto=null;
		if(pratPag!=null) {
			pagDto=new PagamentoDto();
			pagDto.setCausale(pratPag.getCausale());
			pagDto.setDataScadenza(pratPag.getDataScadenza());
			pagDto.setId(pratPag.getId());
			pagDto.setImportoPagamento(pratPag.getImportoPagamento());
			pagDto.setImportoProgetto(pratPag.getImportoProgetto());
			pagDto.setIud(pratPag.getIud());
			pagDto.setIuv(pratPag.getIuv());
			pagDto.setPagato(pratPag.getPagato());
			pagDto.setRicevuta(pratPag.getRicevuta());
			pagDto.setUrlMyPay(pratPag.getUrlMypay());
			pagDto.setUrlPdf(pratPag.getUrlPdf());
		}
		return pagDto;
	}
}
