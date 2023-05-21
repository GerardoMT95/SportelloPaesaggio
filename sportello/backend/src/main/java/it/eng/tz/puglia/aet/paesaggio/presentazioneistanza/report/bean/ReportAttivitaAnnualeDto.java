package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportAttivitaAnnualeDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 11 mag 2022
	 */
	private static final long serialVersionUID = -8661361289326807492L;

	
	private Integer anno;
	private Long numeroTrasmissioni;
	private Long numeroAvviate;
	private Long numeroConcluse;
	private Long numeroArchiviate;
	/**
	 * @return the anno
	 */
	public Integer getAnno() {
		return this.anno;
	}
	/**
	 * @param anno the anno to set
	 */
	public void setAnno(final Integer anno) {
		this.anno = anno;
	}
	/**
	 * @return the numeroTrasmissioni
	 */
	public Long getNumeroTrasmissioni() {
		return this.numeroTrasmissioni;
	}
	/**
	 * @param numeroTrasmissioni the numeroTrasmissioni to set
	 */
	public void setNumeroTrasmissioni(final Long numeroTrasmissioni) {
		this.numeroTrasmissioni = numeroTrasmissioni;
	}
	/**
	 * @return the numeroAvviate
	 */
	public Long getNumeroAvviate() {
		return this.numeroAvviate;
	}
	/**
	 * @param numeroAvviate the numeroAvviate to set
	 */
	public void setNumeroAvviate(final Long numeroAvviate) {
		this.numeroAvviate = numeroAvviate;
	}
	/**
	 * @return the numeroConcluse
	 */
	public Long getNumeroConcluse() {
		return this.numeroConcluse;
	}
	/**
	 * @param numeroConcluse the numeroConcluse to set
	 */
	public void setNumeroConcluse(final Long numeroConcluse) {
		this.numeroConcluse = numeroConcluse;
	}
	/**
	 * @return the numeroArchiviate
	 */
	public Long getNumeroArchiviate() {
		return this.numeroArchiviate;
	}
	/**
	 * @param numeroArchiviate the numeroArchiviate to set
	 */
	public void setNumeroArchiviate(final Long numeroArchiviate) {
		this.numeroArchiviate = numeroArchiviate;
	}
}
